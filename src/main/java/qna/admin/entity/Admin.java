package qna.admin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Admin {
    @Id
    private Long adminId;

    private String name;
    private String email;
    private String phone;
}
