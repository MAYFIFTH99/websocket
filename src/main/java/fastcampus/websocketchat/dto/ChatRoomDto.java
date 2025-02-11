package fastcampus.websocketchat.dto;

import fastcampus.websocketchat.entity.ChatRoom;
import java.time.LocalDateTime;

public record ChatRoomDto(Long id, String title, Integer memberCount, LocalDateTime createAt) {

    public static ChatRoomDto from (ChatRoom chatRoom){
        return new ChatRoomDto(
                chatRoom.getId(),
                chatRoom.getTitle(),
                chatRoom.getMemberChatRoomMappings().size(),
                chatRoom.getCreatedAt());
    }

}
