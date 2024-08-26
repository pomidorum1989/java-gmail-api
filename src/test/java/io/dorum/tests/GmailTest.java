package io.dorum.tests;

import io.dorum.GmailAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.dorum.GmailAPI.getSystemEnv;

public class GmailTest {

    @Test
    @DisplayName("Gmail test")
    public void gmailTest() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("type", "authorized_user");
        jsonMap.put("client_id", getSystemEnv("GMAIL_CLIENT_ID"));
        jsonMap.put("client_secret", getSystemEnv("GMAIL_CLIENT_SECRET"));
        jsonMap.put("refresh_token", getSystemEnv("GMAIL_REFRESH_SECRET"));
        System.out.println(jsonMap);

        Assertions.assertFalse(GmailAPI.getMessage().orElse("Message not found").isEmpty());
    }
}
