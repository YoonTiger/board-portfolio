package basic.board.service;

import basic.board.entity.Board;
import basic.board.entity.Member;
import basic.board.exception.DuplicateMemberException;
import basic.board.repository.BoardMapper;
import basic.board.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final BoardMapper boardMapper;


    public Long join(Member member){
        validateDuplicateMember(member);
        memberMapper.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember =
                memberMapper.findByName(member.getName());
        if (findMember.isPresent()){
            throw new DuplicateMemberException("이미 존재하는 회원입니다");
        }
    }

    public Member findById(Long id){
        return memberMapper.findById(id).orElse(null);
    }

    public Member findByName(String name){
        return memberMapper.findByName(name).orElse(null);
    }

    public List<Member> findAll(){
        return memberMapper.findAll();
    }

    public void delete(Long id){
        memberMapper.delete(id);
    }

    public void update(Member member){
        memberMapper.update(member);
    }

    public List<Board> myBoardList(Long id){
        return boardMapper.findByMemberId(id);
    }



}
