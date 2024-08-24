import io.dorum.GmailAPI;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class GmailTest {

    @Test
    public void gmailTest() throws GeneralSecurityException, IOException {
        Assumptions.assumeTrue(!GmailAPI.getMessage().orElse("Message not found").isEmpty());
    }
}
