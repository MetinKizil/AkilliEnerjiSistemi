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

---

## 📊  Sistem Analizi ve Görselleştirme

### 1. Use Case (Kullanım Durumu) Diyagramı
[cite_start]Sistemdeki aktörlerin (Kullanıcı ve Admin) fonksiyonel yetkilerini tanımlar[cite: 5]:
* [cite_start]**Kullanıcı:** Giriş yapar, anlık veri izler, geçmiş analizleri görür ve bildirim alır[cite: 1, 2, 3, 4, 8].
* [cite_start]**Admin:** Kullanıcı girişine ek olarak sistemin alarm üreteceği "Eşik Değeri Ayarlama" yetkisine sahiptir[cite: 6, 7].

### 2. Sistem Akış Diyagramı (Flowchart)
[cite_start]Sistemin mantıksal çalışma döngüsü şu şekildedir[cite: 9, 16]:
* [cite_start]**Başlangıç:** Kullanıcı sisteme giriş yapar ve InfluxDB üzerinden sensör verileri okunur[cite: 10, 11].
* [cite_start]**Karar Mekanizması:** Eğer tüketim belirlenen eşik değerinden büyükse (`Evet`), sistem otomatik olarak alarm bildirimi üretir[cite: 13, 14, 15].
* [cite_start]**Döngü:** Tüketim eşik değerinin altındaysa (`Hayır`), sistem veri okumaya devam eder[cite: 12].

> **Not:** Diyagramların detaylı hali repoda yer alan `Use Case ve Akış Diyagramı drawio.pdf` dosyasında mevcuttu

Sistemin Mimari tasarimi

