package y.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import y.domain.Member;
import y.repository.MemberRepository;
import y.service.MemberService;

/**
 * Service Implementation for managing {@link Member}.
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member save(Member member) {
        log.debug("Request to save Member : {}", member);
        return memberRepository.save(member);
    }

    @Override
    public Member update(Member member) {
        log.debug("Request to update Member : {}", member);
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> partialUpdate(Member member) {
        log.debug("Request to partially update Member : {}", member);

        return memberRepository
            .findById(member.getId())
            .map(existingMember -> {
                if (member.getUserName() != null) {
                    existingMember.setUserName(member.getUserName());
                }
                if (member.getPassWord() != null) {
                    existingMember.setPassWord(member.getPassWord());
                }

                return existingMember;
            })
            .map(memberRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Member> findAll(Pageable pageable) {
        log.debug("Request to get all Members");
        return memberRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findOne(Long id) {
        log.debug("Request to get Member : {}", id);
        return memberRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Member : {}", id);
        memberRepository.deleteById(id);
    }

    @Override
    public Optional<Member> findByUserNamePassWord(String useName, String password) {
        return memberRepository.findByUserNamePassWord(useName,password);
    }


}
