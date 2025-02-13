package fastcampus.websocketchat.service;

import fastcampus.websocketchat.enums.Gender;
import fastcampus.websocketchat.entity.Member;
import fastcampus.websocketchat.entity.Role;
import fastcampus.websocketchat.repository.jpa.JpaMemberRepository;
import fastcampus.websocketchat.vo.CustomOAuth2User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuthUserService extends DefaultOAuth2UserService {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        Member member = jpaMemberRepository.findByEmail(email).orElseGet(() -> {
            Member newMember = MemberFactory.create(userRequest, oAuth2User);
            return jpaMemberRepository.save(newMember);
        });

        return new CustomOAuth2User(member, oAuth2User.getAttributes());
    }

}
