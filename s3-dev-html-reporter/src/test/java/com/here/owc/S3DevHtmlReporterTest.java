package com.here.owc;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class S3DevHtmlReporterTest {

    @Test
    void handleRequest() {
        Context mockContext = Mockito.mock(Context.class);
        S3DevHtmlReporter s3DevHtmlReporter = new S3DevHtmlReporter();
        SNSEvent event = new SNSEvent();

        s3DevHtmlReporter.handleRequest(event, mockContext);
    }
}