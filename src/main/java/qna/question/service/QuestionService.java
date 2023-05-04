package qna.question.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.member.service.MemberService;
import qna.question.entity.Question;
import qna.question.repository.QuestionRepository;

@Service
@Transactional
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    public QuestionService(QuestionRepository questionRepository,
                           MemberService memberService) {
        this.questionRepository = questionRepository;
        this.memberService = memberService;
    }

    public Question createQuestion(Question question) {
        verifyQuestion(question);

        return questionRepository.save(question);
    }

    private void verifyQuestion(Question question) {
        // 회원이 존재하는지 확인
        memberService.findVerifyMember(question.getMember().getMemberId());

        // 일반 회원인지 확인
        memberService.verifyGeneralMember(question.getMember());
    }
}
