package com.example.userservice.user.controller;

import com.example.userservice.user.controller.port.JoinUserService;
import com.example.userservice.user.controller.response.UserJoinResponse;
import com.example.userservice.user.domain.join.JoinUserCertification;
import com.example.userservice.user.domain.join.JoinUserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.domain.user.UserStatus;
import com.example.userservice.util.certification.email.EmailCertification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCreateController {

    private final JoinUserService joinUserService;
    private final EmailCertification emailCertification;
    // user Create
    @PostMapping
    public ResponseEntity<UserJoinResponse> create(@RequestBody JoinUserCreate userCreate) {
        if (!emailCertification.verify(userCreate.getSchool(), userCreate.getEmail())) {
            JoinUser joinUser = JoinUser.builder().email(userCreate.getEmail()).school(userCreate.getSchool())
                    .status(UserStatus.BANNED).build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(UserJoinResponse.from(joinUser));
        }
        JoinUser joinUser = joinUserService.join(userCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserJoinResponse.from(joinUser));
    }

    // verify certification code after user create
    // 방식 1 번호 입력
    @PostMapping("/certification")
    public ResponseEntity<UserJoinResponse> certificationByCode(
            @RequestBody JoinUserCertification joinUserCertification
    ){
        JoinUser joinUser = joinUserService.certification(joinUserCertification);
        return ResponseEntity.status(HttpStatus.OK).body(UserJoinResponse.from(joinUser));
    }

    // 방식 2 링크
    // String으로 인증 정보 반환 : 수정 가능
    @GetMapping("/{email}/verify")
    public JoinUser certificationByLink(
            @PathVariable String email,
            @RequestParam String certificationCode
    ){
        JoinUserCertification joinUserCertification = JoinUserCertification.from(email, certificationCode);
        return joinUserService.certification(joinUserCertification);
    }


    // 회원 가입

}
