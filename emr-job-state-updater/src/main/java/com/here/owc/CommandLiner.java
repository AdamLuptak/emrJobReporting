package com.here.owc;


import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
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

    public void run(ScheduledEvent input) {
        logger.info("sdfsff");
        Map<String, Object> detail = input.getDetail();
        detail.entrySet().forEach(e ->
                logger.info((String) e.getValue())
        );


    }
}
