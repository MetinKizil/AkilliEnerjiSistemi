package com.akillienerji.AkilliEnerjiSistemi.repository;

import com.akillienerji.AkilliEnerjiSistemi.model.SensorReading;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * InfluxDB veri erisim katmani.
 * Sensor verilerinin yazilmasi ve sorgulanmasi.
 *
 * Flux query dili kullanarak zaman serisi sorgulari yapar.
 */
@Repository
public class InfluxDBRepository {

    private static final Logger logger = LoggerFactory.getLogger(InfluxDBRepository.class);

    private final InfluxDBClient influxDBClient;
    private final String bucket;
    private final String org;

    @Autowired
    public InfluxDBRepository(InfluxDBClient influxDBClient,
                              @Qualifier("influxDBBucket") String bucket,
                              @Qualifier("influxDBOrg") String org) {
        this.influxDBClient = influxDBClient;
        this.bucket = bucket;
        this.org = org;
    }

    /**
     * Sensor verisini InfluxDB'ye yazar.
     *
     * @param reading Yazilacak sensor olcumu
     */
    public void writeSensorData(SensorReading reading) {
        try {
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

            Point point = Point.measurement(reading.getMeasurement())
                    .addTag("cihaz", reading.getDeviceId())
                    .addTag("tip", reading.getSensorType())
                    .addTag("birim", reading.getUnit())
                    .addField("deger", reading.getValue())
                    .time(reading.getTimestamp(), WritePrecision.NS);

            // Konum bilgisi varsa ekle
            if (reading.getLocation() != null && !reading.getLocation().isEmpty()) {
                point.addTag("konum", reading.getLocation());
            }

            // Ek tag'leri ekle
            for (Map.Entry<String, String> tag : reading.getTags().entrySet()) {
                point.addTag(tag.getKey(), tag.getValue());
            }

            // Ek field'lari ekle
            for (Map.Entry<String, Object> field : reading.getFields().entrySet()) {
                if (field.getValue() instanceof Double) {
                    point.addField(field.getKey(), (Double) field.getValue());
                } else if (field.getValue() instanceof Long) {
                    point.addField(field.getKey(), (Long) field.getValue());
                } else if (field.getValue() instanceof Boolean) {
                    point.addField(field.getKey(), (Boolean) field.getValue());
                } else {
                    point.addField(field.getKey(), field.getValue().toString());
                }
            }

            writeApi.writePoint(point);
            logger.debug("Sensor verisi yazildi: {}", reading);
        } catch (Exception e) {
            logger.error("InfluxDB yazma hatasi: {}", e.getMessage());
        }
    }

    /**
     * Son N dakikadaki verileri sorgular.
     *
     * @param measurement Olcum adi (orn: "enerji_tuketim")
     * @param minutes     Son X dakika
     * @return Sorgu sonuclari
     */
    public List<SensorReading> queryRecentData(String measurement, int minutes) {
        String flux = String.format(
                "from(bucket: \"%s\")" +
                " |> range(start: -%dm)" +
                " |> filter(fn: (r) => r._measurement == \"%s\")" +
                " |> sort(columns: [\"_time\"], desc: false)",
                bucket, minutes, measurement
        );
        return executeQuery(flux);
    }

    /**
     * Son N saatteki verileri sorgular.
     *
     * @param measurement Olcum adi
     * @param hours       Son X saat
     * @return Sorgu sonuclari
     */
    public List<SensorReading> queryHistoryData(String measurement, int hours) {
        String flux = String.format(
                "from(bucket: \"%s\")" +
                " |> range(start: -%dh)" +
                " |> filter(fn: (r) => r._measurement == \"%s\")" +
                " |> sort(columns: [\"_time\"], desc: false)",
                bucket, hours, measurement
        );
        return executeQuery(flux);
    }

    /**
     * Belirli bir cihazin son verilerini sorgular.
     *
     * @param measurement Olcum adi
     * @param deviceId    Cihaz ID
     * @param hours       Son X saat
     * @return Sorgu sonuclari
     */
    public List<SensorReading> queryDeviceData(String measurement, String deviceId, int hours) {
        String flux = String.format(
                "from(bucket: \"%s\")" +
                " |> range(start: -%dh)" +
                " |> filter(fn: (r) => r._measurement == \"%s\")" +
                " |> filter(fn: (r) => r.cihaz == \"%s\")" +
                " |> sort(columns: [\"_time\"], desc: false)",
                bucket, hours, measurement, deviceId
        );
        return executeQuery(flux);
    }

    /**
     * Agregasyon sorgusu (ortalama, max, min).
     *
     * @param measurement   Olcum adi
     * @param hours         Son X saat
     * @param aggregateFunc mean, max, min, sum
     * @param windowMinutes Pencere suresi (dakika)
     * @return Agrege sonuclar
     */
    public List<SensorReading> queryAggregatedData(String measurement, int hours,
                                                    String aggregateFunc, int windowMinutes) {
        String flux = String.format(
                "from(bucket: \"%s\")" +
                " |> range(start: -%dh)" +
                " |> filter(fn: (r) => r._measurement == \"%s\")" +
                " |> aggregateWindow(every: %dm, fn: %s, createEmpty: false)" +
                " |> sort(columns: [\"_time\"], desc: false)",
                bucket, hours, measurement, windowMinutes, aggregateFunc
        );
        return executeQuery(flux);
    }

    /**
     * Son okunan veriyi getirir.
     *
     * @param measurement Olcum adi
     * @return Son sensor okumasi veya null
     */
    public SensorReading queryLastData(String measurement) {
        String flux = String.format(
                "from(bucket: \"%s\")" +
                " |> range(start: -1h)" +
                " |> filter(fn: (r) => r._measurement == \"%s\")" +
                " |> last()",
                bucket, measurement
        );
        List<SensorReading> results = executeQuery(flux);
        return results.isEmpty() ? null : results.get(results.size() - 1);
    }

    /**
     * Flux sorgusunu calistirir ve sonuclari SensorReading listesine donusturur.
     */
    private List<SensorReading> executeQuery(String flux) {
        List<SensorReading> readings = new ArrayList<>();
        try {
            QueryApi queryApi = influxDBClient.getQueryApi();
            List<FluxTable> tables = queryApi.query(flux, org);

            for (FluxTable table : tables) {
                for (FluxRecord record : table.getRecords()) {
                    SensorReading reading = new SensorReading();
                    reading.setMeasurement(record.getMeasurement());
                    reading.setDeviceId(getTagValue(record, "cihaz"));
                    reading.setSensorType(getTagValue(record, "tip"));
                    reading.setUnit(getTagValue(record, "birim"));
                    reading.setLocation(getTagValue(record, "konum"));

                    Object value = record.getValue();
                    if (value instanceof Number) {
                        reading.setValue(((Number) value).doubleValue());
                    }

                    Instant time = record.getTime();
                    if (time != null) {
                        reading.setTimestamp(time);
                    }

                    readings.add(reading);
                }
            }
            logger.debug("Flux sorgusu {} sonuc dondurdu", readings.size());
        } catch (Exception e) {
            logger.error("InfluxDB sorgu hatasi: {}", e.getMessage());
        }
        return readings;
    }

    /**
     * FluxRecord'dan guvenli tag degeri okur.
     */
    private String getTagValue(FluxRecord record, String tagName) {
        Object value = record.getValueByKey(tagName);
        return value != null ? value.toString() : "";
    }
}
