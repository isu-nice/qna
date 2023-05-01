package qna.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qna.admin.entity.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}
