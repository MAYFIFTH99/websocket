package fastcampus.websocketchat.repository.jpa;

import fastcampus.websocketchat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
