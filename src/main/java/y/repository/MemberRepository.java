package y.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import y.domain.Member;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.userName = :userName AND m.passWord = :password")
    Optional<Member> findByUserNamePassWord(@Param("userName") String userName, @Param("password") String password);

}
