package com.example.userservice.user.service.user.join;

import com.example.userservice.user.service.user.mail.UserCertificationService;
import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.user.dto.request.user.UserJoinCertificationRequest;
import com.example.userservice.user.domain.user.value.UserStatus;
import com.example.userservice.util.exception.code.GlobalException;
import com.example.userservice.user.infrastructure.user.UserRepository;
import com.example.userservice.util.certification.CertificationHolder;
import com.example.userservice.user.dto.request.user.UserJoinRequest;
import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.user.infrastructure.user.join.JoinUserRepository;
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
    private final UserCertificationService userCertificationService;
    private final EmailCertification emailCertification;
    private final UserRepository userRepository;

    @Override
    public boolean checkEmailBeforeCertification(String email) {
        if ((!joinUserRepository.obtain(email)) && (userRepository.findByEmail(email).isEmpty())) return true;
        throw new GlobalException(ErrorCode.USER_DUPLICATE_ERROR);
    }

    @Override
    public UserJoin join(UserJoinRequest userCreate) {

        // 이미 인증을 시도한 이메일인지 확인
        if(joinUserRepository.obtain(userCreate.getEmail())){
            UserJoin duplicateUser = joinUserRepository.findByEmail(userCreate.getEmail()).orElseThrow(
                    () -> new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR)
            );
            userCertificationService.send(duplicateUser.getEmail(), duplicateUser.getCertificationCode());
            return duplicateUser;
        }

        UserJoin userJoin = UserJoin.from(userCreate, certificationHolder);
        joinUserRepository.save(userJoin);

        userCertificationService.send(userJoin.getEmail(), userJoin.getCertificationCode());
        return userJoin;
    }

    @Override
    public UserJoin certification(UserJoinCertificationRequest joinUserCertification){
        UserJoin userJoin = joinUserRepository.findByEmail(joinUserCertification.getEmail()).orElseThrow(
                () -> new GlobalException(ErrorCode.USER_NOT_FOUND_EMAIL)
        );
        // 보안코드가 맞는지 확인
        boolean compareResult = compare(userJoin.getCertificationCode(), joinUserCertification.getCertificationCode());

        if (compareResult) {
            userJoin = userJoin.updateStatus(userJoin, UserStatus.ABLE);
            joinUserRepository.replace(userJoin.getEmail(), userJoin);
            return userJoin;
        }

        throw new GlobalException(ErrorCode.USER_CERTIFICATION_NOT_MATCHED);
    }

    private boolean compare(String userCertificationCode, String inputCertificationCode) {
        return userCertificationCode.equals(inputCertificationCode);
    }

    @Override
    public UserJoin emailCertificationBeforeJoin(String email) {
        UserJoin userFind = joinUserRepository.findByEmail(email).orElseThrow(
                () -> new GlobalException(ErrorCode.USER_NOT_FOUND_EMAIL)
        );

        if (userFind.getStatus().equals(UserStatus.ABLE)) {
            joinUserRepository.delete(userFind.getEmail());
            return userFind;
        }
        throw new GlobalException(ErrorCode.USER_INVALID_ERROR);
    }
}
