package fastcampus.websocketchat.repository.jpa;

import fastcampus.websocketchat.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChatRoomId(Long chatRoomId);
}
