/* ================================================
   AUTH.JS - Kimlik Dogrulama Islemleri
   ================================================ */

const Auth = {
    // Credentials'i sessionStorage'da tut
    getCredentials() {
        return sessionStorage.getItem('authCredentials');
    },

    setCredentials(username, password) {
        const encoded = btoa(username + ':' + password);
        sessionStorage.setItem('authCredentials', encoded);
        sessionStorage.setItem('username', username);
    },

    getUsername() {
        return sessionStorage.getItem('username') || 'Kullanici';
    },

    clearCredentials() {
        sessionStorage.removeItem('authCredentials');
        sessionStorage.removeItem('username');
        sessionStorage.removeItem('userRoles');
    },

    isLoggedIn() {
        return !!this.getCredentials();
    },

    getAuthHeader() {
        const creds = this.getCredentials();
        if (creds) {
            return { 'Authorization': 'Basic ' + creds };
        }
        return {};
    },

    isAdmin() {
        const roles = sessionStorage.getItem('userRoles');
        return roles && roles.includes('ROLE_ADMIN');
    },

    // API istegi yap (auth header ile)
    async apiRequest(url, options = {}) {
        const headers = {
            'Content-Type': 'application/json',
            ...this.getAuthHeader(),
            ...options.headers
        };

        try {
            const response = await fetch(url, {
                ...options,
                headers
            });

            if (response.status === 401) {
                this.clearCredentials();
                window.location.href = '/login.html';
                return null;
            }

            return response;
        } catch (error) {
            console.error('API Hatasi:', error);
            throw error;
        }
    },

    // Giris yap
    async login(username, password) {
        const encoded = btoa(username + ':' + password);

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: {
                    'Authorization': 'Basic ' + encoded,
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                const data = await response.json();
                this.setCredentials(username, password);
                if (data.roles) {
                    sessionStorage.setItem('userRoles', JSON.stringify(data.roles));
                }
                return { success: true, data };
            } else {
                return { success: false, message: 'Gecersiz kullanici adi veya sifre' };
            }
        } catch (error) {
            return { success: false, message: 'Sunucuya baglanilamadi' };
        }
    },

    // Cikis yap
    logout() {
        this.clearCredentials();
        window.location.href = '/login.html';
    }
};

/* ================================================
   LOGIN PAGE LOGIC
   ================================================ */
document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');

    // Eger login sayfasindaysak
    if (loginForm) {
        // Zaten giris yapilmissa dashboard'a yonlendir
        if (Auth.isLoggedIn()) {
            window.location.href = '/index.html';
            return;
        }

        // Form submit
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value;
            const loginBtn = document.getElementById('loginBtn');
            const btnText = loginBtn.querySelector('.btn-text');
            const btnLoader = loginBtn.querySelector('.btn-loader');
            const loginError = document.getElementById('loginError');
            const errorMessage = document.getElementById('errorMessage');

            // Loading durumu
            loginBtn.disabled = true;
            btnText.style.display = 'none';
            btnLoader.style.display = 'inline-flex';
            loginError.style.display = 'none';

            // Giris denemesi
            const result = await Auth.login(username, password);

            if (result.success) {
                // Basarili - dashboard'a yonlendir
                window.location.href = '/index.html';
            } else {
                // Hata goster
                errorMessage.textContent = result.message;
                loginError.style.display = 'flex';
                loginBtn.disabled = false;
                btnText.style.display = 'inline';
                btnLoader.style.display = 'none';
            }
        });

        // Sifre goster/gizle butonu
        const togglePassword = document.getElementById('togglePassword');
        if (togglePassword) {
            togglePassword.addEventListener('click', () => {
                const passwordInput = document.getElementById('password');
                const icon = togglePassword.querySelector('i');
                if (passwordInput.type === 'password') {
                    passwordInput.type = 'text';
                    icon.classList.replace('fa-eye', 'fa-eye-slash');
                } else {
                    passwordInput.type = 'password';
                    icon.classList.replace('fa-eye-slash', 'fa-eye');
                }
            });
        }

        // Demo credential chip'leri
        document.querySelectorAll('.credential-chip').forEach(chip => {
            chip.addEventListener('click', () => {
                document.getElementById('username').value = chip.dataset.user;
                document.getElementById('password').value = chip.dataset.pass;
            });
        });
    }

    // Dashboard sayfasindaysak ve giris yapilmamissa
    if (document.querySelector('.dashboard-page') && !Auth.isLoggedIn()) {
        window.location.href = '/login.html';
    }
});
