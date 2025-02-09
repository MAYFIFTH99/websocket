package fastcampus.websocketchat.service;

import fastcampus.websocketchat.entity.ChatRoom;
import fastcampus.websocketchat.entity.Member;
import fastcampus.websocketchat.entity.MemberChatRoomMapping;
import fastcampus.websocketchat.repository.jpa.JpaChatRoomRepository;
import fastcampus.websocketchat.repository.jpa.JpaMemberChatRoomMappingRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final JpaChatRoomRepository jpaChatRoomRepository;
    private final JpaMemberChatRoomMappingRepository jpaMemberChatRoomMappingRepository;

    // 채팅방 생성
    public ChatRoom createChatRoom(Member member, String title){
        ChatRoom chatRoom = ChatRoom.builder()
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();
        ChatRoom savedChatRoom = jpaChatRoomRepository.save(chatRoom);

        MemberChatRoomMapping memberChatRoomMapping = MemberChatRoomMapping.builder()
                .chatRoom(savedChatRoom)
                .member(member)
                .build();
        jpaMemberChatRoomMappingRepository.save(memberChatRoomMapping);

        return savedChatRoom;
    }

    public Boolean joinChatRoom(Member member, Long chatRoomId){
        if (jpaMemberChatRoomMappingRepository.existsByMemberIdAndChatRoomId(member.getId(),
                chatRoomId)) {
            log.info("이미 채팅방에 참여하고 있습니다.");
            return false;
        }
        ChatRoom findChatRoom = jpaChatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        MemberChatRoomMapping savedMemberChatMapping = MemberChatRoomMapping.builder()
                .chatRoom(findChatRoom)
                .member(member)
                .build();
        jpaMemberChatRoomMappingRepository.save(savedMemberChatMapping);

        return true;
    }

    public Boolean leaveChatRoom(Member member, Long chatRoomId){
        if (!jpaMemberChatRoomMappingRepository.existsByMemberIdAndChatRoomId(member.getId(),
                chatRoomId)) {
            log.info("채팅방에 참여하고 있지 않습니다.");
            return false;
        }

        jpaMemberChatRoomMappingRepository.deleteByMemberIdAndChatRoomId(member.getId(),
                chatRoomId);
        return true;
    }

    public List<ChatRoom> getJoinedChatRooms(Member member){
        List<MemberChatRoomMapping> chatRoomList = jpaMemberChatRoomMappingRepository.findAllByMemberId(
                member.getId());

        return chatRoomList.stream()
                .map(MemberChatRoomMapping::getChatRoom)
                .toList();
    }
}
