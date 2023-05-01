package qna.helper.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    @Bean
    EmailSendable emailSendable() {
        return new TemplateEmailSendable();
    }
}
