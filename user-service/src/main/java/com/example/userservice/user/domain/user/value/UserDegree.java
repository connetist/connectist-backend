package com.example.userservice.user.domain.user.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.generator.EventType;

public enum UserDegree {

    Bachelor, Master, Doctor, OTHERS;

    @JsonCreator
    public static EventType from(String s) {
        return EventType.valueOf(s.toUpperCase());
    }
}
