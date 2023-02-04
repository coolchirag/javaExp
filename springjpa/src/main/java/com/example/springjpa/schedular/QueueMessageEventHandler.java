package com.example.springjpa.schedular;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class QueueMessageEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessageEventHandler.class);

   

    @TransactionalEventListener
    public void onQueueMessageEvent(String event) throws Exception {

        LOGGER.debug("Start:: sending message from QueueMessageEventHandler to queue="+ event);
        Thread.sleep(1000);
        LOGGER.debug("Start:: sending message from QueueMessageEventHandler to queue="+ event);

        
    }
}
