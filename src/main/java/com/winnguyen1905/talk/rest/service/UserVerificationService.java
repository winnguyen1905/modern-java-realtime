package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;
import java.util.Random;

import com.winnguyen1905.talk.persistance.entity.EUserVerification;
import com.winnguyen1905.talk.persistance.repository.UserVerificationRepository;

@Service
public class UserVerificationService {

    private final UserVerificationRepository userVerificationRepository;

    public UserVerificationService(UserVerificationRepository userVerificationRepository) {
        this.userVerificationRepository = userVerificationRepository;
    }

    // Generate a random 6-digit verification code
    private String generateVerificationCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    // 1. Generate and save a verification code for a user
    public Mono<EUserVerification> generateVerification(UUID userId) {
        String code = generateVerificationCode();
        EUserVerification verification = EUserVerification.builder()
                .id(userId)
                .verificationCode(code)
                .build();
        return userVerificationRepository.save(verification);
    }

    // 2. Verify the user's code
    public Mono<Boolean> verifyCode(UUID userId, String code) {
        return userVerificationRepository.findById(userId)
                .map(verification -> verification.getVerificationCode().equals(code))
                .flatMap(isValid -> isValid
                        ? userVerificationRepository.deleteById(userId).thenReturn(true)
                        : Mono.just(false));
    }

    // 3. Resend verification code
    public Mono<EUserVerification> resendVerification(UUID userId) {
        return generateVerification(userId);
    }
}
