package com.example.demo.queue;


import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
@Component
public class MessageQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageQueue.class);
    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    @Getter
    private int successCount = 0;
    @Getter
    private int errorCount = 0;

    public void produce(String message) {
        try {
            if (message != null) {
                queue.put(message);
                LOGGER.info("Produced message: {}", message);
            } else {
                incrementErrorCount();
                LOGGER.error("Cannot produce null message.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            incrementErrorCount();
            LOGGER.error("Error producing message: {}", e.getMessage());
        }
    }

    public String consume() {
        while (true) {
            try {
                String message = queue.take();
                if (processMessage(message)) {
                    incrementSuccessCount();
                    LOGGER.info("Consumed message: {}", message);
                } else {
                    incrementErrorCount();
                    LOGGER.error("Error processing message: {}", message);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                incrementErrorCount();
                LOGGER.error("Error consuming message: {}", e.getMessage());
            }
        }
    }

    private boolean processMessage(String message) {
        return message != null && !message.trim().isEmpty();
    }

    private synchronized void incrementSuccessCount() {
        successCount++;
    }

    private synchronized void incrementErrorCount() {
        errorCount++;
    }

}
