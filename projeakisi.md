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
* **Utku Efe:** Kullanıcı Yetkilendirme (Security) ve Bildirim Servisi






## Teknoloji Araştırması ve Değerlendirme Raporu

## 1. Giriş

Enerji tüketiminin artması, enerji kaynaklarının daha verimli yönetilmesini gerekli hale getirmiştir. Bu proje kapsamında IoT sensörlerinden gelen verileri analiz eden, enerji tüketimini tahmin eden, enerji kaynaklarını optimize eden ve enerji verimliliğini artıran bir **Akıllı Enerji Yönetim Sistemi** geliştirilmesi amaçlanmaktadır.

Bu rapor, sistemin geliştirilmesinde kullanılacak teknolojilerin araştırılması ve değerlendirilmesini içermektedir.

# 2. Sistem Amaçları
Geliştirilecek sistemin temel hedefleri:
* Enerji tüketimini analiz etmek ve tahmin etmek
* Enerji kaynaklarını verimli şekilde kullanmak
* Enerji verimliliğini artırmak
* Gerçek zamanlı enerji verilerini izlemek
* Enerji kullanımındaki anormallikleri tespit etmek

# 3. IoT Veri Toplama Teknolojileri
Akıllı enerji yönetim sisteminde sensörler enerji tüketimi, sıcaklık ve cihaz kullanım verilerini toplar ve merkezi sisteme iletir.

### MQTT Protokolü
IoT sistemlerinde yaygın olarak kullanılan hafif bir mesajlaşma protokolüdür.
Avantajları:
     * Düşük bant genişliği kullanımı
     * Gerçek zamanlı veri iletimi
     * IoT cihazları için uygun yapı
Kullanılabilecek araç:
     * Eclipse Mosquitto

### HTTP / REST API
Sensör verileri REST API aracılığıyla backend sistemine gönderilebilir ve bu yapı **Spring Boot** ile kolayca entegre edilebilir.

# 4. Backend Teknolojileri
Backend geliştirme için **Java** ve **Spring Boot** kullanılacaktır.

### Java
Java güvenilir ve ölçeklenebilir sistemler geliştirmek için yaygın kullanılan bir programlama dilidir.
Avantajları:
     * Güvenilir yapı
     * Nesne yönelimli programlama desteği
     * Büyük geliştirici topluluğu

### Spring Boot
Backend geliştirme için kullanılacak framework’tür.
Avantajları:
     * Hızlı uygulama geliştirme
     * REST API oluşturma kolaylığı
     * Mikroservis mimarisi desteği

# 5. Veri Tabanı Teknolojileri
IoT sistemlerinde büyük miktarda veri üretildiği için uygun veri tabanı seçimi önemlidir.

### Zaman Serisi Veritabanı
Sensör verileri için **InfluxDB** kullanılabilir.
Avantajları:
     * Zaman serisi verileri için optimize edilmiştir
     * Yüksek performanslı veri yazma ve okuma
     * Gerçek zamanlı analiz desteği

### İlişkisel Veritabanı
Kullanıcı bilgileri ve sistem ayarları için **PostgreSQL** kullanılabilir

# 6. Enerji Tüketim Tahmini
Enerji tüketimini tahmin etmek için geçmiş veriler analiz edilir.
Kullanılabilecek algoritmalar:
* Linear Regression
* Random Forest
* Zaman Serisi Analizi
Bu algoritmalar gelecekteki enerji talebini tahmin etmek için kullanılacaktır.

# 7. Enerji Kaynaklarının Optimizasyonu
Sistem farklı enerji kaynaklarını en verimli şekilde kullanmayı amaçlar.
Kullanılabilecek optimizasyon algoritmaları:
    * Linear Programming
    * Genetic Algorithm
    * Greedy Algorithm
Bu algoritmalar sayesinde sistem hangi enerji kaynağının ne zaman kullanılacağını belirleyebilir.

# 8. Enerji Verimliliğini Artırma
Enerji verimliliğini artırmak için sistem çeşitli analiz yöntemleri kullanacaktır.
### Anomali Tespiti
Kullanılabilecek algoritmalar:
* Isolation Forest
* K-Means Clustering
Bu algoritmalar normalden fazla enerji tüketen cihazları tespit etmek için kullanılacaktır.

### Akıllı Otomasyon
Sistem bazı cihazların otomatik kontrolünü sağlayabilir.
Örnekler:
* Boş odalarda ışıkların kapanması
* Gereksiz çalışan cihazların kapatılması
* Enerji tüketiminin dengelenmesi
  
# 9. Gerçek Zamanlı İzleme Paneli
Enerji verilerinin izlenmesi için web tabanlı bir dashboard kullanılacaktır.
Kullanılabilecek araç:
    * Grafana
Dashboard üzerinde gösterilecek bilgiler:
   * Gerçek zamanlı enerji tüketimi
   * Enerji tahminleri
   * Enerji kaynaklarının kullanımı
   * Sistem uyarıları

# 10. Sistem Altyapısı
Sistemin geliştirilmesi ve çalıştırılması için bazı altyapı teknolojileri kullanılacaktır.

### Container Teknolojisi
Uygulamaların farklı ortamlarda sorunsuz çalışabilmesi için **Docker** kullanılacaktır.
Docker sayesinde:
* Backend servisleri izole şekilde çalıştırılabilir
* Veritabanı servisleri ayrı container’larda yönetilebilir
* Sistemin kurulumu ve dağıtımı kolaylaşır

### Geliştirme Ortamı
Geliştirme sürecinde **Windows Subsystem for Linux 2** kullanılabilir. Bu teknoloji Windows üzerinde Linux ortamı çalıştırılmasına olanak tanır ve backend geliştirme araçlarının daha stabil kullanılmasını sağlar.

### Build ve Bağımlılık Yönetimi
Backend projesinin derlenmesi ve bağımlılıkların yönetilmesi için **Apache Maven** kullanılacaktır.
Maven sayesinde:
* Proje kütüphaneleri otomatik yönetilir
* Kod derleme ve test süreçleri otomatikleştirilir
* Uygulama çalıştırılabilir paket haline getirilir


# Kullanılan Teknolojilerin Özeti

| Sistem Katmanı             | Kullanılan Teknoloji                  | Amaç                                     |
| -------------------------- | ------------------------------------- | ---------------------------------------- |
| IoT Veri İletimi           | MQTT                                  | Sensör verilerini iletmek                |
| Backend                    | Java                                  | Sistem mantığını geliştirmek             |
| Backend Framework          | Spring Boot                           | API ve backend servisleri                |
| Veri Tabanı                | InfluxDB                              | Sensör verilerini saklamak               |
| Veri Tabanı                | PostgreSQL                            | Kullanıcı ve sistem verileri             |
| Tahmin Algoritmaları       | Linear Regression, Random Forest      | Enerji tüketimini tahmin etmek           |
| Optimizasyon Algoritmaları | Linear Programming, Genetic Algorithm | Enerji kaynaklarını optimize etmek       |
| Anomali Tespiti            | Isolation Forest, K-Means             | Verimsiz enerji kullanımını tespit etmek |
| Dashboard                  | Grafana                               | Gerçek zamanlı enerji izleme             |
| Container                  | Docker                                | Servisleri container içinde çalıştırmak  |
| Geliştirme Ortamı          | WSL 2                                 | Linux tabanlı geliştirme ortamı          |
| Build Aracı                | Maven                                 | Backend projesini derlemek               |



