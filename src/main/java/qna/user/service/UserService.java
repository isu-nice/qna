package qna.user.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import qna.helper.event.MemberRegistrationApplicationEvent;
import qna.user.entity.User;
import qna.user.repository.UserRepository;

import java.util.Optional;

import static qna.user.entity.User.UserAuth.ADMIN;
import static qna.user.entity.User.UserAuth.GENERAL_MEMBER;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;

    public UserService(UserRepository userRepository, ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.publisher = publisher;
    }

    public User createMember(User user) {
        verifyExistsEmail(user.getEmail());
        authorizeAdminOrGeneral(user);
        User savedUser = userRepository.save(user);

        // 이메일 전송
        publisher.publishEvent(new MemberRegistrationApplicationEvent(this, savedUser));
        return savedUser;
    }

    // 관리자 이메일이면 관리자 권한 부여
    private void authorizeAdminOrGeneral(User user) {
        final String AUTH_EMAIL = "admin@gmail.com";

        if (user.getEmail().equals(AUTH_EMAIL)) {
            user.setAuth(ADMIN);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public User updateUser(User user) {
        User findUser = findVerifyUser(user.getUserId());

        Optional.ofNullable(user.getName())
                .ifPresent(findUser::setName);
        Optional.ofNullable(user.getPhone())
                .ifPresent(phone -> user.setPhone(phone));
        Optional.ofNullable(user.getStatus())
                .ifPresent(findUser::setStatus);
        Optional.ofNullable(user.getAuth())
                .ifPresent(findUser::setAuth);

        return userRepository.save(findUser);
    }

    @Transactional(readOnly = true)
    public User findUser(long userId) {
        return findVerifyUser(userId);
    }

    public Page<User> findUsers(int page, int size) {
        return userRepository.findAll(
                PageRequest.of(page, size, Sort.by("userId").descending()));
    }

    public void deleteUser(long userId) {
        User findUser = findVerifyUser(userId);
        userRepository.delete(findUser);
    }

    @Transactional(readOnly = true)
    public User findVerifyUser(long userId) {
        Optional<User> user = userRepository.findById(userId);

        return user.orElseThrow(() ->
                // TODO 예외처리 지정
                new IllegalArgumentException("not found user"));
    }

    // 관리자인지 검증
    public void verifyAdmin(User user) {
        if (user.getAuth() != ADMIN) {
            // TODO 예외처리 지정
            throw new IllegalArgumentException("not admin");
        }
    }

    // 일반 회원인지 검증
    public void verifyGeneralMember(User user) {
        if (user.getAuth() != GENERAL_MEMBER) {
            // TODO 예외처리 지정
            throw new IllegalArgumentException("not general member");
        }
    }

    // 활동중인 회원인지 검증
    public void verifyActiveMember(User user) {
        if (user.getStatus() != User.UserStatus.MEMBER_ACTIVE) {
            // TODO 예외처리 지정
            throw new IllegalArgumentException("not active member");
        }
    }

    private void verifyExistsEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            // TODO 예외처리 지정
            throw new IllegalArgumentException("already exist email");
        }
    }
}
