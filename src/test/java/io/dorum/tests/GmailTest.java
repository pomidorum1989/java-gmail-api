package io.dorum.tests;

import io.dorum.GmailAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GmailTest {

    @Test
    @DisplayName("Gmail test")
    public void gmailTest() {;
        System.out.println(GmailAPI.getSystemEnv("GMAIL_CLIENT_ID"));
        Assertions.assertFalse(GmailAPI.getMessage().orElse("Message not found").isEmpty());
    }
}
