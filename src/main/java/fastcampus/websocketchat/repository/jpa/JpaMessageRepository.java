package fastcampus.websocketchat.repository.jpa;

import fastcampus.websocketchat.entity.Message;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChatRoomId(Long chatRoomId);

    Boolean existsByChatRoomIdAndCreatedAtAfter(Long chatRoomId, LocalDateTime lastCheckedAt);
}
