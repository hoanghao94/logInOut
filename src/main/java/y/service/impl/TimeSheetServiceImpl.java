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

/**
 * Service Implementation for managing {@link TimeSheet}.
 */
@Service
@Transactional
public class TimeSheetServiceImpl implements TimeSheetService {

    private final Logger log = LoggerFactory.getLogger(TimeSheetServiceImpl.class);

    private final TimeSheetRepository timeSheetRepository;

    public TimeSheetServiceImpl(TimeSheetRepository timeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
    }

    @Override
    public TimeSheet save(TimeSheet timeSheet) {
        log.debug("Request to save TimeSheet : {}", timeSheet);
        return timeSheetRepository.save(timeSheet);
    }

    @Override
    public TimeSheet update(TimeSheet timeSheet) {
        log.debug("Request to update TimeSheet : {}", timeSheet);
        return timeSheetRepository.save(timeSheet);
    }

    @Override
    public Optional<TimeSheet> partialUpdate(TimeSheet timeSheet) {
        log.debug("Request to partially update TimeSheet : {}", timeSheet);

        return timeSheetRepository
            .findById(timeSheet.getId())
            .map(existingTimeSheet -> {
                if (timeSheet.getIdMember() != null) {
                    existingTimeSheet.setIdMember(timeSheet.getIdMember());
                }
                if (timeSheet.getLogTime() != null) {
                    existingTimeSheet.setLogTime(timeSheet.getLogTime());
                }
                if (timeSheet.getState() != null) {
                    existingTimeSheet.setState(timeSheet.getState());
                }

                return existingTimeSheet;
            })
            .map(timeSheetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TimeSheet> findAll(Pageable pageable) {
        log.debug("Request to get all TimeSheets");
        return timeSheetRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TimeSheet> findOne(Long id) {
        log.debug("Request to get TimeSheet : {}", id);
        return timeSheetRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeSheet : {}", id);
        timeSheetRepository.deleteById(id);
    }
}
