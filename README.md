# ⚡ Akıllı Enerji Yönetim Sistemi

Bu proje, enerji tüketim verilerini anlık olarak takip etmek, analiz etmek ve belirlenen eşikler aşıldığında uyarılar üretmek amacıyla geliştirilen bir akıllı enerji otomasyonudur.

## 🛠️ Kullanılan Teknolojiler
* **Backend:** Java 25, Spring Boot
* **Veritabanı:** InfluxDB (Zaman Serisi Veritabanı)
* **Altyapı:** Docker, WSL 2, Maven

## 🚀 Kurulum ve Çalıştırma (Ekip İçin Talimatlar)

Projeyi kendi bilgisayarınızda (Local) çalıştırmak için aşağıdaki adımları sırasıyla izleyin:

### 1. Gereksinimler
Bilgisayarınızda **Docker Desktop**, **Java 25** ve **IntelliJ IDEA** kurulu olmalıdır.

### 2. Veritabanını (InfluxDB) Ayağa Kaldırma
Projenin veritabanı Docker üzerinde çalışmaktadır. CMD veya Terminal'i açıp şu komutu çalıştırın:
`docker run -d --name influxdb -p 8086:8086 influxdb:latest`

### 3. Veritabanı Ayarları (Token)
* Tarayıcınızdan `http://localhost:8086` adresine girin.
* "Get Started" diyerek kurulumu tamamlayın.
* **Organization:** `akillienerji`
* **Bucket:** `enerji_verileri`
* API Tokens menüsünden kendinize bir "All Access" token oluşturun ve Java kodundaki ilgili yere yapıştırın.

---

## 👥 Geliştirici Ekip ve Görev Dağılımı
* **Muhammed Metin:** Proje Lideri & Sistem Altyapısı (Docker / InfluxDB)
* **Berkay:** Sensör ve Veri Kaynakları Entegrasyonu
* **Lalise:** Teknoloji Araştırması ve Analiz Araçları
* **Halenaz:** Sistem Gereksinimleri ve Dokümantasyon
* **[5. Arkadaşın Adı]:** Kullanıcı Yetkilendirme (Security) ve Bildirim Servisi