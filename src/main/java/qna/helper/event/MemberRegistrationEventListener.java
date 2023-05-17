package qna.helper.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import qna.helper.email.EmailSender;
import qna.user.entity.User;
import qna.user.service.UserService;

@EnableAsync
@Configuration
@Component
@Slf4j
public class MemberRegistrationEventListener {
    private final EmailSender emailSender;
    private final UserService userService;

    public MemberRegistrationEventListener(EmailSender emailSender, UserService userService) {
        this.emailSender = emailSender;
        this.userService = userService;
    }

    @Async
    @EventListener
    public void listen(MemberRegistrationApplicationEvent event) throws  Exception {
        try {
            String message = "any email message";
            emailSender.sendEmail(message);
        }catch (InterruptedException e) {
            e.printStackTrace();
            log.error("rollback for member registration");
            User user = event.getUser();
            userService.deleteUser(user.getUserId());
        }
    }
}
