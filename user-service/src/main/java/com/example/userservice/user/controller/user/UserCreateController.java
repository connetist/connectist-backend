package com.example.userservice.user.controller.user;

import com.example.userservice.user.service.user.join.JoinUserService;
import com.example.userservice.user.service.user.UserService;
import com.example.userservice.user.dto.request.RequestCheck;
import com.example.userservice.user.dto.response.GlobalResponse;
import com.example.userservice.util.exception.code.SuccessCode;
import com.example.userservice.user.dto.response.user.UserJoinResponse;
import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.dto.request.user.UserCreateRequest;
import com.example.userservice.user.dto.request.user.UserJoinCertificationRequest;
import com.example.userservice.user.dto.request.user.UserJoinRequest;
import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.util.certification.email.EmailCertification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.userservice.user.dto.response.GlobalResponse.of;

@Slf4j
@RestController
@RequestMapping("/api/users/join")
@RequiredArgsConstructor
public class UserCreateController {

    private final JoinUserService joinUserService;
    private final EmailCertification emailCertification;
    private final UserService userService;

    @GetMapping("/check")
    public ResponseEntity<GlobalResponse<String>> check(
            @RequestBody Map<String, String> emailMap
    ) {
        new RequestCheck(emailMap.get("email")).checkString();

        boolean value = joinUserService.checkEmailBeforeCertification(emailMap.get("email"));
        return of(SuccessCode.OK, String.valueOf(value));
    }


    // user Create
    @PostMapping("/email")
    public ResponseEntity<GlobalResponse<UserJoin>> create(@RequestBody UserJoinRequest userCreate) {
        new RequestCheck(userCreate).check();

        emailCertification.verify(userCreate.getSchool(), userCreate.getEmail());
        UserJoin userJoin = joinUserService.join(userCreate);
        return of(SuccessCode.EMAIL_CREATED, userJoin);
    }

    // verify certification code after user create
    // 방식 1 번호 입력
    @PostMapping("/certification")
    public ResponseEntity<GlobalResponse<UserJoinResponse>> certificationByCode(
            @RequestBody UserJoinCertificationRequest joinUserCertification
    ){
        new RequestCheck(joinUserCertification).check();

        UserJoin userJoin = joinUserService.certification(joinUserCertification);
        return of(SuccessCode.EMAIL_VERIFIED, UserJoinResponse.from(userJoin));
    }

    // 방식 2 링크
    // String으로 인증 정보 반환 : 수정 가능
    @GetMapping("/{email}/verify")
    public ResponseEntity<GlobalResponse<UserJoinResponse>> certificationByLink(
            @PathVariable String email,
            @RequestParam String certificationCode
    ){
        new RequestCheck(email).checkString();
        new RequestCheck(certificationCode).checkString();

        UserJoinCertificationRequest joinUserCertification = UserJoinCertificationRequest.from(email, certificationCode);
        UserJoin certification = joinUserService.certification(joinUserCertification);
        return of(SuccessCode.EMAIL_VERIFIED, UserJoinResponse.from(certification));
    }


    // 회원 가입
    @PostMapping
    public ResponseEntity<GlobalResponse<User>> createUser(
            @RequestBody UserCreateRequest userCreateRequest
    ){
        new RequestCheck(userCreateRequest).check();

        return of(SuccessCode.USER_CREATED, userService.create(userCreateRequest));
    }
}
