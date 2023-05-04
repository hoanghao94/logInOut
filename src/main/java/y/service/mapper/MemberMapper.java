package y.service.mapper;

import org.mapstruct.*;
import y.domain.Member;
import y.service.dto.MemberDTO;

/**
 * Mapper for the entity {@link Member} and its DTO {@link MemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {}
