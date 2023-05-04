package y.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import y.domain.TimeSheet;

/**
 * Service Interface for managing {@link TimeSheet}.
 */
public interface TimeSheetService {
    /**
     * Save a timeSheet.
     *
     * @param timeSheet the entity to save.
     * @return the persisted entity.
     */
    TimeSheet save(TimeSheet timeSheet);

    /**
     * Updates a timeSheet.
     *
     * @param timeSheet the entity to update.
     * @return the persisted entity.
     */
    TimeSheet update(TimeSheet timeSheet);

    /**
     * Partially updates a timeSheet.
     *
     * @param timeSheet the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TimeSheet> partialUpdate(TimeSheet timeSheet);

    /**
     * Get all the timeSheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TimeSheet> findAll(Pageable pageable);

    /**
     * Get the "id" timeSheet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeSheet> findOne(Long id);

    /**
     * Delete the "id" timeSheet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
