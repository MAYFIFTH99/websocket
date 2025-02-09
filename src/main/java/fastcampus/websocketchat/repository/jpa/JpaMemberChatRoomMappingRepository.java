package fastcampus.websocketchat.repository.jpa;

import fastcampus.websocketchat.entity.ChatRoom;
import fastcampus.websocketchat.entity.MemberChatRoomMapping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaMemberChatRoomMappingRepository extends JpaRepository<MemberChatRoomMapping, Long> {

    boolean existsByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

    void deleteByMemberIdAndChatRoomId(Long id, Long chatRoomId);

    @Query("select m.chatRoom from MemberChatRoomMapping m where m.member.id = :memberId")
    List<MemberChatRoomMapping> findAllByMemberId(Long memberId);
}
