package y.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import y.IntegrationTest;
import y.domain.TimeSheet;
import y.repository.TimeSheetRepository;

/**
 * Integration tests for the {@link TimeSheetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TimeSheetResourceIT {

    private static final Long DEFAULT_ID_MEMBER = 1L;
    private static final Long UPDATED_ID_MEMBER = 2L;

    private static final LocalDate DEFAULT_LOG_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LOG_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/time-sheets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeSheetMockMvc;

    private TimeSheet timeSheet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSheet createEntity(EntityManager em) {
        TimeSheet timeSheet = new TimeSheet().idMember(DEFAULT_ID_MEMBER).logTime(DEFAULT_LOG_TIME).state(DEFAULT_STATE);
        return timeSheet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSheet createUpdatedEntity(EntityManager em) {
        TimeSheet timeSheet = new TimeSheet().idMember(UPDATED_ID_MEMBER).logTime(UPDATED_LOG_TIME).state(UPDATED_STATE);
        return timeSheet;
    }

    @BeforeEach
    public void initTest() {
        timeSheet = createEntity(em);
    }

    @Test
    @Transactional
    void createTimeSheet() throws Exception {
        int databaseSizeBeforeCreate = timeSheetRepository.findAll().size();
        // Create the TimeSheet
        restTimeSheetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isCreated());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeCreate + 1);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getIdMember()).isEqualTo(DEFAULT_ID_MEMBER);
        assertThat(testTimeSheet.getLogTime()).isEqualTo(DEFAULT_LOG_TIME);
        assertThat(testTimeSheet.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    void createTimeSheetWithExistingId() throws Exception {
        // Create the TimeSheet with an existing ID
        timeSheet.setId(1L);

        int databaseSizeBeforeCreate = timeSheetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeSheetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTimeSheets() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList
        restTimeSheetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.intValue())))
            .andExpect(jsonPath("$.[*].logTime").value(hasItem(DEFAULT_LOG_TIME.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }

    @Test
    @Transactional
    void getTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get the timeSheet
        restTimeSheetMockMvc
            .perform(get(ENTITY_API_URL_ID, timeSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeSheet.getId().intValue()))
            .andExpect(jsonPath("$.idMember").value(DEFAULT_ID_MEMBER.intValue()))
            .andExpect(jsonPath("$.logTime").value(DEFAULT_LOG_TIME.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    void getNonExistingTimeSheet() throws Exception {
        // Get the timeSheet
        restTimeSheetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet
        TimeSheet updatedTimeSheet = timeSheetRepository.findById(timeSheet.getId()).get();
        // Disconnect from session so that the updates on updatedTimeSheet are not directly saved in db
        em.detach(updatedTimeSheet);
        updatedTimeSheet.idMember(UPDATED_ID_MEMBER).logTime(UPDATED_LOG_TIME).state(UPDATED_STATE);

        restTimeSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTimeSheet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTimeSheet))
            )
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getIdMember()).isEqualTo(UPDATED_ID_MEMBER);
        assertThat(testTimeSheet.getLogTime()).isEqualTo(UPDATED_LOG_TIME);
        assertThat(testTimeSheet.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void putNonExistingTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timeSheet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTimeSheetWithPatch() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet using partial update
        TimeSheet partialUpdatedTimeSheet = new TimeSheet();
        partialUpdatedTimeSheet.setId(timeSheet.getId());

        partialUpdatedTimeSheet.idMember(UPDATED_ID_MEMBER).logTime(UPDATED_LOG_TIME);

        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeSheet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeSheet))
            )
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getIdMember()).isEqualTo(UPDATED_ID_MEMBER);
        assertThat(testTimeSheet.getLogTime()).isEqualTo(UPDATED_LOG_TIME);
        assertThat(testTimeSheet.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    void fullUpdateTimeSheetWithPatch() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet using partial update
        TimeSheet partialUpdatedTimeSheet = new TimeSheet();
        partialUpdatedTimeSheet.setId(timeSheet.getId());

        partialUpdatedTimeSheet.idMember(UPDATED_ID_MEMBER).logTime(UPDATED_LOG_TIME).state(UPDATED_STATE);

        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeSheet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeSheet))
            )
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getIdMember()).isEqualTo(UPDATED_ID_MEMBER);
        assertThat(testTimeSheet.getLogTime()).isEqualTo(UPDATED_LOG_TIME);
        assertThat(testTimeSheet.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, timeSheet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(timeSheet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeDelete = timeSheetRepository.findAll().size();

        // Delete the timeSheet
        restTimeSheetMockMvc
            .perform(delete(ENTITY_API_URL_ID, timeSheet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
