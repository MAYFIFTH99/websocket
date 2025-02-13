package fastcampus.websocketchat.service;

import fastcampus.websocketchat.dto.MemberDto;
import fastcampus.websocketchat.entity.Member;
import fastcampus.websocketchat.entity.Role;
import fastcampus.websocketchat.repository.jpa.JpaMemberRepository;
import fastcampus.websocketchat.vo.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final JpaMemberRepository jpaMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = jpaMemberRepository.findByName(username).get();

        if (member.getRole() != Role.CONSULTANT) {
            throw new AccessDeniedException("상담사가 아닙니다");
        }

        return new CustomUserDetails(member);
    }

    public MemberDto saveMember(MemberDto dto) {
        Member member = MemberDto.to(dto);
        member.updatePassword(dto.password(), dto.confirmedPassword(), passwordEncoder);

        Member savedMember = jpaMemberRepository.save(member);
        return MemberDto.from(savedMember);

    }


}
