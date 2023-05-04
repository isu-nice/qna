package qna.answer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qna.audit.BaseEntity;
import qna.member.entity.Member;
import qna.question.entity.Question;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false)
    private String content;

    private AnswerPost post;

    @OneToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public enum AnswerPost {
        PUBLIC("공개글"),
        SECRET("비밀글");

        @Getter
        private String post;

        AnswerPost(String post) {
            this.post = post;
        }
    }
}
