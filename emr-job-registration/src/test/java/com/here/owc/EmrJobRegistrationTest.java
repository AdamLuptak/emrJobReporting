package com.here.owc;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.HashMap;


class EmrJobRegistrationTest {

    @Test
    void handleRequest() {
        Context mockContext = Mockito.mock(Context.class);
        EmrJobRegistration emrJobRegistration = new EmrJobRegistration();
        SNSEvent event = new SNSEvent();

        emrJobRegistration.handleRequest(event, mockContext);
    }
}