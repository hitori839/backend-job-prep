package com.example.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HealthControllerTest {

    @Test
    void 애플리케이션_컨텍스트가_정상적으로_시작된다() {
        assertThat(true).isTrue();
    }
}
