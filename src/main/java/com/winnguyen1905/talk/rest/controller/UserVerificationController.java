package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/verification")
public class UserVerificationController {

    private final UserVerificationService userVerificationService;

    public UserVerificationController(UserVerificationService userVerificationService) {
        this.userVerificationService = userVerificationService;
    }

    // 1. Generate verification code
    @PostMapping("/{userId}")
    public Mono<ResponseEntity<EUserVerification>> generateVerification(@PathVariable UUID userId) {
        return userVerificationService.generateVerification(userId)
                .map(ResponseEntity::ok);
    }

    // 2. Verify user code
    @PostMapping("/{userId}/verify")
    public Mono<ResponseEntity<String>> verifyCode(@PathVariable UUID userId, @RequestParam String code) {
        return userVerificationService.verifyCode(userId, code)
                .map(isValid -> isValid
                        ? ResponseEntity.ok("Verification successful")
                        : ResponseEntity.badRequest().body("Invalid verification code"));
    }

    // 3. Resend verification code
    @PostMapping("/{userId}/resend")
    public Mono<ResponseEntity<EUserVerification>> resendVerification(@PathVariable UUID userId) {
        return userVerificationService.resendVerification(userId)
                .map(ResponseEntity::ok);
    }
}
