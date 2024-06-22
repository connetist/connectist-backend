package com.example.userservice.util.certification;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CertificationDigitHolderTest {

    @Test
    @DisplayName("암호 생성")
    void 암호생성기() {
        CertificationHolder certificationHolder = new CertificationDigitHolder();
        Assertions.assertThat(certificationHolder.random().length()).isEqualTo(6);
    }

}