package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.AccountRequest;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.VerifyCodeDto;
import com.winnguyen1905.talk.model.viewmodel.UserVerificationVm;
import com.winnguyen1905.talk.rest.service.UserVerificationService;

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
  public Mono<ResponseEntity<UserVerificationVm>> generateVerification(@AccountRequest TAccountRequest accountRequest) {
    return userVerificationService.generateVerification(accountRequest)
        .map(verification -> ResponseEntity
            .ok(new UserVerificationVm(verification.getId(), verification.getVerificationCode())));
  }

  // 2. Verify user code
  @PostMapping("/{userId}/verify")
  public Mono<ResponseEntity<String>> verifyCode(@RequestBody VerifyCodeDto verifyCodeDto, @AccountRequest TAccountRequest accountRequest) {
    return userVerificationService.verifyCode(verifyCodeDto, accountRequest)
        .map(isValid -> isValid
            ? ResponseEntity.ok("Verification successful")
            : ResponseEntity.badRequest().body("Invalid verification code"));
  }

  // 3. Resend verification code
  @PostMapping("/{userId}/resend")
  public Mono<ResponseEntity<UserVerificationVm>> resendVerification(@AccountRequest TAccountRequest accountRequest) {
    return userVerificationService.resendVerification(accountRequest)
        .map(verification -> ResponseEntity
            .ok(new UserVerificationVm(verification.getId(), verification.getVerificationCode())));
  }
}
