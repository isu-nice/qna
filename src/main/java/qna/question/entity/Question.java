package qna.question.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qna.member.entity.Member;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question {
    @Id
    private Long questionId;

    private String title;
    private String content;
    private Member member;
    private QuestionStatus status;
    private QuestionPost post;

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
        PUBLIC("공개글"),
        SECRET("비밀글");

        @Getter
        private String post;

        QuestionPost(String post) {
            this.post = post;
        }
    }
}
