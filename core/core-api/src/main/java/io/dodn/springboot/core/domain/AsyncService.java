package io.dodn.springboot.core.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AsyncService {

    private final Logger logger = LoggerFactory.getLogger(AsyncService.class);
    final AtomicBoolean isTaskCompleted = new AtomicBoolean(false);

    public AtomicBoolean getIsTaskCompleted() {
        return isTaskCompleted;
    }

    @Async
    public void longRunningTask() throws InterruptedException {
        logger.info("비동기 작업을 시작합니다.");

        Thread.sleep(3000);
        isTaskCompleted.set(true);

        logger.info("비동기 작업이 성공적으로 완료되었습니다.");
    }
}
