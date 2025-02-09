package fastcampus.websocketchat.repository.jpa;

import fastcampus.websocketchat.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
