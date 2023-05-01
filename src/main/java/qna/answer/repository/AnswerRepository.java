package qna.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qna.answer.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // TODO 쿼리 추가
}
