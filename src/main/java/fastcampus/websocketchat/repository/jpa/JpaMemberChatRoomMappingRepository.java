package fastcampus.websocketchat.repository.jpa;

import fastcampus.websocketchat.entity.MemberChatRoomMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberChatRoomMappingRepository extends JpaRepository<MemberChatRoomMapping, Long> {

}
