package qna.member.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import qna.helper.event.MemberRegistrationApplicationEvent;
import qna.member.entity.Member;
import qna.member.repository.MemberRepository;

import java.util.Optional;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;

    public MemberService(MemberRepository memberRepository, ApplicationEventPublisher publisher) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;
    }

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        authorizeAdminOrGeneral(member);
        Member savedMember = memberRepository.save(member);

        // 이메일 전송
        publisher.publishEvent(new MemberRegistrationApplicationEvent(this, savedMember));
        return savedMember;
    }

    // 관리자 이메일이면 관리자 권한 부여
    private void authorizeAdminOrGeneral(Member member) {
        final String AUTH_EMAIL = "admin@gmail.com";

        if(member.getMemberAuth().getAuth().equals(AUTH_EMAIL)){
            member.setMemberAuth(Member.MemberAuth.ADMIN);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member) {
        Member findMember = findVerifyMember(member.getMemberId());

        Optional.ofNullable(member.getName())
                .ifPresent(findMember::setName);
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> member.setPhone(phone));
        Optional.ofNullable(member.getMemberStatus())
                .ifPresent(findMember::setMemberStatus);

        return memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId) {
        return findVerifyMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(
                PageRequest.of(page, size, Sort.by("memberId").descending()));
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifyMember(memberId);
        memberRepository.delete(findMember);
    }

    @Transactional(readOnly = true)
    public Member findVerifyMember(long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        return member.orElseThrow(() ->
                // TODO 예외처리 지정
                new IllegalArgumentException("not found member"));
    }

    // 관리자인지 검증
    public void verifyAdmin(Member member) {
        if(member.getMemberAuth() != Member.MemberAuth.ADMIN){
            // TODO 예외처리 지정
            throw new IllegalArgumentException("not admin");
        }
    }

    // 일반 회원인지 검증
    public void verifyGeneralMember(Member member) {
        if(member.getMemberAuth() != Member.MemberAuth.GENERAL_MEMBER){
            // TODO 예외처리 지정
            throw new IllegalArgumentException("not general member");
        }
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()){
            // TODO 예외처리 지정
            throw new IllegalArgumentException("already exist email");
        }
    }
}
