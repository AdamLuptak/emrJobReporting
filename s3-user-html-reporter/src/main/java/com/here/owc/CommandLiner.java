package com.here.owc;


import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommandLiner {

    static final Logger logger = LoggerFactory.getLogger(CommandLiner.class);


    @Autowired
    String helloWorld;

    public void run(SNSEvent snsEvent) {
        logger.info("s3-user-friendly-reporter");
        logger.info("Received this message payload from SNS: " + snsEvent.getRecords().get(0).getSNS().getMessage());
    }

}
