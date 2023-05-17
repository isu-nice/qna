package qna.user.entity;

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
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(length = 8, nullable = false)
    private String name;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 12, nullable = false, unique = true)
    private String phone;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.MEMBER_ACTIVE;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserAuth auth = UserAuth.GENERAL_MEMBER;

    @OneToMany(mappedBy = "user")
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public enum UserStatus{
        MEMBER_ACTIVE("활동 중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        UserStatus(String status) {
            this.status = status;
        }
    }

    public enum UserAuth{
        ADMIN,
        GENERAL_MEMBER;
    }
}
