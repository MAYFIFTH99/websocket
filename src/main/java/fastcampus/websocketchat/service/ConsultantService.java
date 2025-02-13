package fastcampus.websocketchat.service;

import fastcampus.websocketchat.dto.ChatRoomDto;
import fastcampus.websocketchat.dto.MemberDto;
import fastcampus.websocketchat.entity.ChatRoom;
import fastcampus.websocketchat.entity.Member;
import fastcampus.websocketchat.entity.Role;
import fastcampus.websocketchat.repository.jpa.JpaChatRoomRepository;
import fastcampus.websocketchat.repository.jpa.JpaMemberRepository;
import fastcampus.websocketchat.vo.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultantService implements UserDetailsService {

    private final JpaMemberRepository jpaMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JpaChatRoomRepository jpaChatRoomRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = jpaMemberRepository.findByName(username).get();

        if (member.getRole() != Role.CONSULTANT) {
            throw new AccessDeniedException("상담사가 아닙니다");
        }

        return new CustomUserDetails(member, null);
    }

    public MemberDto saveMember(MemberDto dto) {
        Member member = MemberDto.to(dto);
        member.updatePassword(dto.password(), dto.confirmedPassword(), passwordEncoder);

        Member savedMember = jpaMemberRepository.save(member);
        return MemberDto.from(savedMember);

    }


    @ResponseBody
    @GetMapping
    public Page<ChatRoomDto> getAllChatrooms(Pageable pageable) {
        Page<ChatRoom> chatroomList = jpaChatRoomRepository.findAll(pageable);
        return chatroomList.map(ChatRoomDto::from);
    }

}
