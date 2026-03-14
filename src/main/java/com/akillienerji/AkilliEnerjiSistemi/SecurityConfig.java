import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // API kullanıyorsanız CSRF'i deaktif edebilirsiniz
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll() // Herkese açık uç noktalar
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Sadece Admin girebilir
                .anyRequest().authenticated() // Diğer her şey için giriş zorunlu
            )
            .httpBasic(Customizer.withDefaults()); // Temel doğrulama (Gelişmiş projede JWT kullanmanız önerilir)

        return http.build();
    }

    // Test amaçlı bellekte (In-Memory) kullanıcı oluşturma
    // Gerçek senaryoda bu veriler veritabanından (PostgreSQL vb.) çekilir.
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
            .username("yetkilipersonel")
            .password("{noop}sifre123") // {noop} şifrelemenin olmadığını belirtir (sadece test için)
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(admin);
    }
}