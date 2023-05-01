package qna.helper.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import qna.helper.email.EmailSender;
import qna.member.entity.Member;
import qna.member.service.MemberService;

@EnableAsync
@Configuration
@Component
@Slf4j
public class MemberRegistrationEventListener {
    private final EmailSender emailSender;
    private final MemberService memberService;

    public MemberRegistrationEventListener(EmailSender emailSender, MemberService memberService) {
        this.emailSender = emailSender;
        this.memberService = memberService;
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
            Member member = event.getMember();
            memberService.deleteMember(member.getMemberId());
        }
    }
}
