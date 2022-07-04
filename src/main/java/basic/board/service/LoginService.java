package basic.board.service;

import basic.board.entity.Member;
import basic.board.exception.NoSuchMemberException;
import basic.board.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final MemberMapper memberMapper;

    public Member loginCheck(String name, String password) {

        Optional<Member> findMember = memberMapper.findByName(name);

        Member member = findMember.orElseThrow(()->new NoSuchMemberException("존재하지 않는 아이디입니다"));

        if (member.getPassword().equals(password)) {
            return member;
        }else{
            return null;
        }
    }
}
