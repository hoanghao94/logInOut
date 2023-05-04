package y.service.mapper;

import org.mapstruct.*;
import y.domain.TimeSheet;
import y.service.dto.TimeSheetDTO;

/**
 * Mapper for the entity {@link TimeSheet} and its DTO {@link TimeSheetDTO}.
 */
@Mapper(componentModel = "spring")
public interface TimeSheetMapper extends EntityMapper<TimeSheetDTO, TimeSheet> {}
