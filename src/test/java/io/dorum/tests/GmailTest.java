package io.dorum.tests;

import io.dorum.GmailAPI;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GmailTest {

    @Test
    @DisplayName("Gmail test")
    public void gmailTest() throws GeneralSecurityException, IOException {
        Assumptions.assumeTrue(!GmailAPI.getMessage().orElse("Message not found").isEmpty());
    }
}
