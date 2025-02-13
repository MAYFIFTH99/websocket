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

        Map<String, Object> attributeMap = oAuth2User.getAttribute("kakao_account");
        String email = attributeMap.get("email").toString();
        Member member = jpaMemberRepository.findByEmail(email).orElseGet(() ->
                registerMember(attributeMap));

        return new CustomOAuth2User(member, oAuth2User.getAttributes());
    }

    private Member registerMember(Map<String, Object> attributeMap) {
         Member member = Member.builder()
                .email((String)attributeMap.get("email"))
                 .nickname((String) ((Map) attributeMap.get("profile")).get("nickname"))
                .name(attributeMap.get("name").toString())
                .phoneNumber(attributeMap.get("phone_number").toString())
                .gender(Gender.valueOf(((String)attributeMap.get("gender")).toUpperCase()))
                .birthDay(getBirthDay(attributeMap))
                .role(Role.USER)
                .build();

        return jpaMemberRepository.save(member);
    }

    private LocalDate getBirthDay(Map<String, Object> attributeMap) {
        String birthYear = attributeMap.get("birthyear").toString();
        String birthDay = attributeMap.get("birthday").toString();

        return LocalDate.parse(birthYear + birthDay, DateTimeFormatter.BASIC_ISO_DATE);
    }
}
