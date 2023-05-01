package qna.helper.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import qna.member.entity.Member;

@Getter
public class MemberRegistrationApplicationEvent extends ApplicationEvent {
    private Member member;

    public MemberRegistrationApplicationEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }
}
