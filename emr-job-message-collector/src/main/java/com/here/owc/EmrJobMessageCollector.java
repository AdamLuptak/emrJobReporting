package com.here.owc;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class EmrJobMessageCollector implements RequestHandler<ScheduledEvent, String> {
    private static ApplicationContext ctx;

    static {
        ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
    }

    @Override
    public String handleRequest(ScheduledEvent event, Context context) {
        CommandLiner commandLiner = ctx.getBean(CommandLiner.class);
        commandLiner.run(event);

        return "KONEC";
    }
}
