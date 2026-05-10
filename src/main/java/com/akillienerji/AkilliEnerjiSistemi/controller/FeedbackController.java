package com.akillienerji.AkilliEnerjiSistemi.controller;

import com.akillienerji.AkilliEnerjiSistemi.dto.ApiResponseDTO;
import com.akillienerji.AkilliEnerjiSistemi.dto.FeedbackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @PostMapping
    public ResponseEntity<ApiResponseDTO> submitFeedback(@RequestBody FeedbackDTO feedback) {
        // Su anlik loglara yazdiriyoruz. Ilerleyen asamada InfluxDB veya PostgreSQL'e kaydedilebilir.
        logger.info("YENI GERI BILDIRIM ALINDI:");
        logger.info("Kullanici: {}", feedback.getUsername());
        logger.info("Puan: {}/5", feedback.getRating());
        logger.info("Mesaj: {}", feedback.getMessage());

        ApiResponseDTO response = new ApiResponseDTO(true, "Geri bildirim basariyla alindi.", null);
        return ResponseEntity.ok(response);
    }
}
