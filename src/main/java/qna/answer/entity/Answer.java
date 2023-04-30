package qna.answer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qna.admin.entity.Admin;
import qna.question.entity.Question;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false)
    private String content;

    private Question question;

    private Admin admin;

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
