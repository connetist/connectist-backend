package com.example.userservice.user.controller;

import com.example.userservice.user.controller.port.JoinUserService;
import com.example.userservice.user.controller.port.UserService;
import com.example.userservice.user.controller.response.GlobalResponse;
import com.example.userservice.user.controller.response.code.ErrorCode;
import com.example.userservice.user.controller.response.code.SuccessCode;
import com.example.userservice.user.dto.response.UserJoinResponse;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.create.UserCreate;
import com.example.userservice.user.domain.create.UserCreateDto;
import com.example.userservice.user.domain.join.JoinUserCertification;
import com.example.userservice.user.domain.join.JoinUserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.domain.user.UserStatus;
import com.example.userservice.user.error.GlobalException;
import com.example.userservice.util.certification.email.EmailCertification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.userservice.user.controller.response.GlobalResponse.of;

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
        boolean value = joinUserService.checkEmailBeforeCertification(emailMap.get("email"));
        return of(SuccessCode.OK, String.valueOf(value));
    }


    // user Create
    @PostMapping("/email")
    public ResponseEntity<GlobalResponse<JoinUser>> create(@RequestBody JoinUserCreate userCreate) {
        emailCertification.verify(userCreate.getSchool(), userCreate.getEmail());
        JoinUser joinUser = joinUserService.join(userCreate);
        return of(SuccessCode.EMAIL_CREATED, joinUser);
    }

    // verify certification code after user create
    // 방식 1 번호 입력
    @PostMapping("/certification")
    public ResponseEntity<GlobalResponse<UserJoinResponse>> certificationByCode(
            @RequestBody JoinUserCertification joinUserCertification
    ){
        JoinUser joinUser = joinUserService.certification(joinUserCertification);
        return of(SuccessCode.EMAIL_VERIFIED, UserJoinResponse.from(joinUser));
    }

    // 방식 2 링크
    // String으로 인증 정보 반환 : 수정 가능
    @GetMapping("/{email}/verify")
    public ResponseEntity<GlobalResponse<UserJoinResponse>> certificationByLink(
            @PathVariable String email,
            @RequestParam String certificationCode
    ){
        JoinUserCertification joinUserCertification = JoinUserCertification.from(email, certificationCode);
        JoinUser certification = joinUserService.certification(joinUserCertification);
        return of(SuccessCode.EMAIL_VERIFIED, UserJoinResponse.from(certification));
    }


    // 회원 가입
    @PostMapping
    public ResponseEntity<GlobalResponse<User>> createUser(
            @RequestBody UserCreateDto userCreateDto
    ){
        UserCreate userCreate = UserCreate.from(userCreateDto);
        return of(SuccessCode.USER_CREATED, userService.create(userCreate));
    }
}
