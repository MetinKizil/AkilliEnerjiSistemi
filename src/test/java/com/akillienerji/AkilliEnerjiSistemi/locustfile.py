from locust import HttpUser, task, between
import random

class AkilliEnerjiTester(HttpUser):
    # Sanal cihazların istek göndermeden önce bekleyeceği süre (1-3 saniye arası)
    wait_time = between(1, 3) 

    @task
    def test_energy_check(self):
        # 10.0 ile 200.0 arasında rastgele bir tüketim değeri (consumption) üretiyoruz
        tuketim_degeri = round(random.uniform(10.0, 200.0), 2)
        
        # Senin Java API'ne uygun uç nokta (endpoint)
        endpoint_url = f"/api/energy/check?consumption={tuketim_degeri}"
        
        # POST isteğini gönderiyoruz
        with self.client.post(endpoint_url, catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Hata! Kod: {response.status_code}")