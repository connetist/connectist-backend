package com.example.userservice.util.certification.email;

import com.example.userservice.user.controller.response.code.ErrorCode;
import com.example.userservice.user.domain.user.School;
import com.example.userservice.user.error.GlobalException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class EmailCertification {

    private HashMap<School, ArrayList<String>> emailCertificationMap = new HashMap<>();

    public EmailCertification() {
        ArrayList<String> eamilCertificateStringsOfKAIST = new ArrayList<>();
        eamilCertificateStringsOfKAIST.add("kaist.ac.kr");
        emailCertificationMap.put(School.KAIST, eamilCertificateStringsOfKAIST);

        ArrayList<String> eamilCertificateStringsOfGIST = new ArrayList<>();
        eamilCertificateStringsOfGIST.add("gist.ac.kr");
        eamilCertificateStringsOfGIST.add("gm.gist.ac.kr");
        emailCertificationMap.put(School.GIST, eamilCertificateStringsOfGIST);

        ArrayList<String> eamilCertificateStringsOfDGIST = new ArrayList<>();
        eamilCertificateStringsOfDGIST.add("dgist.ac.kr");
        emailCertificationMap.put(School.DGIST, eamilCertificateStringsOfDGIST);

        ArrayList<String> eamilCertificateStringsOfUnist = new ArrayList<>();
        eamilCertificateStringsOfUnist.add("unist.ac.kr");
        emailCertificationMap.put(School.UNIST, eamilCertificateStringsOfUnist);

        ArrayList<String> eamilCertificateStringsOfKentech = new ArrayList<>();
        eamilCertificateStringsOfKentech.add("kentech.ac.kr");
        emailCertificationMap.put(School.KENTECH, eamilCertificateStringsOfKentech);
    }

    public String verify(School school, String email) {
        String schoolEmailString = (email.split("@"))[1];
        for(String emailCertification : emailCertificationMap.get(school)){
            if (emailCertification.equals(schoolEmailString)) {
                return email;
            }
        }
        throw new GlobalException(ErrorCode.USER_EMAIL_CHECK_FAIL);
    }

}
