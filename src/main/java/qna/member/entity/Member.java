package qna.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qna.audit.BaseEntity;
import qna.question.entity.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(length = 8, nullable = false)
    private String name;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 12, nullable = false, unique = true)
    private String phone;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberAuth memberAuth = MemberAuth.GENERAL_MEMBER;

    @OneToMany(mappedBy = "member")
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public enum MemberStatus{
        MEMBER_ACTIVE("활동 중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

    public enum MemberAuth {
        GENERAL_MEMBER("일반 회원"),
        ADMIN("관리자");

        @Getter
        private String auth;

        MemberAuth(String auth) {
            this.auth = auth;
        }
    }
}
