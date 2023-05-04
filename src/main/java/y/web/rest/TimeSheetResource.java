package y.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import y.domain.TimeSheet;
import y.repository.TimeSheetRepository;
import y.service.TimeSheetService;
import y.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link y.domain.TimeSheet}.
 */
@RestController
@RequestMapping("/api")
public class TimeSheetResource {

    private final Logger log = LoggerFactory.getLogger(TimeSheetResource.class);

    private static final String ENTITY_NAME = "timeSheet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeSheetService timeSheetService;

    private final TimeSheetRepository timeSheetRepository;

    public TimeSheetResource(TimeSheetService timeSheetService, TimeSheetRepository timeSheetRepository) {
        this.timeSheetService = timeSheetService;
        this.timeSheetRepository = timeSheetRepository;
    }

    /**
     * {@code POST  /time-sheets} : Create a new timeSheet.
     *
     * @param timeSheet the timeSheet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeSheet, or with status {@code 400 (Bad Request)} if the timeSheet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-sheets")
    public ResponseEntity<TimeSheet> createTimeSheet(@RequestBody TimeSheet timeSheet) throws URISyntaxException {
        log.debug("REST request to save TimeSheet : {}", timeSheet);
        if (timeSheet.getId() != null) {
            throw new BadRequestAlertException("A new timeSheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeSheet result = timeSheetService.save(timeSheet);
        return ResponseEntity
            .created(new URI("/api/time-sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /time-sheets/:id} : Updates an existing timeSheet.
     *
     * @param id the id of the timeSheet to save.
     * @param timeSheet the timeSheet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeSheet,
     * or with status {@code 400 (Bad Request)} if the timeSheet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeSheet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-sheets/{id}")
    public ResponseEntity<TimeSheet> updateTimeSheet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimeSheet timeSheet
    ) throws URISyntaxException {
        log.debug("REST request to update TimeSheet : {}, {}", id, timeSheet);
        if (timeSheet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timeSheet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeSheetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TimeSheet result = timeSheetService.update(timeSheet);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timeSheet.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /time-sheets/:id} : Partial updates given fields of an existing timeSheet, field will ignore if it is null
     *
     * @param id the id of the timeSheet to save.
     * @param timeSheet the timeSheet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeSheet,
     * or with status {@code 400 (Bad Request)} if the timeSheet is not valid,
     * or with status {@code 404 (Not Found)} if the timeSheet is not found,
     * or with status {@code 500 (Internal Server Error)} if the timeSheet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/time-sheets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TimeSheet> partialUpdateTimeSheet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimeSheet timeSheet
    ) throws URISyntaxException {
        log.debug("REST request to partial update TimeSheet partially : {}, {}", id, timeSheet);
        if (timeSheet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timeSheet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeSheetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TimeSheet> result = timeSheetService.partialUpdate(timeSheet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timeSheet.getId().toString())
        );
    }

    /**
     * {@code GET  /time-sheets} : get all the timeSheets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeSheets in body.
     */
    @GetMapping("/time-sheets")
    public ResponseEntity<List<TimeSheet>> getAllTimeSheets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TimeSheets");
        Page<TimeSheet> page = timeSheetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /time-sheets/:id} : get the "id" timeSheet.
     *
     * @param id the id of the timeSheet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeSheet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-sheets/{id}")
    public ResponseEntity<TimeSheet> getTimeSheet(@PathVariable Long id) {
        log.debug("REST request to get TimeSheet : {}", id);
        Optional<TimeSheet> timeSheet = timeSheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeSheet);
    }

    /**
     * {@code DELETE  /time-sheets/:id} : delete the "id" timeSheet.
     *
     * @param id the id of the timeSheet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-sheets/{id}")
    public ResponseEntity<Void> deleteTimeSheet(@PathVariable Long id) {
        log.debug("REST request to delete TimeSheet : {}", id);
        timeSheetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
