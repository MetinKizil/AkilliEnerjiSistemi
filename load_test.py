"""
InfluxDB Yük Testi (Load Test)
Saniyede 1000 rastgele enerji verisi toplu yazma (batch) ile InfluxDB'ye gönderir.
"""

import time
import random
import signal
import sys
from datetime import datetime, timezone

from influxdb_client import InfluxDBClient, Point, WritePrecision
from influxdb_client.client.write_api import SYNCHRONOUS, WriteOptions

# ── InfluxDB Bağlantı Ayarları (InfluxDB Cloud - AWS eu-central-1) ──────────
INFLUXDB_URL = "https://eu-central-1-1.aws.cloud2.influxdata.com"
INFLUXDB_TOKEN = "ARUstZ80J_wkjOUQaEcQL1eo9zj5jpfQPaEhmbUJlOcY_7Ab3y3BPLi2L2E2nyjyPkiLUo85UbHC4z1DL4xdWw=="
INFLUXDB_ORG = "akillienerji"
INFLUXDB_BUCKET = "enerji_verileri"

# ── Test Parametreleri ───────────────────────────────────────────────────────
POINTS_PER_SECOND = 1000          # Saniyede yazılacak veri noktası
BATCH_SIZE = 500                  # Tek seferde gönderilecek nokta sayısı
FLUSH_INTERVAL_MS = 500           # Tampon boşaltma aralığı (ms)
TEST_DURATION_SECONDS = 60        # Toplam test süresi (saniye), 0 = sınırsız

# ── Sensör / Bölge tanımları ─────────────────────────────────────────────────
SENSOR_IDS = [f"sensor_{i:03d}" for i in range(1, 21)]       # 20 sensör
REGIONS = ["bölge_A", "bölge_B", "bölge_C", "bölge_D"]
ENERGY_TYPES = ["solar", "wind", "grid", "battery"]

running = True


def signal_handler(_sig, _frame):
    global running
    print("\n⏹  Durdurma sinyali alındı, yazma durduruluyor…")
    running = False


signal.signal(signal.SIGINT, signal_handler)


def generate_random_point() -> Point:
    """Rastgele bir enerji ölçüm noktası üretir."""
    sensor_id = random.choice(SENSOR_IDS)
    region = random.choice(REGIONS)
    energy_type = random.choice(ENERGY_TYPES)

    consumption_kwh = round(random.uniform(0.1, 150.0), 3)
    voltage_v = round(random.gauss(220.0, 10.0), 2)
    current_a = round(random.uniform(0.5, 32.0), 3)
    power_w = round(voltage_v * current_a, 2)
    frequency_hz = round(random.gauss(50.0, 0.5), 2)
    power_factor = round(random.uniform(0.85, 1.00), 3)
    temperature_c = round(random.gauss(35.0, 8.0), 1)

    return (
        Point("energy_measurement")
        .tag("sensor_id", sensor_id)
        .tag("region", region)
        .tag("energy_type", energy_type)
        .field("consumption_kwh", consumption_kwh)
        .field("voltage_v", voltage_v)
        .field("current_a", current_a)
        .field("power_w", power_w)
        .field("frequency_hz", frequency_hz)
        .field("power_factor", power_factor)
        .field("temperature_c", temperature_c)
        .time(datetime.now(timezone.utc), WritePrecision.NS)
    )


def run_load_test():
    """Ana yük testi döngüsü."""
    print("=" * 60)
    print("  InfluxDB Enerji Verisi Yük Testi")
    print("=" * 60)
    print(f"  URL            : {INFLUXDB_URL}")
    print(f"  Bucket         : {INFLUXDB_BUCKET}")
    print(f"  Org            : {INFLUXDB_ORG}")
    print(f"  Nokta/sn       : {POINTS_PER_SECOND}")
    print(f"  Batch boyutu   : {BATCH_SIZE}")
    print(f"  Süre           : {TEST_DURATION_SECONDS if TEST_DURATION_SECONDS else '∞'} sn")
    print("=" * 60)

    client = InfluxDBClient(
        url=INFLUXDB_URL,
        token=INFLUXDB_TOKEN,
        org=INFLUXDB_ORG,
    )

    write_options = WriteOptions(
        batch_size=BATCH_SIZE,
        flush_interval=FLUSH_INTERVAL_MS,
        jitter_interval=0,
        retry_interval=5_000,
        max_retries=3,
        max_retry_delay=30_000,
        max_close_wait=30_000,
        exponential_base=2,
    )

    write_api = client.write_api(write_options=write_options)

    total_points = 0
    elapsed_seconds = 0
    test_start = time.perf_counter()

    try:
        while running:
            loop_start = time.perf_counter()

            # ── Bu saniye için noktaları üret ve yaz ─────────────────────
            points = [generate_random_point() for _ in range(POINTS_PER_SECOND)]
            write_api.write(bucket=INFLUXDB_BUCKET, org=INFLUXDB_ORG, record=points)

            total_points += POINTS_PER_SECOND
            elapsed_seconds += 1

            # ── İstatistik yazdır ────────────────────────────────────────
            wall_elapsed = time.perf_counter() - test_start
            rate = total_points / wall_elapsed if wall_elapsed > 0 else 0
            print(
                f"  [Saniye {elapsed_seconds:>5}]  "
                f"Yazılan: {total_points:>10,} nokta  |  "
                f"Ort. hız: {rate:>10,.1f} nokta/sn"
            )

            # ── Süre kontrolü ────────────────────────────────────────────
            if TEST_DURATION_SECONDS and elapsed_seconds >= TEST_DURATION_SECONDS:
                print("\n⏱  Belirlenen test süresi doldu.")
                break

            # ── 1 saniye aralığı koru ────────────────────────────────────
            loop_duration = time.perf_counter() - loop_start
            sleep_time = max(0.0, 1.0 - loop_duration)
            if sleep_time > 0:
                time.sleep(sleep_time)

    except Exception as exc:
        print(f"\n❌  Hata: {exc}")
    finally:
        print("\n🔄  Tampon boşaltılıyor (flush)…")
        write_api.close()
        client.close()

        wall_total = time.perf_counter() - test_start
        print("\n" + "=" * 60)
        print("  Test Özeti")
        print("=" * 60)
        print(f"  Toplam yazılan   : {total_points:,} nokta")
        print(f"  Toplam süre      : {wall_total:.2f} sn")
        print(f"  Ortalama hız     : {total_points / wall_total:,.1f} nokta/sn" if wall_total > 0 else "")
        print("=" * 60)


if __name__ == "__main__":
    run_load_test()
