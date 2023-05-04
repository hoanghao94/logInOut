package y.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import y.domain.Member;

/**
 * Spring Data JPA repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {}
