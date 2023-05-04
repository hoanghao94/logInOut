package y.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link y.domain.TimeSheet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimeSheetDTO implements Serializable {

    private Long id;

    private Long idMember;

    private LocalDate logTime;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMember() {
        return idMember;
    }

    public void setIdMember(Long idMember) {
        this.idMember = idMember;
    }

    public LocalDate getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDate logTime) {
        this.logTime = logTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSheetDTO)) {
            return false;
        }

        TimeSheetDTO timeSheetDTO = (TimeSheetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, timeSheetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSheetDTO{" +
            "id=" + getId() +
            ", idMember=" + getIdMember() +
            ", logTime='" + getLogTime() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
