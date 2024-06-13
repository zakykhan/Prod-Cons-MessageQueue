package com.example.demo.consumer;


import com.example.demo.queue.MessageQueue;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer implements Runnable {
    private final MessageQueue messageQueue;

    @Autowired
    public Consumer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }


    @Override
    public void run() {
        while (true) {
            try {
                String message = messageQueue.consume();
                System.out.println("Consumed message: " + message);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.err.println("Consumer interrupted: " + e.getMessage());
                break; // Exit the loop if interrupted
            }
        }
    }
}