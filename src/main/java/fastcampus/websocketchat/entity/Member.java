package fastcampus.websocketchat.entity;

import fastcampus.websocketchat.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String nickname;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime lastCheckedAt;

    public void updatePassword(String password, String confirmedPassword,
            PasswordEncoder passwordEncoder) {
        if (!password.equals(confirmedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        this.password = passwordEncoder.encode(password);

    }
}
