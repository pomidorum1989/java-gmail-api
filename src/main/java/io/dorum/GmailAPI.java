package io.dorum;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.*;

import static io.dorum.utils.KeyChainAccess.getPasswordFromKeychain;

public class GmailAPI {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/gmail.readonly");

    private static Gmail getGmailService(HttpTransport httpTransport) throws IOException {
        Gson gson = new Gson();
        String osName = System.getProperty("os.name").toLowerCase();
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("type", "authorized_user");
        if (osName.contains("mac")) {
            jsonMap.put("client_id", getPasswordFromKeychain("gmail-client-id"));
            jsonMap.put("client_secret", getPasswordFromKeychain("gmail-client-secret"));
            jsonMap.put("refresh_token", getPasswordFromKeychain("gmail-refresh-token"));
        } else {
            jsonMap.put("client_id", getSystemEnv("GMAIL_CLIENT_ID"));
            jsonMap.put("client_secret", getSystemEnv("GMAIL_CLIENT_SECRET"));
            jsonMap.put("refresh_token", getSystemEnv("GMAIL_REFRESH_SECRET"));
        }
        String jsonString = gson.toJson(jsonMap);
        GoogleCredentials credentials = GoogleCredentials.
                fromStream(new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8)))
                .createScoped(SCOPES);
        return new Gmail.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                .setApplicationName("Gmail test")
                .build();
    }

    public static Optional<String> getMessage() {
        Gmail service = null;
        ListMessagesResponse response = null;
        try {
            final HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service = getGmailService(HTTP_TRANSPORT);
            response = service.users().messages().list("me").setMaxResults(1L).execute();
        } catch (GeneralSecurityException | IOException e) {
            System.out.println(e.getMessage());
        }

        assert response != null;
        List<Message> messages = response.getMessages();
        Message message = null;
        if (messages.isEmpty()) {
            System.out.println("No messages found.");
            return Optional.empty();
        } else {
            String messageId = messages.getFirst().getId();
            try {
                message = service.users().messages().get("me", messageId).execute();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            assert message != null;
            System.out.println("Message snippet: " + message.getSnippet());
        }
        return Optional.of(message.getSnippet());
    }

    public static String getSystemEnv(String variable) {
        try {
            return System.getenv(variable);
        } catch (NullPointerException | SecurityException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}

