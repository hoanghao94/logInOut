package y.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A TimeSheet.
 */
@Entity
@Table(name = "time_sheet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimeSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_member")
    private Long idMember;

    @Column(name = "log_time")
    private LocalDate logTime;

    @Column(name = "state")
    private String state;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TimeSheet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMember() {
        return this.idMember;
    }

    public TimeSheet idMember(Long idMember) {
        this.setIdMember(idMember);
        return this;
    }

    public void setIdMember(Long idMember) {
        this.idMember = idMember;
    }

    public LocalDate getLogTime() {
        return this.logTime;
    }

    public TimeSheet logTime(LocalDate logTime) {
        this.setLogTime(logTime);
        return this;
    }

    public void setLogTime(LocalDate logTime) {
        this.logTime = logTime;
    }

    public String getState() {
        return this.state;
    }

    public TimeSheet state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSheet)) {
            return false;
        }
        return id != null && id.equals(((TimeSheet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSheet{" +
            "id=" + getId() +
            ", idMember=" + getIdMember() +
            ", logTime='" + getLogTime() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
