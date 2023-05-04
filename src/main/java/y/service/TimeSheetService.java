package y.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import y.service.dto.TimeSheetDTO;

/**
 * Service Interface for managing {@link y.domain.TimeSheet}.
 */
public interface TimeSheetService {
    /**
     * Save a timeSheet.
     *
     * @param timeSheetDTO the entity to save.
     * @return the persisted entity.
     */
    TimeSheetDTO save(TimeSheetDTO timeSheetDTO);

    /**
     * Updates a timeSheet.
     *
     * @param timeSheetDTO the entity to update.
     * @return the persisted entity.
     */
    TimeSheetDTO update(TimeSheetDTO timeSheetDTO);

    /**
     * Partially updates a timeSheet.
     *
     * @param timeSheetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TimeSheetDTO> partialUpdate(TimeSheetDTO timeSheetDTO);

    /**
     * Get all the timeSheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TimeSheetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" timeSheet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeSheetDTO> findOne(Long id);

    /**
     * Delete the "id" timeSheet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
