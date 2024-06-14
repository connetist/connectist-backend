package org.example.chatservice.chatRoom.controller;

import org.apache.coyote.Response;
import org.example.chatservice.chatRoom.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class ChatControllerTest {

    private TestContainer testContainer;

    @BeforeEach
    public void init(){
        this.testContainer = TestContainer.builder().build();
    }

    @Test
    public void 헬스체크_할_수_있다(){
        ResponseEntity<String> result = testContainer.chatController.status();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
    }

}
