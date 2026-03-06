package com.akillienerji.AkilliEnerjiSistemi;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.write.Point;
import com.influxdb.client.domain.WritePrecision;
import java.time.Instant;

public class VeriServisi {
    // ELİNDEKİ O UZUN TOKEN'I BURAYA YAPIŞTIR
    private static final String token = "3mNco8zhj6oSR3E001A19kFupMVJtBotEgzaalzfyix8Je39gx2V3HuPeSu34GI9fke_FT-nrhqRa714XT63FA==";

    private static final String bucket = "enerji_verileri";
    private static final String org = "akillienerji";

    public static void main(String[] args) {
        // Kurduğun Docker sistemine bağlanıyoruz
        InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray(), org, bucket);

        // Sisteme ilk test verisini gönderiyoruz
        Point point = Point
                .measurement("enerji_tuketim")
                .addTag("cihaz", "test_cihazi")
                .addField("watt", 100.0)
                .time(Instant.now(), WritePrecision.NS);

        client.getWriteApiBlocking().writePoint(point);

        System.out.println("ALTYAPI KURULUMU BAŞARILI: Veri gönderildi!");

        client.close();
    }
}