package com.example.userservice.user.service;

import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.user.dto.request.UserJoinCertificationRequest;
import com.example.userservice.user.domain.user.UserStatus;
import com.example.userservice.util.exception.code.GlobalException;
import com.example.userservice.user.service.port.UserRepository;
import com.example.userservice.util.certification.CertificationHolder;
import com.example.userservice.user.controller.port.JoinUserService;
import com.example.userservice.user.dto.request.UserJoinRequest;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.infrastructure.join.JoinUserRepository;
import com.example.userservice.util.certification.email.EmailCertification;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Builder
@Service
@RequiredArgsConstructor
public class JoinUserServiceImpl implements JoinUserService {

    private final CertificationHolder certificationHolder;
    private final JoinUserRepository joinUserRepository;
    private final CertificationService certificationService;
    private final EmailCertification emailCertification;
    private final UserRepository userRepository;

    @Override
    public boolean checkEmailBeforeCertification(String email) {
        if ((!joinUserRepository.obtain(email)) && (userRepository.findByEmail(email).isEmpty())) return true;
        throw new GlobalException(ErrorCode.USER_DUPLICATE_ERROR);
    }

    @Override
    public JoinUser join(UserJoinRequest userCreate) {

        // 이미 인증을 시도한 이메일인지 확인
        if(joinUserRepository.obtain(userCreate.getEmail())){
            JoinUser duplicateUser = joinUserRepository.findByEmail(userCreate.getEmail()).orElseThrow(
                    () -> new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR)
            );
            certificationService.send(duplicateUser.getEmail(), duplicateUser.getCertificationCode());
            return duplicateUser;
        }

        JoinUser joinUser = JoinUser.from(userCreate, certificationHolder);
        joinUserRepository.save(joinUser);

        certificationService.send(joinUser.getEmail(), joinUser.getCertificationCode());
        return joinUser;
    }

    @Override
    public JoinUser certification(UserJoinCertificationRequest joinUserCertification) {
        JoinUser joinUser = joinUserRepository.findByEmail(joinUserCertification.getEmail()).orElseThrow(
                () -> new GlobalException(ErrorCode.USER_NOT_FOUND_EMAIL)
        );
        // 보안코드가 맞는지 확인
        boolean compareResult = compare(joinUser.getCertificationCode(), joinUserCertification.getCertificationCode());
        if (compareResult) {
            joinUser = joinUser.updateStatus(joinUser, UserStatus.ABLE);
            joinUserRepository.replace(joinUser.getEmail(), joinUser);
            return joinUser;
        }
        throw new GlobalException(ErrorCode.USER_CERTIFICATION_NOT_MATCHED);
    }

    private boolean compare(String userCertificationCode, String inputCertificationCode) {
        return userCertificationCode.equals(inputCertificationCode);
    }

    @Override
    public JoinUser emailCertificationBeforeJoin(String email) {
        JoinUser userFind = joinUserRepository.findByEmail(email).orElseThrow(
                () -> new GlobalException(ErrorCode.USER_NOT_FOUND_EMAIL)
        );

        if (userFind.getStatus().equals(UserStatus.ABLE)) {
            joinUserRepository.delete(userFind.getEmail());
            return userFind;
        }
        throw new GlobalException(ErrorCode.USER_INVALID_ERROR);
    }
}
