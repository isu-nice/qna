package qna.question.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qna.audit.BaseEntity;
import qna.user.entity.User;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 25, nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private QuestionStatus status = QuestionStatus.QUESTION_REGISTERED;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private QuestionPost post;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public enum QuestionStatus {
        QUESTION_REGISTERED("질문 등록"),
        QUESTION_ANSWERED("답변 완료"),
        QUESTION_DELETED("질문 삭제"),
        QUESTION_DEACTIVATED("질문 비활성화");

        @Getter
        private String status;

        QuestionStatus(String status) {
            this.status = status;
        }
    }

    public enum QuestionPost {
        PUBLIC,
        SECRET;
    }
}
