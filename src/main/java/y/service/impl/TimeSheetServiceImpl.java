package y.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import y.domain.TimeSheet;
import y.repository.TimeSheetRepository;
import y.service.TimeSheetService;
import y.service.dto.TimeSheetDTO;
import y.service.mapper.TimeSheetMapper;

/**
 * Service Implementation for managing {@link TimeSheet}.
 */
@Service
@Transactional
public class TimeSheetServiceImpl implements TimeSheetService {

    private final Logger log = LoggerFactory.getLogger(TimeSheetServiceImpl.class);

    private final TimeSheetRepository timeSheetRepository;

    private final TimeSheetMapper timeSheetMapper;

    public TimeSheetServiceImpl(TimeSheetRepository timeSheetRepository, TimeSheetMapper timeSheetMapper) {
        this.timeSheetRepository = timeSheetRepository;
        this.timeSheetMapper = timeSheetMapper;
    }

    @Override
    public TimeSheetDTO save(TimeSheetDTO timeSheetDTO) {
        log.debug("Request to save TimeSheet : {}", timeSheetDTO);
        TimeSheet timeSheet = timeSheetMapper.toEntity(timeSheetDTO);
        timeSheet = timeSheetRepository.save(timeSheet);
        return timeSheetMapper.toDto(timeSheet);
    }

    @Override
    public TimeSheetDTO update(TimeSheetDTO timeSheetDTO) {
        log.debug("Request to update TimeSheet : {}", timeSheetDTO);
        TimeSheet timeSheet = timeSheetMapper.toEntity(timeSheetDTO);
        timeSheet = timeSheetRepository.save(timeSheet);
        return timeSheetMapper.toDto(timeSheet);
    }

    @Override
    public Optional<TimeSheetDTO> partialUpdate(TimeSheetDTO timeSheetDTO) {
        log.debug("Request to partially update TimeSheet : {}", timeSheetDTO);

        return timeSheetRepository
            .findById(timeSheetDTO.getId())
            .map(existingTimeSheet -> {
                timeSheetMapper.partialUpdate(existingTimeSheet, timeSheetDTO);

                return existingTimeSheet;
            })
            .map(timeSheetRepository::save)
            .map(timeSheetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TimeSheetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TimeSheets");
        return timeSheetRepository.findAll(pageable).map(timeSheetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TimeSheetDTO> findOne(Long id) {
        log.debug("Request to get TimeSheet : {}", id);
        return timeSheetRepository.findById(id).map(timeSheetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeSheet : {}", id);
        timeSheetRepository.deleteById(id);
    }
}
