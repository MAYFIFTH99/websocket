package fastcampus.websocketchat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @Column(name = "chat_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatRoom")
    private Set<MemberChatRoomMapping> memberChatRoomMappings;

    private String title;

    private LocalDateTime createdAt;


    public MemberChatRoomMapping addMember(Member member){
        if (memberChatRoomMappings == null) {
            memberChatRoomMappings = new HashSet<>();
        }

        MemberChatRoomMapping memberChatRoomMapping = MemberChatRoomMapping.builder()
                .chatRoom(this)
                .member(member)
                .build();
        memberChatRoomMappings.add(memberChatRoomMapping);
        return memberChatRoomMapping;
    }
}
