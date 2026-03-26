package com.akillienerji.AkilliEnerjiSistemi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Kullanici giris dogrulamasi
     * POST /api/auth/login
     * Spring Security HTTP Basic ile dogrulama yapar.
     * Bu endpoint'e ulasildiysa dogrulama basarilidir.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("username", auth.getName());
        response.put("roles", auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

    /**
     * Oturum bilgisi
     * GET /api/auth/me
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            Map<String, Object> user = new HashMap<>();
            user.put("username", auth.getName());
            user.put("roles", auth.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.toList()));
            user.put("authenticated", true);
            return ResponseEntity.ok(user);
        }
        Map<String, Object> error = new HashMap<>();
        error.put("authenticated", false);
        return ResponseEntity.status(401).body(error);
    }
}
