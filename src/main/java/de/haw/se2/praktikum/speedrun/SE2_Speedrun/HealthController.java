package de.haw.se2.praktikum.speedrun.SE2_Speedrun;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/up")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("OK");
    }
}