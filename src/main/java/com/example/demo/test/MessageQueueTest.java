package com.example.demo.test;

import com.example.demo.consumer.Consumer;
import com.example.demo.producer.Producer;
import com.example.demo.queue.MessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageQueueTest {

    private MessageQueue messageQueue;
    private Producer producer;

    @BeforeEach
    public void setUp() {
        messageQueue = new MessageQueue();
        producer = new Producer(messageQueue);
        Consumer consumer = new Consumer(messageQueue);

        // Start consumer in a separate thread
        new Thread(messageQueue::consume).start();
    }

    @Test
    public void testSuccessfulMessageProcessing() throws InterruptedException {
        producer.send("Test message 1");
        producer.send("Test message 2");
        producer.send("Test message 3");

        // Wait for messages to be processed
        Thread.sleep(1000);

        assertEquals(3, messageQueue.getSuccessCount());
        assertEquals(0, messageQueue.getErrorCount());
    }

    @Test
    public void testErrorInMessageProcessing() throws InterruptedException {
        // Simulate an error scenario
        producer.send(null);
        producer.send(null);

        // Wait for messages to be processed
        Thread.sleep(1000);

        assertEquals(0, messageQueue.getSuccessCount());
        assertEquals(2, messageQueue.getErrorCount());
    }
}

