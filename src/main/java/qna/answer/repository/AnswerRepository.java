package qna.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qna.answer.entity.Answer;
import qna.question.entity.Question;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findAnswerByQuestion(Question question);
    // TODO 쿼리 추가
}
