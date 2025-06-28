package io.dodn.springboot.core.domain;

import io.dodn.springboot.core.api.config.AsyncConfig;
import io.dodn.springboot.core.api.config.AsyncConfigToBe;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class AsyncServiceTest {

    @Test
    void gracefulShutdown_asIs() throws InterruptedException {
        var context = new SpringApplicationBuilder(
            AsyncConfig.class, // 기존 AsyncConfig
            AsyncService.class
        )
            .web(WebApplicationType.NONE)
            .run();
        var asyncService = context.getBean(AsyncService.class);

        asyncService.longRunningTask();
        Thread.sleep(500);
        context.close();

        // 실패
        assertThat(asyncService.getIsTaskCompleted().get()).isTrue();
    }

    @Test
    void gracefulShutdown_toBe() throws InterruptedException {
        var context = new SpringApplicationBuilder(
            AsyncConfigToBe.class, // 변경될 AsyncConfig
            AsyncService.class
        )
            .web(WebApplicationType.NONE)
            .run();
        var asyncService = context.getBean(AsyncService.class);

        asyncService.longRunningTask();
        Thread.sleep(500);
        context.close();

        // 성공
        assertThat(asyncService.getIsTaskCompleted().get()).isTrue();
    }

}