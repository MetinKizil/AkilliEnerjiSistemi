import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    // Gerçek senaryoda burada JavaMailSender veya SMS API kullanılır
    public void sendAlert(String message) {
        System.out.println("🚨 KRİTİK UYARI BİLDİRİMİ GÖNDERİLDİ 🚨");
        System.out.println("Mesaj: " + message);
        // Örn: emailService.send("admin@sirket.com", "Enerji Limiti Aşıldı", message);
    }

    public void checkEnergyLimit(double currentConsumption, double limit) {
        if (currentConsumption > limit) {
            String alertMessage = "Dikkat! Anlık enerji tüketimi (" + currentConsumption + 
                                  " kW) belirlenen limiti (" + limit + " kW) aştı!";
            sendAlert(alertMessage);
        }
    }
}