package com.example.userservice.user.service;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.join.JoinUserCertification;
import com.example.userservice.user.domain.user.UserStatus;
import com.example.userservice.util.certification.CertificationHolder;
import com.example.userservice.user.controller.port.JoinUserService;
import com.example.userservice.user.domain.join.JoinUserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.infrastructure.join.JoinUserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Builder
@Service
@RequiredArgsConstructor
public class JoinUserServiceImpl implements JoinUserService {

    private final CertificationHolder certificationHolder;
    private final JoinUserRepository joinUserRepository;
    private final CertificationService certificationService;


    @Override
    public JoinUser join(JoinUserCreate userCreate) {
        JoinUser joinUser = JoinUser.from(userCreate, certificationHolder);

        // 이메일과 학교 정보 대조
        
        // 이미 인증을 시도한 이메일인지 확인
        if(joinUserRepository.obtain(joinUser.getEmail())){
            certificationService.send(joinUser.getEmail(), joinUser.getCertificationCode());
            return joinUser;
        }

        joinUser = joinUserRepository.save(joinUser);
        certificationService.send(joinUser.getEmail(), joinUser.getCertificationCode());
        return joinUser;
    }

    @Override
    public JoinUser certification(JoinUserCertification joinUserCertification) {
        JoinUser joinUser = joinUserRepository.findByEmail(joinUserCertification.getEmail());
        boolean compareResult = compare(joinUser.getCertificationCode(), joinUserCertification.getCertificationCode());

        if (compareResult) {
            return joinUser.builder()
                    .email(joinUser.getEmail())
                    .school(joinUser.getSchool())
                    .certificationCode((joinUserCertification.getCertificationCode()))
                    .status(UserStatus.ABLE)
                    .build();
        }
        return joinUser;
    }

    private boolean compare(String userCertificationCode, String inputCertificationCode) {
        return userCertificationCode.equals(inputCertificationCode);
    }

    @Override
    public User joinWithInfo(User user) {
        return null;
    }
}
