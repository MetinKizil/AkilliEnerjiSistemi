import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/energy")
public class EnergyController {

    @Autowired
    private NotificationService notificationService;

    // Sadece yetkili personelin (yukarıdaki ayara göre) erişebileceği endpoint
    @PostMapping("/check")
    public String checkData(@RequestParam double consumption) {
        double MAX_LIMIT = 500.0; // Örnek limit
        
        // Tüketimi kontrol et, aşarsa bildirim servisini tetikle
        notificationService.checkEnergyLimit(consumption, MAX_LIMIT);
        
        return "Veri alındı ve kontrol edildi. Tüketim: " + consumption;
    }
}