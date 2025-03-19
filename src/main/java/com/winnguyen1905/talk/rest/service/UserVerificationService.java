package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;
import java.util.Random;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.VerifyCodeDto;
import com.winnguyen1905.talk.persistance.entity.EUserVerification;
import com.winnguyen1905.talk.persistance.repository.UserVerificationRepository;

@Service
public class UserVerificationService {

  private final UserVerificationRepository userVerificationRepository;

  public UserVerificationService(UserVerificationRepository userVerificationRepository) {
    this.userVerificationRepository = userVerificationRepository;
  }

  private String generateVerificationCode() {
    return String.valueOf(100000 + new Random().nextInt(900000));
  }

  public Mono<EUserVerification> generateVerification(TAccountRequest accountRequest) {
    String code = generateVerificationCode();
    EUserVerification verification = EUserVerification.builder()
        .id(accountRequest.id())
        .verificationCode(code)
        .build();
    return userVerificationRepository.save(verification);
  }

  public Mono<Boolean> verifyCode(VerifyCodeDto verifyCodeDto, TAccountRequest accountRequest) {
    return userVerificationRepository.findById(accountRequest.id())
        .map(verification -> verification.getVerificationCode().equals(verifyCodeDto.code()))
        .flatMap(isValid -> isValid
            ? userVerificationRepository.deleteById(accountRequest.id()).thenReturn(true)
            : Mono.just(false));
  }

  public Mono<EUserVerification> resendVerification(TAccountRequest accountRequest) {
    return generateVerification(accountRequest);
  }
}
