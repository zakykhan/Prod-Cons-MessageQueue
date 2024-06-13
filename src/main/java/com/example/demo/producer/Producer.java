package com.example.demo.producer;

import com.example.demo.queue.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private final MessageQueue messageQueue;

    @Autowired
    public Producer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void send(String message) throws InterruptedException {
        if (message == null) {
            LOGGER.error("Null message cannot be sent to the queue.");
        }
        messageQueue.produce(message);
    }
}