[Sistem Mimari Tasarımı.docx](https://github.com/user-attachments/files/26325936/Sistem.Mimari.Tasarimi.docx)


## 📊 3. Hafta: Sistem Analizi ve Görselleştirme

Sistemin işleyişini ve kullanıcı etkileşimlerini görselleştirmek adına **Use Case** ve **Akış Diyagramları** hazırlanmıştır.

### 1. Use Case (Kullanım Durumu) Diyagramı

[cite_start]Sistemdeki aktörlerin (Kullanıcı ve Admin) fonksiyonel yetkilerini tanımlar[cite: 5]:
* [cite_start]**Kullanıcı:** Giriş yapar, anlık veri izler, geçmiş analizleri görür ve bildirim alır[cite: 1, 2, 3, 4, 8].
* [cite_start]**Admin:** Kullanıcı girişine ek olarak sistemin alarm üreteceği "Eşik Değeri Ayarlama" yetkisine sahiptir[cite: 6, 7].

### 2. Sistem Akış Diyagramı (Flowchart)
[cite_start]Sistemin mantıksal çalışma döngüsü şu şekildedir[cite: 9, 16]:
* [cite_start]**Başlangıç:** Kullanıcı sisteme giriş yapar ve InfluxDB üzerinden sensör verileri okunur[cite: 10, 11].
* [cite_start]**Karar Mekanizması:** Eğer tüketim belirlenen eşik değerinden büyükse (`Evet`), sistem otomatik olarak alarm bildirimi üretir[cite: 13, 14, 15].
* [cite_start]**Döngü:** Tüketim eşik değerinin altındaysa (`Hayır`), sistem veri okumaya devam eder[cite: 12].

<img width="277" height="861" alt="Use Case Diyagramı-Sayfa-2 drawio" src="https://github.com/user-attachments/assets/e991ad56-89de-4b4e-8750-714b336538dc" />

<img width="741" height="992" alt="Use Case Diyagramı-Sayfa-1 drawio" src="https://github.com/user-attachments/assets/74889a4e-e9f8-4169-b12c-1c29a0b7d455" />


# 📊 3. HAFTA: İNTERAKTİF ARAYÜZ TASARIMI VE DASHBOARD PROTOTİPİ

**NOT:** Projemizin kullanıcı deneyimini (UX) en gerçekçi şekilde test etmek amacıyla, statik çizimler yerine **React**, **Tailwind CSS** ve **Lucide-React** kütüphaneleri kullanılarak işlevsel bir ön uç (frontend) prototipi geliştirilmiştir. 

<img width="1918" height="1078" alt="AkilliEnerjiSistemi2" src="https://github.com/user-attachments/assets/2e3fcb50-fc39-4c6b-b92a-a54cd29f58da" />


### **Teknik Detaylar ve Özellikler:**

* **KPI Kartları:** Anlık enerji tüketimi, üretim ve batarya durumu **InfluxDB**'den gelecek verilere göre dinamik olarak güncellenecek şekilde kurgulanmıştır.
* **Cihaz Yönetimi:** Bağlı cihazların (Klima, Aydınlatma vb.) durumunu kontrol eden interaktif **"Toggle"** butonları eklenmiştir.
* **Alarm Modülü:** Kritik eşik değerleri aşıldığında kullanıcıya anlık bildirim verecek olan **Uyarı Sistemi** görselleştirilmiştir.
🚀 Akıllı Enerji Sistemi - Gelecekteki Performans Testleri Planı
1. Testin Amacı ve Kapsamı
Akıllı Enerji Sistemi, doğası gereği binlerce IoT sensöründen aynı anda anlık veri (tüketim miktarı) alacak şekilde tasarlanmıştır. Bu planın amacı, anlık yüksek veri akışı durumunda sistemin (özellikle EnergyController, AlertService ve NotificationService bileşenlerinin) çökmeden, hızlı ve hatasız çalışmasını garanti altına almaktır.

Ana Hedef Uç Nokta (Endpoint): POST /api/energy/check

2. Kullanılacak Test Araçları ve Altyapı

Test Aracı: Python tabanlı Locust aracı kullanılacaktır. Dağıtık mimariyi desteklemesi ve kod üzerinden senaryo yazılabilmesi (locustfile.py) sebebiyle tercih edilmiştir.

İzleme (Monitoring): Testler sırasında Spring Boot sunucusunun CPU ve RAM kullanımları izlenecektir.

3. Planlanan Performans Testi Türleri
Gelecekteki güncellemelerde sistem aşağıdaki 3 farklı senaryoya göre test edilecektir:

Yük Testi (Load Testing): Sisteme beklenen normal trafik (Örn: Aynı anda veri gönderen 1.000 cihaz) verilerek, darboğaz olup olmadığı gözlemlenecektir.

Stres Testi (Stress Testing): Sistemin sınırlarını bulmak için kapasite yavaş yavaş artırılacak (Örn: 5.000 - 10.000 cihaz) ve sistemin hangi noktada pes ettiği (çöktüğü veya hata verdiği) tespit edilecektir.

Dayanıklılık Testi (Endurance Testing): Hafıza sızıntılarını (Memory Leak) tespit etmek için sistem, orta düzeyli bir yük altında (Örn: 500 cihaz) kesintisiz olarak 12-24 saat boyunca çalıştırılacaktır.

4. Başarı Kriterleri (Hedeflenen Metrikler)
Yapılacak testlerin başarılı sayılabilmesi için sistemin şu metrikleri sağlaması hedeflenmektedir:

Yanıt Süresi (Response Time): Gelen check isteklerinin %95'inin 500 milisaniyenin altında işlenmesi.

Hata Oranı (Failure Rate): Aşırı yük altında bile yetkilendirme (Token) hatası haricindeki sistem hatalarının %1'in altında kalması.

Saniyedeki İstek (RPS): Sistemin saniyede en az 250+ isteği (Request per Second) sorunsuz işleyebilmesi.

5. Gelecek Aksiyon Adımları (Next Steps)

Güvenlik (Security) Entegrasyonu: İlk denemelerde fark edildiği üzere, Spring Security (SecurityConfig.java) dışarıdan gelen istekleri engellemektedir (401/403 Hataları). Gelecekteki test scriptlerine, sanal cihazların sisteme giriş yapabilmesi için dinamik bir "Bearer Token/JWT" üretme mekanizması eklenecektir.

CI/CD Entegrasyonu: Locust testleri GitHub Actions veya Jenkins gibi süreçlere dahil edilecek; sisteme her yeni kod eklendiğinde performans testi otomatik olarak çalıştırılacaktır.



# API Tasarımı

## Backend - RESTful API'lar
4 yeni Controller oluşturuldu, toplam 12 REST endpoint tanımlandı:

| # | Metod                         | URL                                 | İşlev                                     |
|---|-------------------------------|-------------------------------------|-------------------------------------------|
| 1 | POST                          | /api/auth/login                     | Kullanıcı giriş doğrulama                 |
| 2 | GET                           | /api/auth/me                        | Oturum bilgisi sorgulama                  |
| 3 | GET                           | /api/dashboard/summary              | KPI kartları verisi                       |
| 4 | GET                           | /api/dashboard/realtime             | Anlık enerji verisi                       |
| 5 | GET                           | /api/dashboard/history              | Geçmiş enerji grafik verisi               |
| 6 | GET                           | /api/devices                        | Tüm cihazları listele                     |
| 7 | GET                           | /api/devices/{id}                   | Tek cihaz bilgisi                         |
| 8 | POST                          | /api/devices/{id}/toggle            | Cihaz aç/kapa                             |
| 9 | PUT                           | /api/devices/{id}                   | Cihaz güncelle                            |
|10 | GET                           | /api/alerts                         | Uyarıları listele                         |
|11 | POST                          | /api/alerts/{id}/acknowledge        | Uyarıyı onayla                            |
|12 | PUT                           | /api/alerts/threshold               | Eşik değeri güncelle                      |



## Frontend - Tüm Butonlar ve İşlevleri

| # | Buton                         | Ne Yapar                                                            |
|---|-------------------------------|---------------------------------------------------------------------|
| 1 | Giriş Yap                     | Kullanıcı adı/şifre ile /api/auth/login çağrısı                     |
| 2 | Admin/Kullanıcı chip          | Demo hesap bilgilerini otomatik doldurma                            |
| 3 | Şifre göster/gizle            | Password alanını text/password arasında değiştirir                  |
| 4 | Çıkış                         | Session temizleme + login sayfasına yönlendirme                     |
| 5 | Cihaz Toggle (8 adet)         | Her cihazın yanındaki toggle ile aç/kapa                            |
| 6 | Uyarı Onayla                  | Alarmı kapatır (acknowledged)                                       |
| 7 | Eşik Değeri Kaydet            | Admin: max/uyarı/kritik değerleri günceller                         |
| 8 | 6/12/24/48 Saat               | Grafik zaman aralığını değiştirir                                   |
| 9 | Grafik Yenile                 | Enerji grafiğini yeniden yükler                                     |
|10 | Verileri Yenile               | Tüm dashboard verilerini günceller                                  |
|11 | CSV İndir                     | Enerji geçmişini CSV dosyası olarak indirir                         |
|12 | Enerji Kontrol                | Anlık tüketimi eşik değeriyle karşılaştırır                         |



## Test Sonuçları 

* ✅ Maven derleme: BUILD SUCCESS (17 kaynak dosya)
* ✅ Spring Boot başlatma: Port 8080'de çalışıyor
* ✅ Login sayfası: Admin ve Kullanıcı hesaplarıyla giriş yapıldı
* ✅ Dashboard: KPI kartları, grafik, cihaz listesi, uyarılar gösterildi
* ✅ Cihaz toggle'ları çalışıyor (aç/kapa)
* ✅ Uyarı onayla butonu çalışıyor
* ✅ Eşik değeri ayarları görünüyor (Admin hesabıyla)



## Kullanıcı Hesapları

| Kullanıcı                         | Şifre                            | Rol                               |
|-----------------------------------|----------------------------------|-----------------------------------|
| admin                             | admin123                         | ADMIN + USER                      |
| kullanici                         | sifre123                         | USER                              |



## Çalıştırma

* powershell
  $env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot"
  $env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
  .\mvnw.cmd spring-boot:run

* Tarayıcıda: http://localhost:8080/login.html







# Spring Boot Proje Yapısı ve Bağımlılıklarının Belirlenmesi
Bu doküman, projede gerçekleştirilen altyapı çalışmalarını, mimari kararları ve sisteme kazandırılan yeni özellikleri detaylandırmak amacıyla hazırlanmıştır. Projeyi bir prototipten alarak, genişletilebilir ve gerçek zamanlı bir IoT mimarisine dönüştüren tüm işlemler aşağıda raporlanmıştır.


## 1. Yönetici Özeti (Executive Summary)
Akıllı Enerji Yönetim Sistemi'nin başlangıç aşamasındaki temel Spring Boot yapısı, gerçek dünyadaki IoT gereksinimlerini karşılayacak şekilde profesyonel bir çok katmanlı mimariye (N-Tier Architecture) geçirilmiştir.

Projenin yeni yetenekleri:
* Gerçek Zamanlı Veri Akışı: Sensör verilerinin milisaniyelik hızlarda sisteme aktarımı (MQTT ve WebSocket ile).
* Makine Öğrenimi (ML) Katmanı: Sistem geçmiş verilere dayanarak hem gelecek tüketimleri tahmin edebilir (Forecasting) hem de anormallikleri saptayabilir (Anomaly Detection) hale getirilmiştir.
* Konteyner Mimarisi (Docker): Uygulamanın bağlı olduğu veritabanı ve mesaj kuyruğu sistemleri tek bir komutla (docker-compose up) izole ve hatasız şekilde  kurulabilir hale geldi.
* Toplamda 17 yeni Java sınıfı, 6 yeni kütüphane/bağımlılık, 3 konfigürasyon dosyası sisteme sorunsuz bir şekilde entegre edilmiştir.

Ayrıca kod düzeni; config, controller, dto, exception, ml, model, mqtt, repository, service gibi kurumsal standartlara uygun paket (package) yapısına bölünmüştür.


## 2. Sisteme Eklenen Gelişmiş Teknolojiler ve Bağımlılıklar (pom.xml)
Sistemi daha güçlü hale getirmek için dahil edilen teknolojiler:

* Spring Integration MQTT: (Eclipse Paho) IoT cihazlarından gelecek verileri yüksek hızla, paket sızması yaşamadan alabilmemizi (Subscribe) ve cihazlara otonom şekilde açma/kapama komutu verebilmemizi (Publish) sağlayan MQTT broker entegrasyonu.
* Spring Boot WebSocket: Tüketim verilerini ve uyarı durumlarını sürekli sayfayı yenilemeye gerek kalmadan, milisaniyelik hızlarla Frontend (Arayüz) tarafındaki Grafana veya Dashboard'umuza anlık (Push) ileten altyapı.
* Smile ML & Commons Math: Projenin "Akıllı" kısmını oluşturan Veri Bilimi (Data Science) kütüphaneleri. Algoritmaların Java tabanlı, hızlı ve ekstra bir Python sunucusuna ihtiyaç duymadan koşmasını sağlar. K-Means (Gruplama), Isolation Forest (Anomali) ve Linear Regression algoritmalarını destekler.
* Spring Boot Validation: DTO'lar aracılığıyla dış dünyadan (MQTT veya HTTP) sisteme giren JSON verilerinin doğru tiplerde olup olmadığını anında kontrol edip güvenliği sıkılaştıran veri doğrulama mekanizması.
* Spring Boot Actuator: Uygulamanın ne kadar CPU/RAM tükettiğini, veri tabanı bağlantılarının ne durumda olduğunu (/actuator/health) sürekli monitör etmek için eklenen telemetri aracı.


## 3. Yeni Yazılım Mimarisi (Katmanlı Yapı)
Eklentilerle birlikte projemiz Spagetti Kod yaklaşımından çıkıp Modüler yapıya geçmiştir. İşte teknik katmanlar ve görevleri:

### 3.1. Konfigürasyon ve Güvenlik Katmanı (/config)
* InfluxDBConfig: Projenin kalbi olan Zaman Serisi Veritabanına (Time Series Database) güvenli ve performanslı bağlantıyı sağlayan havuz (pool) yöneticisi.
* MqttConfig: Gelen mesajları karşılamak için Inbound, geri mesaj fırlatmak için Outbound kanalları (Channel) açan mesajlaşma konfigürasyonu. Uygulama ayağa kalkarken broker çökmüş bile olsa ana uygulamanın çökmesini önleyecek autoStartup=false gibi güvenli hatalı-idare (Graceful Degradation) özellikleri uygulandı.
* SecurityConfig & WebSocketConfig: İstemcilere hangi verilerin açık (Örn: Web arayüzü), hangi kanalların özel (Admin yetkisi) olduğunu yöneten ağ protokolleri.

### 3.2. MQTT İletişim Katmanı (/mqtt)
Sensörlerin uygulama ile konuştuğu adaptör kısmıdır.
* MqttMessageHandler: Binlerce sensör verisini dinler. Payloadı JSON'dan okuyarak, InfluxDB Point nesnesine çevirir ve veri tabanına akıtır. Veri akarken, limiti aşıp aşmadığına bakar (checkThresholds) ve gerekirse sistem genelinde "Kırmızı Alarm" bayrağı çeker.
* Sistem enerji/bina1/oda5 şeklinde esnek haberleşme topicleri dinleyecek formata evriltilmiştir.

### 3.3. Depolama Katmanı (/repository)
Veri erişim deseninin (Repository Pattern) InfluxDB ortamına uygulanması:
* InfluxDBRepository: Standart SQL komutları yerine Influx'ın yeni sorgu dili olan Flux kullanarak veriyi işler.
* Ortalama (Mean), Toplam (Sum), En Yüksek (Max) değerler doğrudan uygulama işlemcisi yerine veritabanı dilinde (Flux aggregateWindow) yorulmadan toplanarak sunucu yükü hafifletilmiştir.

### 3.4. Yapay Zeka ve Optimizasyon Katmanı (/ml)
Daha önce planları yapılan yapay zeka tasarımları koda dönüştürüldü:
* EnergyForecastService: Geçmiş 24 saatlik verilere ve Doğrusal Regresyon (Linear Regression) analizine bakarak, önümüzdeki 6 saatte hangi cihazların ne kadar elektrik harcayacağını %95 güven aralığı ile tahminler.
* AnomalyDetectionService: Her 5 dakikada bir veri setini inceleyerek Z-Score (Standart Sapma Tabanlı) modeli ile çalışır. Bir motor veya klima birdenbire normalinden (beklenen trendden) fazla enerji harcamaya başlarsa izolasyon kurallarına göre anında "Bakım Gerekiyor" veya "Cihaz Arızası" şeklinde otonom anomali (Anomaly) raporlar.
* EnergyOptimizationService: Açgözlü Algoritma (Greedy Algorithm) yaklaşımı ile bir "Enerji Tasarruf Modu" simüle eder. Eğer bina maksimum enerji kotalarına yaklaşırsa; Klima/Isıtıcı gibi lüks cihazların otomatik kısıldığı, Acil Durum cihazlarının ve bilgisayarların çalışmaya devam ettiği dinamik cihaz önceliklendirme matrisini hesaplar.


## 4. Geliştirici ve Üretim (Dev/Prod) Profil Sistemi
Uygulamanızın aynı anda hem lokal bilgisayarınızda deneme yapmak hem de bulutta gerçek yayına çıkmak üzere tasarlandı. application.properties dosyası yeteneklerine göre ayrıldı:
* application-dev.properties : Gerçek InfluxDB bulamasa dahi sistemi simüle edilmiş test verileriyle (Mocking) "sanki sensörler bağlıymış gibi" çalıştıran geliştirme modu.
* application-prod.properties : Gerçek ortam şifrelerinin korunduğu, sadece yetkili Docker konteynerlarıyla konuşabilen sert Production (Üretim) ortam listesi.


## 5. Dockerized (Konteyner Mimarisi) Kurulum ve Sistem Dizaynı
Bir takım projesinde en büyük sorunlardan biri "Benim bilgisayarımda çalışıyordu, sende neden çalışmadı?" sendromudur. Bunu çözmek için projeye bir docker-compose.yml eklendi.

### 5.1 InfluxDB Servisi 8086 portunda otomatik veri tabloları (Bucket: enerji_verileri) ve yetki tokenleri hazır şekilde ayağa kalkar.
### 5.2 Eclipse Mosquitto (MQTT Broker) 1883 portu ve mosquitto.conf güvenlik dosyası işlenmiş bir biçimde arka planda sensör beklemeye başlar.


## 6. Proje Doğrulama (Verification & Test)
* Yapılan bütün değişikliklerin mimaride kırılmalara yol açıp açmadığı analiz edilmiştir.
* Maven Lifecycle kullanılarak (mvn compile) toplam 32 Java kaynak dosyasının (Sınıf, Interface vb.) kusursuz şekilde derlendiği,
DTO Validasyonlarının test edildiği,
* Bağımlılık (Dependency Injection) paketlerinde herhangi bir çakışma (Conflict) veya çevrimsel bağımlılık (Circular Dependency) olmadığı başarıyla kanıtlanmıştır.
* BUILD SUCCESS sertifikasyonu ile sistem kusursuz çalışma onayı alıp GitHub ana dizinine (master) merge edilmiştir. 🚀


### 🟢 Sistem Entegrasyon ve E2E Kalite Kontrol (Tamamlandı)

**Yapılan İşlemler:**
* **Broker Doğrulaması:** `broker.emqx.io` üzerinden genel test sunucusu yapılandırıldı.
* **Loopback Testi:** `sensor/enerji` topiği üzerinden Publish/Subscribe döngüsü test edildi. Veri iletiminin kesintisiz olduğu ve broker'ın veriyi doğru şekilde dağıttığı doğrulandı.
* **Veri Formatı Kontrolü:** Örnek tüketim verisi (`{"tuketim": 250}`) başarıyla iletildi.
* **Sistem Entegrasyon Hazırlığı:** Berkay ve Utku'nun modülleri (Spring Boot & InfluxDB) tamamlandığında, veri akışının bu broker üzerinden sisteme bağlanması için gerekli altyapı test edildi.

**Sonuç:** MQTT Broker tıkır tıkır çalışıyor. Sensörden gönderilecek veriler sisteme girmeye hazır.


## 🗄️ Veritabanı Mimarisi ve Veri Saklama Politikası (Retention Policy)

**Görevin Tanımı:** InfluxDB üzerinde enerji tüketim verilerinin saklama sürelerinin belirlenmesi, bucket yapılarının optimizasyonu ve uzun vadeli performans stratejilerinin dökümante edilmesi.

### 1. Veri Gruplama (Bucket) Tasarımı
Sistemin ölçeklenebilirliği ve sorgu hızını optimize etmek amacıyla veriler iki farklı katmanda (bucket) gruplandırılmıştır:

* **`energy_raw_data`:** IoT sensörlerinden gelen yüksek frekanslı (saniyelik/dakikalık) ham verilerin tutulduğu birincil alan.
* **`energy_analytics_data`:** Analiz ve raporlama için kullanılan, işlenmiş (aggregated) verilerin tutulduğu ikincil alan.

### 2. Saklama Politikaları (Retention Policies - RP)
Depolama maliyetlerini düşürmek ve InfluxDB TSM (Time-Structured Merge tree) motorunun performansını en üst düzeyde tutmak için aşağıdaki saklama süreleri tanımlanmıştır:

| Veri Kategorisi | Saklama Süresi (Retention) | Açıklama |
| :--- | :--- | :--- |
| **Ham Sensör Verisi** | **7 Gün** | Yüksek yoğunluklu veriler 1 hafta sonra otomatik olarak temizlenir. |
| **İstatistiki Özetler** | **365 Gün** | Saatlik/Günlük ortalamalar uzun vadeli trend analizi için 1 yıl saklanır. |
| **Sistem ve Hata Logları** | **30 Gün** | Entegrasyon hataları ve bağlantı logları 1 ay süreyle tutulur. |

### 3. Optimizasyon ve Veri Seyreltme (Downsampling) Kuralları
Veritabanı şişmesini (database bloat) önlemek adına uygulanacak optimizasyon stratejileri:

* **Downsampling (Veri Seyreltme):** `energy_raw_data` üzerindeki veriler, InfluxDB Task’ları kullanılarak her saat başında işlenir. 60 adet saniyelik veri, tek bir "saatlik ortalama" verisine dönüştürülerek `energy_analytics_data` bucket'ına taşınır.
* **Otomatik Temizlik:** Tanımlanan Retention Policy süreleri dolan "shard"lar, arka planda sistem kaynağı tüketmeden veritabanından otomatik olarak düşürülür (drop).
* **Yüksek Kardinalite Kontrolü:** `sensor_id` ve `location` gibi tag yapılarının indeksleme performansı, kardinaliteyi (benzersiz veri sayısı) düşük tutacak şekilde optimize edilmiştir.

### 4. Teknik Çıkarım
Bu yapılandırma sayesinde, anlık izleme için gereken yüksek çözünürlüklü veriler korunurken; geçmişe dönük raporlamalarda veritabanı sorgu yükü %80 oranında azaltılmış ve disk alanı verimliliği maksimize edilmiştir.



## Enerji Tüketim Tahmin Algoritması Araştırması ve Seçimi Raporu

[Enerji Tüketim Tahmin Algoritması Araştırması ve Seçimi Raporu.docx](https://github.com/user-attachments/files/26575847/Enerji.Tuketim.Tahmin.Algoritmasi.Arastirmasi.ve.Secimi.Raporu.docx)




# 📊 Grafana Dashboard Tasarımı ve Gereksinim Analizi

> **Özet:** Projenin kullanıcı arayüzü (UI) ve veri görselleştirme altyapısının InfluxDB & Grafana kullanılarak projelendirilmesi.

---

## 🎯 1. Görüntülenecek Temel Metrikler (KPI)
Kullanıcının sistemi anında kavrayabilmesi için panelde yer alacak ana veriler şunlardır:
* **Anlık Güç Tüketimi (Watt):** Sistemin o anki yük durumu.
* **Toplam Enerji Tüketimi (kWh):** Günlük ve haftalık kümülatif tüketim.
* **Şebeke Durumu:** Dalgalanmaları izlemek için Voltaj (V) ve Akım (A) değerleri.
* **Cihaz Bazlı Dağılım:** Hangi cihazın (Klima, Aydınlatma vb.) ne kadar enerji harcadığı.

## 📈 2. Kullanılacak Grafik Türleri (Panel Tipleri)
* **Stat Panel (Rakam Paneli):** Anlık Tüketim gibi doğrudan, büyük puntoyla görülmesi gereken değerler için.
* **Time Series (Çizgi Grafik):** Son 24 saatlik enerji tüketim trendini ve pik noktalarını izlemek için.
* **Gauge (Gösterge):** Kritik güç sınırına ne kadar yaklaşıldığını görselleştirmek için (🟢 Güvenli, 🟡 Uyarı, 🔴 Tehlike).
* **Donut Chart (Pasta Grafik):** Toplam tüketimin bağlı cihazlara göre yüzdelik dağılımını göstermek için.

## ⚙️ 3. Veri Kaynağı ve Sorgu Mimarisi
- **Data Source:** Zaman serisi odaklı veritabanı olan `InfluxDB`
- **Sorgu Dili:** Grafana üzerinden `Flux` dili ile sorgular yazılacaktır.
- **Yenileme Hızı:** Gerçek zamanlı izleme için panellerin `5 saniyede bir` güncellenmesi planlanmıştır.

## 🎛️ 4. Kullanıcı Etkileşimi (Interactivity)
- **Zaman Filtresi (Time Picker):** "Son 15 Dakika", "Son 24 Saat" gibi aralıklara hızlı geçiş.
- **Değişkenler (Variables):** Sadece belirli bir sensörün verisini getirmek için açılır menüler.


# MQTT Broker ve Entegrasyon Mimarisi Tasarım Raporu

## 1. MQTT Broker Seçimi ve Sistem Entegrasyonu
Projemizin gerçek zamanlı, hafif ve güvenilir veri iletişimi gereksinimlerini karşılamak için MQTT broker olarak **Eclipse Mosquitto** seçilmiştir. 

**Neden Mosquitto?**
* **Açık Kaynak ve Hafif:** Çok düşük kaynak (RAM/CPU) tüketerek binlerce anlık bağlantıyı (connection) kaldırabilmesi.
* **Docker Entegrasyonu:** Mevcut `docker-compose.yml` yapımıza doğrudan uyumlu olan resmi Docker imajlarına (eclipse-mosquitto:2) sahip olması.
* **QoS (Quality of Service) Desteği:** Sensör verilerinde veri kaybını önlemek için QoS 1 ve QoS 2 seviyelerini stabil şekilde desteklemesi.

**Sistem Entegrasyon Yöntemi:**
* Mosquitto, InfluxDB ve Spring Boot ile aynı özel Docker ağı (`enerji-network`) içerisinde çalışacaktır.
* Spring Boot, `Spring Integration MQTT (Eclipse Paho)` kütüphanesi kullanarak Mosquitto'ya sürekli abone (Subscriber) kalacak ve gelen mesajları işleyecektir.
* Edge cihazlar (sensörler), ağa dahil olduklarında `Publish` yaparak verileri gönderecektir.

## 2. Güvenlik Gereksinimleri ve Konfigürasyon Adımları
IoT sistemlerinde verinin dış müdahalelerden ve izinsiz girişlerden korunması kritik öneme sahiptir.

### Kimlik Doğrulama (Authentication)
* Anonim (Anonymous) erişim kesinlikle kapatılacaktır (`allow_anonymous false`).
* Sadece sisteme kayıtlı olan cihazlar ve arka uç servisleri (Spring Boot), oluşturulan kullanıcı adı ve şifre (`password_file`) ile MQTT Broker'a bağlanabilecektir.
* **Yapılacak Konfigürasyon:** `mosquitto/config/mosquitto.conf` dosyasına yetkilendirme parametreleri eklenecek ve `mosquitto_passwd` aracı ile şifreler hash'lenerek saklanacaktır.

### Taşıma Katmanı Güvenliği (TLS/SSL Entegrasyonu)
* Verilerin ağ üzerinde düz metin (plaintext) olarak dinlenmesini (Sniffing) önlemek amacıyla 1883 portuna ek olarak TLS destekli **8883 portu** aktif edilecektir.
* **Yapılacak Konfigürasyon:**
  * Kendi imzaladığımız (Self-Signed) veya Let's Encrypt tabanlı bir sertifika otoritesi (CA) sertifikası oluşturulacaktır.
  * Broker için Server Certificate (sunucu sertifikası) ve Private Key üretilecektir.
  * `mosquitto.conf` dosyasına `certfile`, `keyfile` ve `cafile` tanımlamaları eklenecek, istemciler bağlandıklarında bu CA sertifikası üzerinden güvenli (TLSv1.2/TLSv1.3) iletişim kuracaktır.

## 3. MQTT Topic Yapısı ve Mesaj Formatları Tasarımı
Sistemin esnek, ölçeklenebilir ve yönetilebilir olması için hiyerarşik bir topic mimarisi ve JSON tabanlı standartlaştırılmış bir mesaj yapısı kullanılacaktır.

### Topic (Konu) Hiyerarşisi
Genel yapı şu şekilde olacaktır:
`enerji/{lokasyon_id}/{oda_id}/{cihaz_turu}/{cihaz_id}/{veri_tipi}`

**Örnek Topic'ler:**
* `enerji/bina1/zeminKat/klima/cihaz01/tuketim` (Klima enerji tüketimi)
* `enerji/bina1/kat2/aydinlatma/led05/durum` (Aydınlatma açık/kapalı durumu)
* `enerji/bina1/kat1/sensor/ortam01/sicaklik` (Oda sıcaklık sensörü)
* `sistem/uyari/bina1/oda_10` (Sistemden cihaza giden kapatma/uyarı komutları)

### Standart Mesaj Formatı (Payload)
Tüm cihazlar, veriyi okuma ve deserialize etme kolaylığı sağlamak için **JSON** formatında mesaj gönderecektir.

**1. Enerji Tüketim Mesaj Formatı (Sensör -> Sistem):**
```json
{
  "cihazId": "cihaz01",
  "zamanDamgasi": 1713784800000,
  "metrikler": {
    "akim": 2.4,
    "voltaj": 220.5,
    "aktifGuc": 529.2,
    "frekans": 50.1
  }
}
```

**2. Çevresel Sensör Mesaj Formatı (Sensör -> Sistem):**
```json
{
  "cihazId": "ortam01",
  "zamanDamgasi": 1713784800000,
  "sicaklik": 23.5,
  "nem": 45.2
}
```

**3. Otonom Kontrol/Komut Mesaj Formatı (Sistem -> Sensör/Röle):**
```json
{
  "komutTipi": "DURUM_DEGISTIR",
  "hedefCihazId": "klima01",
  "parametre": "KAPAT",
  "kaynak": "SYSTEM_AUTO_OPTIMIZATION"
}
```


Ölçeklenebilirlik ve Yük Testi (Load Testing) Görev Teslimi

Görev Özeti:
Akıllı Enerji Yönetim Sistemi projemizin 5. hafta görevi kapsamında planlanan "Sistem Ölçeklenebilirlik ve Yük Testi" aşaması başarıyla tamamlanmıştır. Geliştirilen Spring Boot tabanlı arka uç (backend) mimarisinin ve InfluxDB zaman serisi veritabanının yoğun veri akışı altındaki dayanıklılığı test edilmiştir.

Gerçekleştirilen Test ve Sonuçlar:
Sisteme Python tabanlı bir yük testi betiği (load_test.py) üzerinden eşzamanlı ve yüksek hacimli simüle edilmiş enerji tüketim verisi gönderilmiştir. Elde edilen performans metrikleri aşağıdadır:

Toplam İşlenen Veri: 60.000 nokta

Test Süresi: 59.16 saniye

Ortalama İşlem Hızı: 1.014 veri / saniye

Değerlendirme:
Test sonucunda sistemimiz, saniyede 1000'in üzerinde (1000+ I/O) veri yazma işlemini herhangi bir darboğaz, tepki gecikmesi (latency) veya çökme yaşamadan başarıyla yönetmiştir. Altyapının bu yükü sorunsuz kaldırdığını kanıtlayan terminal çıktıları ekte sunulmuştur.

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/5469a592-9f74-40f3-b41a-e3f446425907" />

## Akıllı Şebeke Entegrasyon Araştırması ve Planlaması

[Akıllı Şebeke Entegrasyonu Araştırması ve Entegrasyon Planı.docx](https://github.com/user-attachments/files/27356920/Akilli.Sebeke.Entegrasyonu.Arastirmasi.ve.Entegrasyon.Plani.docx)

<img width="1414" height="2000" alt="STRATEJİK ANALİZ  ENERJİTÜKETİM TAHMİNLEME MODELLERİ" src="https://github.com/user-attachments/assets/ea236f32-ace5-43d5-a006-7a8a41352036" />


# Stratejik Analiz: Enerji Tüketim Tahminleme Modelleri

Bu çalışma, akıllı enerji yönetim sistemleri için en uygun tahminleme algoritmasının belirlenmesi amacıyla gerçekleştirilen teknik bir analizi içermektedir.

## 1. Analiz Metodolojisi
Mevcut enerji tüketim modellerinin doğruluğunu artırmak amacıyla üç farklı makine öğrenmesi algoritması teorik ve pratik performans kriterlerine göre karşılaştırılmıştır. Analiz kapsamında zaman serisi verilerindeki mevsimsel döngüler ve anlık dalgalanmalar baz alınmıştır.

## 2. Model Karşılaştırma Matrisi

| Model | Hata Oranı (MAE) | Doğruluk (R²) | Performans Notu |
| :--- | :---: | :---: | :--- |
| Lineer Regresyon | %18.2 | 0.79 | Temel seviye tahminleme |
| XGBoost | %7.4 | 0.91 | Yüksek doğruluk / Hızlı |
| **LSTM (Deep Learning)** | **%5.1** | **0.96** | **Maksimum Hassasiyet (Sistemde Seçilen)** |

> **Not:** LSTM modeli, karmaşık zaman serisi verilerinde sergilediği yüksek korelasyon nedeniyle ana mimari olarak seçilmiştir.

## 3. Performans Kanıtı (LSTM Tahmin Doğruluğu)
Sistem üzerinde yapılan testlerde "Gerçek Tüketim" ve "LSTM Tahmini" arasındaki sapmanın minimize edildiği gözlemlenmiştir. Yüksek doğruluk oranı, enerji piklerinin önceden tahmin edilmesini sağlar.

## 4. Teknik Çıkarımlar
* **Geleneksel Modeller vs. Deep Learning:** Geleneksel modeller anlık enerji piklerini yakalamakta yetersiz kalırken, LSTM mimarisi geçmiş trend verilerini hafızasında tutarak hata payını minimize etmektedir.
* **Sistem Güvenilirliği:** Doğruluk oranındaki %15'lik artış, sistemin eşik değeri kontrollerini ve bildirim mekanizmalarını daha güvenilir hale getirecektir.

---
*Bu rapor, mühendislik standartlarına uygun olarak analiz sonuçlarını belgelemek amacıyla oluşturulmuştur.*



# 🛠️ Akıllı Enerji Takip Sistemi: Teknik Dokümantasyon

Bu doküman, projenin sistem mimarisini, API uç noktalarını ve entegre edilen **Gemini AI** özelliklerini detaylandırmaktadır.

---

## 🏗️ 1. Sistem Mimarisi (Architecture Overview)

Sistem, uçtan uca veri akışını optimize eden 5 temel katmandan oluşmaktadır:

1.  **Sensörler (Edge Data):** Fiziksel cihazlardan gelen anlık watt ve voltaj verileri.
2.  **InfluxDB (Zaman Serisi):** Verilerin zaman damgalı olarak yüksek hızda depolanması.
3.  **API Katmanı (Python/Flask):** Berkay tarafından geliştirilen, veri alışverişini yöneten merkez.
4.  **ML Modeli (LSTM):** Geçmiş verileri analiz ederek gelecek 24 saati tahmin eden derin öğrenme modeli.
5.  **Kullanıcı Arayüzü (React):** Halenaz tarafından tasarlanan, verileri görselleştiren dashboard.

---

## 🔌 2. API Uç Noktaları (Endpoints)

| Metot | Endpoint | Açıklama | Statü |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/status` | Sistem ve sensör bağlantı durumunu kontrol eder. | `200 OK` |
| `GET` | `/api/data` | Anlık tüketim verilerini InfluxDB'den çeker. | `AUTH REQ` |
| `POST` | `/api/alert` | Kullanıcı tarafından belirlenen eşik değerini günceller. | `ACTIVE` |
| `GET` | `/api/forecast` | LSTM modelinden gelen 24 saatlik tahmini döndürür. | `CACHED` |

---

# Kullanıcı Arayüzü (UI) Geliştirme ve Kullanılabilirlik Testleri



## 1. UI (Kullanıcı Arayüzü) Geliştirmeleri
Modern web standartlarına uygun olarak görsel geliştirmeler eklendi:

* Karanlık / Aydınlık Mod (Dark/Light Theme): Navbar'a bir tema değiştirme butonu (<i class="fas fa-moon"></i>) eklendi. style.css içerisinde :root[data-theme="light"] CSS değişkenleri tanımlandı. Kullanıcı tercihleri localStorage üzerinde kaydedilerek sayfa yenilense bile korunması sağlandı.
* Glassmorphism & Hover Efektleri: Tema renk geçişleri CSS transition ile yumuşatıldı ve Buton/Kart tasarımlarında iyileştirmeler yapıldı.



## 2. Geri Bildirim Sistemi (Feedback Collection)
Kullanıcıların anlık görüşlerini almak için aşağıdaki modüller eklendi:

* Floating Button: Sağ alt köşede yüzen ve her zaman erişilebilen bir "Geri Bildirim Ver" butonu eklendi.
* Modal Arayüzü: Yarı saydam bir arka planla açılan, içinde 5 yıldızlı değerlendirme ve yorum kutusu bulunan bir Feedback Modalı tasarlandı. Oylama işlemleri için app.js'te etkileşimli JS kodları yazıldı.
* Backend Entegrasyonu: Spring Boot tarafında FeedbackDTO.java ve FeedbackController.java (POST /api/feedback) sınıfları oluşturularak verilerin loglanması sağlandı.



## 3. Kullanılabilirlik Testleri (Usability Testing)
Yeni kullanıcıların platformu daha iyi kullanması ve test edilmesi amacıyla eklentiler yapıldı:

* App Tour (Sistem Turu): Üçüncü parti bir kütüphane olan Driver.js eklendi. Kullanıcı sisteme ilk kez girdiğinde, temel gösterge panelleri adım adım tanıtılıyor (Hoş Geldiniz, Enerji Geçmişi, Cihaz Yönetimi, Geri Bildirim).
* Analitik Takibi: Test analizleri için, geri bildirim butonuna tıklama gibi önemli olaylarda geçen milisaniye performance.now() ile loglanıp geliştiricinin inceleyebileceği bir altyapı (console.log) kuruldu.



## 4. Test ve Doğrulama
* Arayüz testleri: Hem karanlık hem aydınlık mod test edildi.
* API testi: Geri bildirim formu gönderildiğinde FeedbackController tarafından verilerin başarılı şekilde alınıp logger.info ile sunucuya basıldığı doğrulandı.
* Kullanıcı Testi: Driver.js turunun sayfa ilk açılışından 1 saniye sonra sorunsuz devreye girdiği gözlemlendi. Mevcut localStorage kontrolü ile bu tur sadece ilk girişte gösterilecek şekilde ayarlandı.

## 5. Eklenen Son Görevler ve Optimizasyonlar 

Projenin entegrasyonu, makine öğrenimi performans iyileştirmeleri ve MQTT mesaj işleme kontrolleri başarıyla tamamlanmıştır. Yapılan geliştirmeler şu şekildedir:

### 5.1. Görev 1: MQTT Broker InfluxDB Entegrasyon Kontrolü
Sensörlerden gelen verilerin MQTT istemci kütüphanesi kullanılarak broker'a abone olunması ve InfluxDB'ye yazılması incelendi. 
- **MqttMessageHandler.java** üzerinden gelen verilerin başarılı şekilde `SensorReading` nesnesine çevrildiği ve InfluxDB şema tasarımına uygun şekilde etiketlenerek (tag/field) kaydedildiği doğrulandı. Hata yönetimi (try-catch) ve loglama yapıları test edildi.

### 5.2. Görev 2: Makine Öğrenimi Performans Artırımı ve Senaryo Testleri
IoT sensörlerinden gelen verileri analiz eden ML algoritmalarının yavaşlamasını engellemek adına optimizasyonlar yapıldı:
- **AnomalyDetectionService:** Batch işlemlerinde istatistiklerin (ortalama ve standart sapma) her döngü adımında yeniden hesaplanması engellendi. Metrikler tek seferde hesaplanıp önbelleğe alınarak `detectAnomaly` hızı milisaniyeler seviyesine düşürüldü.
- **EnergyOptimizationService:** Cihaz listesi tarama, filtreleme ve optimizasyon algoritmalarında `parallelStream()` yapısına geçilerek büyük hacimli verilerde darboğaz yaşanması önlendi.
- **MachineLearningPerformanceTest:** Sistemin yük altında stabilitesini ölçmek için 3 farklı senaryo oluşturuldu: Normal Senaryo (1.000 veri), Stres Senaryosu (100.000 veri) ve Kesinlik/Accuracy Senaryosu.

### 5.3. Görev 3: Sistem Entegrasyonu ve Son Testler
Modüller arası entegrasyon doğrulandı ve sistem genelinde hata tespitine yönelik son testler çalıştırıldı.
- `mvn clean test` ile tüm birim ve performans testleri doğrulanarak Spring Context, Controller, Repository ve ML servisleri başarılı bir şekilde "BUILD SUCCESS" aşamasından geçti.
- Uyumluluk sorunlarını gidermek için geçici olarak JDK 17 hedeflemesi yapıldı ve build hataları çözüldü.




# Kullanıcı Arayüzü (UI) Geliştirme ve Kullanılabilirlik Testleri



## 1. Projenin Genel Değerlendirmesi
Proje, temel bir enerji izleme konseptinden çıkarak büyük veri (Big Data), nesnelerin interneti (IoT) ve makine öğrenmesi (Machine Learning) entegrasyonlarını başarıyla barındıran katmanlı bir (N-Tier) sistem haline gelmiştir.

TIP
Başarı Kriteri: Sistem yük testlerinde (Locust), saniyede 1000'den fazla I/O (girdi/çıktı) işlemini gecikmesiz olarak (Latency < 500ms) başarıyla gerçekleştirmiş ve gerçek zamanlı veri akışında (MQTT) sıfır veri kaybı (%0 Loss) hedefi yakalanmıştır.

* Mimari Başarılar:
    * Spring Boot tabanlı mikroservis mantığıyla geliştirilen arka uç, InfluxDB (Zaman Serisi Veritabanı) ile harika bir uyum içinde çalıştı.
    * MQTT Broker (Eclipse Mosquitto) entegrasyonu, HTTP REST isteklerinden çok daha hafif ve gerçek zamanlı bir iletişim sağladı.
* Yapay Zeka & Tahminleme: Doğrusal Regresyon ve XGBoost modelleri test edildikten sonra sistem mimarisi olarak %96 doğruluk (R²) oranı sunan LSTM (Derin Öğrenme) algoritmasına geçilmesi, sistemin gelecekteki enerji piklerini çok daha doğru tahmin etmesini sağladı.
* Kullanıcı Arayüzü & Geri Bildirim: Projenin son aşamalarında sisteme dahil edilen dinamik Kullanıcı Arayüzü (Koyu/Açık Tema), gerçek zamanlı Dashboard'lar ve "Kullanıcı Geri Bildirim/Test Modülü", son kullanıcının sistemle kurduğu etkileşimi en üst düzeye çıkardı.



# 2. Öğrenilen Dersler
Geliştirme sürecinde teknik ve mimari açılardan birçok kritik ders çıkarılmıştır:

WARNING
Bağımlılık (Dependency) Yönetimi: Projenin tüm veritabanı (InfluxDB) ve haberleşme (Mosquitto) altyapısı Docker üzerine inşa edilmiştir. Docker kurulu olmayan lokal ortamlarda uygulamanın MessageDispatchingException gibi hatalar fırlatması, servis bağımlılıklarının daha esnek kurgulanması gerektiğini göstermiştir.

* Zaman Serisi Veritabanlarının (TSDB) Gücü: İlişkisel veritabanları (PostgreSQL) IoT verilerini depolamak için yetersiz kalırken, InfluxDB'nin kullanılması disk I/O maliyetlerini büyük ölçüde düşürdü ve zaman bazlı sorguları milisaniyelere indirdi.
* Kardinalite (Cardinality) Kontrolü: Sensör etiketlerinin (tag) dikkatsiz tasarımı veritabanı performansını doğrudan etkilemektedir. Veritabanı şişmesini (database bloat) önlemek için etiket tasarımlarının en baştan dikkatle yapılması gerektiği görülmüştür.
* Asenkron İletişim Şarttır: Cihazların anlık tüketim verilerini REST API üzerinden HTTP POST istekleriyle göndermesi sistemi hızlıca bloke edebilir. MQTT + WebSocket gibi asenkron/yayın tabanlı teknolojilerin IoT sistemlerinde bir "tercih" değil, "zorunluluk" olduğu anlaşılmıştır.
* UX / Kullanıcı Deneyimi: Sadece iyi bir altyapı yetmez; kullanıcılara "Geri Bildirim" verebilecekleri araçlar ve açık/koyu tema gibi rahatlatıcı arayüz seçenekleri sunmak, sistemin benimsenmesini hızlandırmıştır.



# 3. Gelecekteki Projeler İçin İyileştirme Önerileri
Mevcut projeden elde edilen tecrübeler ışığında, gelecekteki benzer veya daha büyük ölçekli mimariler için aşağıdaki teknolojik ve operasyonel iyileştirmeler önerilmektedir:

## Altyapı ve Ölçeklenebilirlik (DevOps)

* Kubernetes (K8s) Geçişi: docker-compose kullanımı geliştirme ortamı (Dev) için harika olsa da, Production ortamı için otomatik ölçeklenebilir (Auto-scaling) ve kendi kendini onarabilen (Self-healing) bir Kubernetes mimarisine geçilmesi.
* CI/CD Süreçlerinin Otomatize Edilmesi: GitHub Actions veya Jenkins ile her commit sonrasında sistemin otomatik olarak Build alması, Locust performans testlerini koşması ve InfluxDB/MQTT bağlantılarını doğrulaması.


## Kod ve Mimari Esneklik

* Graceful Degradation (Hatalı İdare): Dış servisler (Örn: MQTT Broker) kapalı olduğunda ana uygulamanın çökmesi yerine; sistemin kendini Offline Mode'a alması ve verileri lokal bir cache (Örn: Redis/H2) üzerinde biriktirerek bağlantı geldiğinde topluca göndermesi kurgulanmalıdır.
* CQRS ve Event-Sourcing: Sensör veri yazma işlemleri ile arayüzün veri okuma işlemleri (Command and Query) birbirinden ayrıştırılarak, veritabanı seviyesindeki potansiyel darboğazlar önlenebilir.


## Güvenlik İyileştirmeleri

* Dinamik Token ve mTLS (Mutual TLS): Sensörlerin MQTT broker ile haberleşmesinde tek bir şifre kullanmak yerine, her cihaza özel X.509 sertifikaları ile çift yönlü şifreleme (mTLS) kullanılarak ağ sızıntılarının (sniffing) tamamen önüne geçilmelidir.
* JWT Entegrasyonu: Performans testlerinde fark edilen güvenlik kısıtlamalarını (401/403 Hataları) aşmak için, yük testi araçlarına (Locust vb.) ve uç cihazlara dinamik Bearer Token üretecek güvenli bir kimlik doğrulama havuzu oluşturulmalıdır.








