package com.here.owc;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class S3DevHtmlReporter implements RequestHandler<SNSEvent, String> {
    private static ApplicationContext ctx;

    static {
        ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
    }

    @Override
    public String handleRequest(SNSEvent snsEvent, Context context) {
        CommandLiner commandLiner = ctx.getBean(CommandLiner.class);
        commandLiner.run(snsEvent);

        return "KONEC";
    }
}
