package com.here.owc;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class S3UserHtmlReporterTest {

    @Test
    void handleRequest() {
        Context mockContext = Mockito.mock(Context.class);
        S3UserHtmlReporter s3UserHtmlReporter = new S3UserHtmlReporter();
        SNSEvent event = new SNSEvent();

        s3UserHtmlReporter.handleRequest(event, mockContext);
    }
}