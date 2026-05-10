/* ================================================
   APP.JS - Ana Uygulama Mantigi
   Tum butonlarin islevleri burada tanimlanir
   ================================================ */

// Sayfa yuklendiginde baslat
document.addEventListener('DOMContentLoaded', () => {
    if (!Auth.isLoggedIn()) return;

    App.init();
});

const App = {
    chart: null,
    currentHours: 6,
    refreshInterval: null,

    /* ==========================================
       INITIALIZATION
       ========================================== */
    init() {
        this.setupUserInfo();
        this.setupEventListeners();
        this.loadAllData();
        this.startAutoRefresh();
        
        // Yeni eklenen moduller
        this.setupTheme();
        this.setupFeedbackSystem();
        setTimeout(() => this.startAppTour(), 1000); // 1 saniye sonra tur baslasin
    },

    /* ==========================================
       KULLANICI BILGISI
       ========================================== */
    setupUserInfo() {
        const navUsername = document.getElementById('navUsername');
        if (navUsername) {
            navUsername.textContent = Auth.getUsername();
        }

        // Admin ise esik degeri ayarlarini goster
        if (Auth.isAdmin()) {
            const thresholdSection = document.getElementById('thresholdSection');
            if (thresholdSection) {
                thresholdSection.style.display = 'block';
            }
        }
    },

    /* ==========================================
       EVENT LISTENERS - TUM BUTONLAR
       ========================================== */
    setupEventListeners() {
        // 1) CIKIS BUTONU
        const logoutBtn = document.getElementById('logoutBtn');
        if (logoutBtn) {
            logoutBtn.addEventListener('click', () => {
                Auth.logout();
            });
        }

        // 2) VERILERI YENILE BUTONU
        const btnRefreshAll = document.getElementById('btnRefreshAll');
        if (btnRefreshAll) {
            btnRefreshAll.addEventListener('click', () => {
                this.loadAllData();
                this.showToast('success', 'Tum veriler guncellendi');
            });
        }

        // 3) GRAFIK YENILE BUTONU
        const btnRefreshChart = document.getElementById('btnRefreshChart');
        if (btnRefreshChart) {
            btnRefreshChart.addEventListener('click', () => {
                btnRefreshChart.classList.add('spinning');
                this.loadChartData(this.currentHours).then(() => {
                    setTimeout(() => btnRefreshChart.classList.remove('spinning'), 500);
                });
            });
        }

        // 4) GRAFIK SAAT BUTONLARI (6, 12, 24, 48 saat)
        document.querySelectorAll('.chart-btn[data-hours]').forEach(btn => {
            btn.addEventListener('click', () => {
                document.querySelectorAll('.chart-btn[data-hours]').forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
                this.currentHours = parseInt(btn.dataset.hours);
                this.loadChartData(this.currentHours);
            });
        });

        // 5) CSV DISA AKTAR BUTONU
        const btnExportCSV = document.getElementById('btnExportCSV');
        if (btnExportCSV) {
            btnExportCSV.addEventListener('click', () => {
                this.exportCSV();
            });
        }

        // 6) ENERJI KONTROL BUTONU
        const btnCheckEnergy = document.getElementById('btnCheckEnergy');
        if (btnCheckEnergy) {
            btnCheckEnergy.addEventListener('click', () => {
                this.checkEnergy();
            });
        }

        // 7) ESIK DEGERI KAYDET BUTONU
        const btnSaveThreshold = document.getElementById('btnSaveThreshold');
        if (btnSaveThreshold) {
            btnSaveThreshold.addEventListener('click', () => {
                this.saveThreshold();
            });
        }
    },

    /* ==========================================
       VERI YUKLEME FONKSIYONLARI
       ========================================== */

    // Tum verileri yukle
    async loadAllData() {
        await Promise.all([
            this.loadDashboardSummary(),
            this.loadDevices(),
            this.loadAlerts(),
            this.loadChartData(this.currentHours),
            this.loadThreshold()
        ]);
    },

    // KPI kartlarini guncelle
    async loadDashboardSummary() {
        try {
            const response = await Auth.apiRequest('/api/dashboard/summary');
            if (!response || !response.ok) return;
            const data = await response.json();

            this.updateKPI('valConsumption', data.totalConsumption);
            this.updateKPI('valProduction', data.totalProduction);
            this.updateKPI('valBattery', data.batteryLevel);
            this.updateKPI('valCost', data.dailyCost);

            // Batarya bar
            const batteryBar = document.getElementById('batteryBar');
            if (batteryBar) {
                batteryBar.style.width = data.batteryLevel + '%';
            }

            // Cihaz sayisi
            const deviceCount = document.getElementById('deviceCount');
            if (deviceCount) {
                deviceCount.textContent = data.activeDevices + '/' + data.totalDevices + ' Aktif';
            }

            // Alert badge
            const alertBadge = document.getElementById('alertBadge');
            if (alertBadge) {
                alertBadge.textContent = data.activeAlerts;
                alertBadge.style.display = data.activeAlerts > 0 ? 'inline-block' : 'none';
            }
        } catch (err) {
            console.error('Dashboard summary yuklenemedi:', err);
        }
    },

    updateKPI(elementId, value) {
        const el = document.getElementById(elementId);
        if (el) {
            const oldVal = parseFloat(el.textContent) || 0;
            el.textContent = value;

            // Animasyonlu guncelleme efekti
            el.style.transition = 'none';
            el.style.transform = 'scale(1.1)';
            setTimeout(() => {
                el.style.transition = 'transform 0.3s ease';
                el.style.transform = 'scale(1)';
            }, 50);
        }
    },

    /* ==========================================
       CIHAZ YONETIMI - TOGGLE BUTONLARI
       ========================================== */

    async loadDevices() {
        try {
            const response = await Auth.apiRequest('/api/devices');
            if (!response || !response.ok) return;
            const devices = await response.json();
            this.renderDevices(devices);
        } catch (err) {
            console.error('Cihazlar yuklenemedi:', err);
        }
    },

    renderDevices(devices) {
        const deviceList = document.getElementById('deviceList');
        if (!deviceList) return;

        if (devices.length === 0) {
            deviceList.innerHTML = `
                <div class="empty-state">
                    <i class="fas fa-plug"></i>
                    <span>Bagli cihaz bulunamadi</span>
                </div>`;
            return;
        }

        deviceList.innerHTML = devices.map(device => `
            <div class="device-item" id="device-${device.id}">
                <div class="device-info">
                    <div class="device-icon">
                        <i class="fas ${this.getDeviceIcon(device.type)}"></i>
                    </div>
                    <div class="device-details">
                        <span class="device-name">${device.name}</span>
                        <div class="device-meta">
                            <span><i class="fas fa-map-marker-alt"></i> ${device.location}</span>
                            <span><i class="fas fa-tag"></i> ${device.type}</span>
                        </div>
                    </div>
                </div>
                <div class="device-controls">
                    <span class="device-power">${device.powerConsumption} kW</span>
                    <label class="toggle-switch">
                        <input type="checkbox" ${device.active ? 'checked' : ''}
                               onchange="App.toggleDevice('${device.id}')">
                        <span class="toggle-slider"></span>
                    </label>
                </div>
            </div>
        `).join('');
    },

    getDeviceIcon(type) {
        const icons = {
            'HVAC': 'fa-fan',
            'LIGHTING': 'fa-lightbulb',
            'HEATER': 'fa-fire',
            'APPLIANCE': 'fa-blender',
            'ENTERTAINMENT': 'fa-tv'
        };
        return icons[type] || 'fa-plug';
    },

    // TOGGLE BUTONU - Cihaz ac/kapa
    async toggleDevice(deviceId) {
        try {
            const response = await Auth.apiRequest(`/api/devices/${deviceId}/toggle`, {
                method: 'POST'
            });

            if (response && response.ok) {
                const device = await response.json();
                const status = device.active ? 'acildi' : 'kapatildi';
                this.showToast('success', `${device.name} ${status}`);

                // KPI'lari guncelle
                this.loadDashboardSummary();
            } else {
                this.showToast('error', 'Cihaz durumu degistirilemedi');
                // Toggle'i geri al
                this.loadDevices();
            }
        } catch (err) {
            this.showToast('error', 'Cihaz isleminde hata olustu');
            this.loadDevices();
        }
    },

    /* ==========================================
       UYARI/ALARM YONETIMI
       ========================================== */

    async loadAlerts() {
        try {
            const response = await Auth.apiRequest('/api/alerts');
            if (!response || !response.ok) return;
            const alerts = await response.json();
            this.renderAlerts(alerts);
        } catch (err) {
            console.error('Uyarilar yuklenemedi:', err);
        }
    },

    renderAlerts(alerts) {
        const alertList = document.getElementById('alertList');
        if (!alertList) return;

        if (alerts.length === 0) {
            alertList.innerHTML = `
                <div class="empty-state">
                    <i class="fas fa-check-circle"></i>
                    <span>Aktif uyari bulunmuyor</span>
                </div>`;
            return;
        }

        alertList.innerHTML = alerts.map(alert => `
            <div class="alert-item severity-${alert.severity} ${alert.acknowledged ? 'acknowledged' : ''}" id="alert-${alert.id}">
                <div class="alert-icon">
                    <i class="fas ${this.getAlertIcon(alert.severity)}"></i>
                </div>
                <div class="alert-content">
                    <span class="alert-message">${alert.message}</span>
                    <span class="alert-time">
                        <i class="fas fa-clock"></i>
                        ${this.formatTime(alert.timestamp)}
                    </span>
                </div>
                <div class="alert-actions">
                    ${!alert.acknowledged ?
                        `<button class="acknowledge-btn" onclick="App.acknowledgeAlert('${alert.id}')">
                            <i class="fas fa-check"></i> Onayla
                        </button>` :
                        '<span style="font-size:0.72rem;color:var(--accent-green)"><i class="fas fa-check-circle"></i></span>'
                    }
                </div>
            </div>
        `).join('');
    },

    getAlertIcon(severity) {
        return {
            'CRITICAL': 'fa-exclamation-circle',
            'WARNING': 'fa-exclamation-triangle',
            'INFO': 'fa-info-circle'
        }[severity] || 'fa-bell';
    },

    formatTime(timestamp) {
        if (!timestamp) return '';
        const date = new Date(timestamp);
        const now = new Date();
        const diff = Math.floor((now - date) / 60000); // dakika farki

        if (diff < 1) return 'Az once';
        if (diff < 60) return diff + ' dakika once';
        if (diff < 1440) return Math.floor(diff / 60) + ' saat once';
        return date.toLocaleDateString('tr-TR') + ' ' + date.toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' });
    },

    // ALARM ONAYLA BUTONU
    async acknowledgeAlert(alertId) {
        try {
            const response = await Auth.apiRequest(`/api/alerts/${alertId}/acknowledge`, {
                method: 'POST'
            });

            if (response && response.ok) {
                this.showToast('success', 'Uyari onaylandi');
                this.loadAlerts();
                this.loadDashboardSummary();
            } else {
                this.showToast('error', 'Uyari onaylanamadi');
            }
        } catch (err) {
            this.showToast('error', 'Uyari isleminde hata olustu');
        }
    },

    /* ==========================================
       ESIK DEGERI AYARLARI
       ========================================== */

    async loadThreshold() {
        try {
            const response = await Auth.apiRequest('/api/alerts/threshold');
            if (!response || !response.ok) return;
            const config = await response.json();

            const thresholdMax = document.getElementById('thresholdMax');
            const thresholdWarning = document.getElementById('thresholdWarning');
            const thresholdCritical = document.getElementById('thresholdCritical');

            if (thresholdMax) thresholdMax.value = config.maxConsumption;
            if (thresholdWarning) thresholdWarning.value = config.warningLevel;
            if (thresholdCritical) thresholdCritical.value = config.criticalLevel;
        } catch (err) {
            console.error('Esik degerleri yuklenemedi:', err);
        }
    },

    // ESIK DEGERI KAYDET BUTONU
    async saveThreshold() {
        const maxVal = parseFloat(document.getElementById('thresholdMax')?.value);
        const warningVal = parseFloat(document.getElementById('thresholdWarning')?.value);
        const criticalVal = parseFloat(document.getElementById('thresholdCritical')?.value);

        if (isNaN(maxVal) || isNaN(warningVal) || isNaN(criticalVal)) {
            this.showToast('warning', 'Lutfen tum alanlari doldurun');
            return;
        }

        if (warningVal >= criticalVal) {
            this.showToast('warning', 'Uyari seviyesi kritik seviyeden dusuk olmali');
            return;
        }

        try {
            const response = await Auth.apiRequest('/api/alerts/threshold', {
                method: 'PUT',
                body: JSON.stringify({
                    maxConsumption: maxVal,
                    warningLevel: warningVal,
                    criticalLevel: criticalVal
                })
            });

            if (response && response.ok) {
                this.showToast('success', 'Esik degerleri guncellendi');
            } else {
                this.showToast('error', 'Esik degerleri kaydedilemedi');
            }
        } catch (err) {
            this.showToast('error', 'Kaydetme isleminde hata olustu');
        }
    },

    /* ==========================================
       GRAFIK - ENERJI GECMISI
       ========================================== */

    async loadChartData(hours) {
        try {
            const response = await Auth.apiRequest(`/api/dashboard/history?hours=${hours}`);
            if (!response || !response.ok) return;
            const data = await response.json();
            this.renderChart(data);
        } catch (err) {
            console.error('Grafik verisi yuklenemedi:', err);
        }
    },

    renderChart(data) {
        const ctx = document.getElementById('energyChart');
        if (!ctx) return;

        // Eski grafigi sil
        if (this.chart) {
            this.chart.destroy();
        }

        const labels = data.map(d => {
            const date = new Date(d.timestamp);
            return date.toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' });
        });

        const consumptionData = data.map(d => d.consumption);
        const productionData = data.map(d => d.production);

        this.chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Tuketim (kW)',
                        data: consumptionData,
                        borderColor: '#3b82f6',
                        backgroundColor: 'rgba(59, 130, 246, 0.1)',
                        borderWidth: 2,
                        fill: true,
                        tension: 0.4,
                        pointRadius: 2,
                        pointHoverRadius: 6,
                        pointBackgroundColor: '#3b82f6',
                        pointBorderColor: '#fff',
                        pointBorderWidth: 2
                    },
                    {
                        label: 'Uretim (kW)',
                        data: productionData,
                        borderColor: '#10b981',
                        backgroundColor: 'rgba(16, 185, 129, 0.1)',
                        borderWidth: 2,
                        fill: true,
                        tension: 0.4,
                        pointRadius: 2,
                        pointHoverRadius: 6,
                        pointBackgroundColor: '#10b981',
                        pointBorderColor: '#fff',
                        pointBorderWidth: 2
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                interaction: {
                    mode: 'index',
                    intersect: false
                },
                plugins: {
                    legend: {
                        position: 'top',
                        labels: {
                            color: '#8892a8',
                            font: { family: 'Inter', size: 12 },
                            usePointStyle: true,
                            pointStyle: 'circle',
                            padding: 20
                        }
                    },
                    tooltip: {
                        backgroundColor: 'rgba(26, 31, 53, 0.95)',
                        titleColor: '#e8ecf4',
                        bodyColor: '#8892a8',
                        borderColor: 'rgba(255,255,255,0.1)',
                        borderWidth: 1,
                        padding: 12,
                        titleFont: { family: 'Inter', weight: '600' },
                        bodyFont: { family: 'Inter' },
                        cornerRadius: 8,
                        displayColors: true
                    }
                },
                scales: {
                    x: {
                        grid: {
                            color: 'rgba(255,255,255,0.04)',
                            drawBorder: false
                        },
                        ticks: {
                            color: '#5c6478',
                            font: { family: 'Inter', size: 11 },
                            maxRotation: 0,
                            maxTicksLimit: 12
                        }
                    },
                    y: {
                        grid: {
                            color: 'rgba(255,255,255,0.04)',
                            drawBorder: false
                        },
                        ticks: {
                            color: '#5c6478',
                            font: { family: 'Inter', size: 11 },
                            callback: (value) => value + ' kW'
                        },
                        beginAtZero: true
                    }
                }
            }
        });
    },

    /* ==========================================
       ENERJI KONTROL BUTONU
       ========================================== */

    async checkEnergy() {
        try {
            // Once anlik tuketimi al
            const realtimeResponse = await Auth.apiRequest('/api/dashboard/realtime');
            if (!realtimeResponse || !realtimeResponse.ok) return;
            const realtimeData = await realtimeResponse.json();

            // Sonra kontrol API'sine gonder
            const checkResponse = await Auth.apiRequest(
                `/api/energy/check?consumption=${realtimeData.consumption * 100}`,
                { method: 'POST' }
            );

            if (checkResponse && checkResponse.ok) {
                const result = await checkResponse.json();
                if (result.exceeded) {
                    this.showToast('warning', result.message);
                    // Alarmlari yenile
                    this.loadAlerts();
                    this.loadDashboardSummary();
                } else {
                    this.showToast('success', result.message);
                }
            }
        } catch (err) {
            this.showToast('error', 'Enerji kontrolunde hata olustu');
        }
    },

    /* ==========================================
       CSV DISA AKTAR BUTONU
       ========================================== */

    async exportCSV() {
        try {
            const response = await Auth.apiRequest(`/api/dashboard/history?hours=${this.currentHours}`);
            if (!response || !response.ok) return;
            const data = await response.json();

            // CSV olustur
            let csv = 'Zaman,Tuketim (kW),Uretim (kW),Batarya (%),Maliyet (TL)\n';
            data.forEach(d => {
                const time = new Date(d.timestamp).toLocaleString('tr-TR');
                csv += `${time},${d.consumption},${d.production},${d.batteryLevel},${d.cost}\n`;
            });

            // Dosyayi indir
            const blob = new Blob(['\ufeff' + csv], { type: 'text/csv;charset=utf-8;' });
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = `enerji_raporu_${new Date().toISOString().slice(0, 10)}.csv`;
            link.click();
            URL.revokeObjectURL(link.href);

            this.showToast('success', 'CSV dosyasi indirildi');
        } catch (err) {
            this.showToast('error', 'CSV olusturulurken hata olustu');
        }
    },

    /* ==========================================
       OTOMATIK YENILEME
       ========================================== */

    startAutoRefresh() {
        // Her 30 saniyede bir verileri guncelle
        this.refreshInterval = setInterval(() => {
            this.loadDashboardSummary();
        }, 30000);
    },

    stopAutoRefresh() {
        if (this.refreshInterval) {
            clearInterval(this.refreshInterval);
        }
    },

    /* ==========================================
       TOAST BILDIRIM SISTEMI
       ========================================== */

    showToast(type, message) {
        const container = document.getElementById('toastContainer');
        if (!container) return;

        const icons = {
            success: 'fa-check-circle',
            error: 'fa-times-circle',
            warning: 'fa-exclamation-triangle',
            info: 'fa-info-circle'
        };

        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        toast.innerHTML = `
            <i class="fas ${icons[type]} toast-icon"></i>
            <span class="toast-message">${message}</span>
            <button class="toast-close" onclick="this.parentElement.classList.add('removing'); setTimeout(() => this.parentElement.remove(), 300);">
                <i class="fas fa-times"></i>
            </button>
        `;

        container.appendChild(toast);

        // 4 saniye sonra otomatik kapat
        setTimeout(() => {
            if (toast.parentElement) {
                toast.classList.add('removing');
                setTimeout(() => toast.remove(), 300);
            }
        }, 4000);
    },

    /* ==========================================
       TEMA SISTEMI (DARK / LIGHT MODE)
       ========================================== */
    setupTheme() {
        const themeBtn = document.getElementById('themeToggleBtn');
        if (!themeBtn) return;

        // Kaydedilmis temayi kontrol et
        const savedTheme = localStorage.getItem('theme') || 'dark';
        if (savedTheme === 'light') {
            document.documentElement.setAttribute('data-theme', 'light');
            themeBtn.innerHTML = '<i class="fas fa-sun"></i>';
        }

        themeBtn.addEventListener('click', () => {
            const currentTheme = document.documentElement.getAttribute('data-theme') || 'dark';
            const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
            
            document.documentElement.setAttribute('data-theme', newTheme);
            localStorage.setItem('theme', newTheme);
            
            themeBtn.innerHTML = newTheme === 'dark' ? '<i class="fas fa-moon"></i>' : '<i class="fas fa-sun"></i>';
            this.showToast('info', newTheme === 'dark' ? 'Karanlık moda geçildi' : 'Aydınlık moda geçildi');
        });
    },

    /* ==========================================
       GERI BILDIRIM SISTEMI (FEEDBACK)
       ========================================== */
    setupFeedbackSystem() {
        const btnOpen = document.getElementById('btnOpenFeedback');
        const btnClose = document.getElementById('btnCloseFeedback');
        const modal = document.getElementById('feedbackModal');
        const form = document.getElementById('feedbackForm');
        const stars = document.querySelectorAll('.star-rating i');
        const ratingInput = document.getElementById('feedbackRating');

        if (!btnOpen || !modal) return;

        // Modali ac
        btnOpen.addEventListener('click', () => {
            const startTime = performance.now();
            modal.style.display = 'flex';
            // Etkilesim analitigi
            console.log(`Geri bildirim butonu tiklandi. Sure: ${startTime}ms`);
        });

        // Modali kapat
        btnClose.addEventListener('click', () => {
            modal.style.display = 'none';
        });

        // Disariya tiklayinca kapat
        window.addEventListener('click', (e) => {
            if (e.target === modal) modal.style.display = 'none';
        });

        // Yildiz oylama
        stars.forEach(star => {
            star.addEventListener('click', () => {
                const value = star.getAttribute('data-value');
                ratingInput.value = value;
                
                stars.forEach(s => {
                    s.classList.remove('fas', 'active');
                    s.classList.add('far');
                    if (s.getAttribute('data-value') <= value) {
                        s.classList.remove('far');
                        s.classList.add('fas', 'active');
                    }
                });
            });
        });

        // Form Gonderimi
        if (form) {
            form.addEventListener('submit', async (e) => {
                e.preventDefault();
                
                if (ratingInput.value === '0') {
                    this.showToast('warning', 'Lütfen bir yıldız puanı verin');
                    return;
                }

                const comment = document.getElementById('feedbackComment').value;
                const feedbackData = {
                    username: Auth.getUsername() || 'Anonim',
                    rating: parseInt(ratingInput.value),
                    message: comment
                };

                try {
                    const response = await Auth.apiRequest('/api/feedback', {
                        method: 'POST',
                        body: JSON.stringify(feedbackData)
                    });

                    if (response && response.ok) {
                        this.showToast('success', 'Geri bildiriminiz başarıyla gönderildi, teşekkürler!');
                        modal.style.display = 'none';
                        form.reset();
                        // Yildizlari sifirla
                        stars.forEach(s => {
                            s.classList.remove('fas', 'active');
                            s.classList.add('far');
                        });
                        ratingInput.value = '0';
                    } else {
                        this.showToast('error', 'Geri bildirim gönderilemedi.');
                    }
                } catch (err) {
                    // Backend henuz yoksa (local test icin konsola basiyoruz)
                    console.log('Geri Bildirim Formu Gonderildi:', feedbackData);
                    this.showToast('success', 'Geri bildirim test modunda alındı!');
                    modal.style.display = 'none';
                }
            });
        }
    },

    /* ==========================================
       UYGULAMA TURU (USABILITY TESTING)
       ========================================== */
    startAppTour() {
        // Tur sadece bir kez gosterilmeli (localStorage'da tutuyoruz)
        const hasSeenTour = localStorage.getItem('hasSeenTour');
        if (hasSeenTour === 'true' || typeof driver === 'undefined') return;

        const driverObj = driver({
            showProgress: true,
            animate: true,
            nextBtnText: 'İleri',
            prevBtnText: 'Geri',
            doneBtnText: 'Bitir',
            steps: [
                {
                    element: '#kpiSection',
                    popover: {
                        title: 'Hoş Geldiniz!',
                        description: 'Akıllı Enerji Yönetim Sistemi dashboarduna hoş geldiniz. Bu kartlar sistemin anlık özetini gösterir.',
                        side: "bottom",
                        align: 'start'
                    }
                },
                {
                    element: '.chart-section',
                    popover: {
                        title: 'Enerji Geçmişi',
                        description: 'Üretim ve tüketim oranlarını zamana bağlı olarak bu grafik üzerinden inceleyebilirsiniz. Üstteki saat butonlarıyla aralığı değiştirebilirsiniz.',
                        side: "top",
                        align: 'center'
                    }
                },
                {
                    element: '.devices-card',
                    popover: {
                        title: 'Cihaz Yönetimi',
                        description: 'Bağlı cihazlarınızı buradan görebilir, uzaktan açıp kapatabilirsiniz.',
                        side: "right",
                        align: 'start'
                    }
                },
                {
                    element: '#btnOpenFeedback',
                    popover: {
                        title: 'Geri Bildirim',
                        description: 'Sistem hakkında herhangi bir öneriniz veya sorununuz olursa bu buton ile bize iletebilirsiniz.',
                        side: "left",
                        align: 'end'
                    }
                }
            ],
            onDestroyed: () => {
                localStorage.setItem('hasSeenTour', 'true');
                this.showToast('info', 'Turu tamamladınız. Keyifli kullanımlar!');
            }
        });

        driverObj.drive();
    }
};
