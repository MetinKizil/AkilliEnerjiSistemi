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
Akıllı enerji yönetim sisteminde sensörler enerji tüketimi, sıcaklık ve cihaz kullanım verilerini toplar ve merkezi sisteme iletir.[Akıllı Enerji yönetim sistemleri.pdf](https://github.com/user-attachments/files/25883786/Akilli.Enerji.yonetim.sistemleri.pdf)
[Akıllı Enerji yönetim sistemleri.pdf](https://github.com/user-attachments/files/25883786/Akilli.Enerji.yonetim.sistemleri.pdf)


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



# Veri Kaynakları ve Entegrasyon Yöntemleri Araştırması


## Akıllı Enerji Yönetim Sistemi: Kapsamlı Veri Toplama, Entegrasyon ve Akış Mimarisi Raporu
Sistemin temel amacı olan enerji tüketiminin anlık analizi, geleceğe yönelik tahmini ve kaynak kullanımının optimizasyonu; sahadan elde edilecek verilerin
kesintisizliğine, kalitesine ve merkezi sistemlerle olan kusursuz entegrasyonuna bağlıdır. Bir IoT ekosisteminde donanımdan yapay zekaya kadar uzanan bu köprü,
veri darboğazlarını önleyecek şekilde tasarlanmalıdır. Bu rapor, fiziksel dünyadaki enerji hareketlerinin dijitalleşerek son kullanıcı ekranına ve otonom kararlara
dönüşme sürecini tüm detaylarıyla açıklamaktadır.



## 1. Veri Kaynaklarının Belirlenmesi ve Stratejik Sınıflandırma
Sistemin besleneceği ham veri kaynakları, çevresel faktörleri ve elektriksel parametreleri okuyan uç cihazlardan (Edge Devices) oluşmaktadır. Makine öğrenmesi
algoritmalarının doğru eğitilebilmesi için veri kaynakları çok boyutlu olarak üç ana kategoride yapılandırılmıştır:

* Telemetri ve Enerji Tüketim Kaynakları: Akıllı elektrik sayaçları, alt süzme sayaçlar ve akıllı prizlerden oluşan bu donanımlar sistemin ana damarıdır. Bu sensörler
  sadece cihazın açık/kapalı olma durumunu değil; anlık çekilen akımı (Amper), şebeke gerilimini (Volt), aktif ve reaktif güç tüketimini (Watt/VAR) milisaniye hassasiyetinde
  ölçer. Elde edilen bu sayısal yoğunluk, Doğrusal Regresyon (Linear Regression) ve Rastgele Orman (Random Forest) algoritmalarının geçmiş tüketim profillerini öğrenerek
  gelecekteki enerji talebini yüksek doğrulukla tahmin etmesini sağlayan birincil kaynaktır.

* Çevresel ve İklimsel Durum Sensörleri: Bir binadaki veya tesisteki en büyük enerji tüketicileri genellikle Isıtma, Havalandırma ve İklimlendirme (HVAC) sistemleridir.
  Bu sistemlerin optimizasyonu için sadece ne kadar elektrik harcadıklarını bilmek yetmez, ortamın şartlarını da bilmek gerekir. Bu nedenle hassas sıcaklık ve nem sensörleri
  konumlandırılmıştır. Bu sensörler, algoritmaların "Dışarısı 15 derece iken içeriyi 22 derece yapmak için ne kadar enerji harcanıyor?" sorusuna yanıt bulmasını ve Doğrusal
  Programlama (Linear Programming) ile optimum çalışma senaryolarını hesaplamasını sağlar.

* Varlık, Hareket ve Durum Sensörleri: Enerji israfını kaynağında kesmek için kurulan tetikleyici donanımlardır. Odalardaki Pasif Kızılötesi (PIR) hareket sensörleri
  ve cihazların anlık konumunu okuyan röleler bu gruba girer. Sistem, İzolasyon Ormanı (Isolation Forest) algoritması ile bir anormallik ararken bu sensörlerin verilerini
  kullanır. Örneğin, enerji analizörü bir odada 500W tüketim okurken, hareket sensörü odanın boş olduğunu bildiriyorsa, sistem bunu anında bir "Verimsizlik Anomalisi" olarak işaretler.



## 2. IoT Veri Toplama Yöntemleri ve Ağ İletişim Protokolleri
Binlerce farklı sensörden gelen asenkron verilerin, merkezi ağı (network) kilitlemeden ve veri kaybı yaşatmadan toplanabilmesi için iki katmanlı bir iletişim mimarisi belirlenmiştir:

* Sürekli Akış ve Yüksek Frekanslı Veri Toplama (MQTT): Enerji ve sıcaklık değerleri gibi saniyede bir güncellenen dinamik veriler, nesnelerin interneti için özel olarak tasarlanmış,
  çok hafif bir mesajlaşma protokolü olan MQTT (Message Queuing Telemetry Transport) ile toplanacaktır. Sistemde merkez nokta olarak konumlandırılan Eclipse Mosquitto mesaj
  aracısı (Broker), sensörlerden gelen yayınları (Publish) belirli konu başlıkları (Topics) altında toplar. MQTT'nin sunduğu "Hizmet Kalitesi" (QoS) standartları sayesinde, bağlantı
  anlık olarak kopsa bile cihazlar veriyi kendi üzerlerinde tutar ve bağlantı geldiğinde merkeze eksiksiz aktarır. Bu, enerji faturalandırması ve yapay zeka eğitim setleri için
  kritik olan "sıfır veri kaybı" ilkesini garanti altına alır.

* Statik Konfigürasyon ve Harici Veri Toplama (HTTP / REST API): Sensörlerin sürekli değişmeyen kimlik bilgileri, ağa ilk kayıt anlarındaki güvenlik sertifikası talepleri
  ve sistemin tahmin modelini güçlendirmek için dış kaynaklardan (örneğin ulusal meteoroloji servislerinden) çekilecek hava durumu verileri standart HTTP/HTTPS protokolleri
  üzerinden toplanır. Bu, gereksiz MQTT trafiğini önler ve dış sistemlerle entegrasyonu standartlaştırır.



## 3. Sistem İçi Veri Entegrasyonu ve Arka Uç (Backend) Mimarisi
Fiziksel sahadan MQTT ve REST API aracılığıyla toplanan ham verilerin doğrulanması, anlamlandırılması ve kalıcı hale getirilmesi süreci, Java ve Spring Boot teknolojileriyle
geliştirilen merkezi arka uç mimarisi üzerinden sağlanmaktadır:

* Veri Karşılama, Doğrulama ve Zenginleştirme: Docker konteynerleri içerisinde birbirinden izole edilmiş olarak çalışan Spring Boot mikroservisleri, Mosquitto mesaj aracısına abone
  (Subscriber) olur. Sensörden gelen ham veri paketi sisteme ulaştığında, Spring Boot bu paketin içindeki cihaz kimliğini alır. Cihazın sahte bir donanım olmadığını kanıtlamak
  için, gelen bilgiyi öncelikle ilişkisel veritabanındaki (PostgreSQL) kayıtlı cihaz listesi ve güvenlik anahtarları ile çapraz sorguya sokar.

* Hibrit ve Çok Katmanlı Depolama Entegrasyonu: Gerçek bir akıllı sistem, verinin türüne göre farklı depolama stratejileri gerektirir.
  * Zaman Serisi Entegrasyonu (InfluxDB): Doğrulanmış olan anlık enerji tüketim ve sıcaklık metrikleri (içerisinde sadece zaman damgası, sensör ID'si ve ölçüm değeri barındıran
    yapılar), saniyede milyonlarca satır yazma ve okuma kapasitesine sahip InfluxDB'ye entegre edilir. Bu sayede geçmişe dönük 6 aylık bir enerji trendi saniyeler içinde analiz
    edilebilir.
  * İlişkisel Entegrasyon (PostgreSQL): Sistemin genel ayarları, hangi sensörün hangi binada ve odada yer aldığı, yöneticilerin sisteme giriş bilgileri ve otomasyon kural
    setleri ACID (Bütünlük ve Tutarlılık) standartlarında PostgreSQL üzerinde tutulur.

- Makine Öğrenmesi Entegrasyonu: Analitik motorları (Python veya Java tabanlı ML servisleri), doğrudan InfluxDB ile entegre çalışır. Modeller (K-Means, Isolation Forest vb.)
  her gece veritabanından günün tüketim özetini çeker, işler, yeni eşik değerleri (threshold) belirler ve bu yeni optimizasyon kurallarını sabah uygulanmak üzere tekrar sistemin
  merkezine aktarır.



## 4. Veri Akış Şeması
Tanımlanan kaynaklar, toplama yöntemleri ve karmaşık entegrasyon süreçleri, fiziksel dünyadan başlayıp yöneticinin ekranında son bulan (ve ardından fiziksel dünyaya geri dönen)
şu kusursuz mantıksal akışı izler:

 1) Ölçüm ve Dijitalizasyon (Sahadaki Doğuş): Enerji analizörü, bağlı olduğu makinenin çektiği akımı okur, içindeki mikroişlemci sayesinde bu elektriksel dalgayı sayısal bir
    değere ve ardından standart bir JSON veri paketine dönüştürür.

 2) Güvenli Taşıma (Ağ Üzerindeki Yolculuk): Sensör, oluşturduğu bu veri paketini TLS şifrelemesi kullanarak, yerel internet ağı üzerinden buluttaki Eclipse Mosquitto (MQTT Broker)
    sunucusuna "enerji/bina1/oda5" konu başlığıyla fırlatır.

 3) Yakalama ve Güvenlik Duvarı (Merkezi İşleme): Sunucuda sürekli uyanık halde bekleyen Java/Spring Boot servisi, Mosquitto'ya düşen bu mesajı anında yakalar. Verinin içindeki
    kimlik numarasını PostgreSQL'e sorarak "Bu cihaz bizim ağımıza ait mi ve yetkisi var mı?" diye kontrol eder.

 4) Kalıcı Hafızaya Yazma (Depolama): Doğrulama başarılıysa, Spring Boot ham ölçüm verisini (Örn: 15.03.2024 10:45:00 - 3200 Watt) milisaniye gecikmeyle doğrudan InfluxDB
    zaman serisi veritabanına kaydeder.

 5) Gerçek Zamanlı Zeka (Anlık Analiz): Veri InfluxDB'ye yazıldığı anda, beklemede olan Isolation Forest algoritması son 10 dakikanın verisiyle bu yeni veriyi karşılaştırır.
    Değer normalin %50 üzerindeyse bir "Kritik Eşik Uyarı Olayı" oluşturur.

 6) Görsel İzleme (Kullanıcı Paneline Yansıma): Sistem yöneticisi o an ekranında açık olan Grafana paneline bakmaktadır. Grafana, InfluxDB'den okuduğu bu veriyi canlı bir grafikte
    çizerken, üretilen uyarı olayı sebebiyle ekranda kırmızı bir bildirim belirir.

 7) Otonom Aksiyon (Sistemin Fiziksel Müdahalesi): Eğer sisteme "Kritik anormalliklerde cihazı kapat" kuralı girilmişse (Genetik Algoritmaların belirlediği senaryolara göre),
    Spring Boot yöneticinin müdahalesini beklemeden MQTT üzerinden ilgili sayaca doğru geri bir mesaj gönderir: "Röleyi Kapat". Sayaç bu mesajı alır, elektriği keser ve döngü güvenli
    bir şekilde tamamlanır.


    
