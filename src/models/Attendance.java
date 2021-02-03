package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "attendance")
@NamedQueries({
    @NamedQuery(
            name = "getAttendedEmployee",
            query = "SELECT a FROM Attendance AS a WHERE a.employee = :employee AND a.date = :date"
            ),
        @NamedQuery(
                name = "getAttendReport",
                query = "SELECT a FROM Attendance AS a WHERE a.employee = :employee AND a.date >= :start_date AND a.date <= :end_date"
            ),
        @NamedQuery(
                name = "CheckAttended",
                query = "SELECT COUNT(a) FROM Attendance AS a WHERE a.employee = :employee AND a.date = :date"
            ),
        @NamedQuery(
                name = "CheckLeft",
                query = "SELECT COUNT(a) FROM Attendance AS a WHERE a.employee = :employee AND a.date = :date AND a.start_at <> a.finish_at"
            )
})

@Entity
public class Attendance {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "start_at", nullable = false)
    private Timestamp start_at;

    @Column(name = "finish_at", nullable = false)
    private Timestamp finish_at;

    @Column(name = "date", nullable = false)
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getStart_at() {
        return start_at;
    }

    public void setStart_at(Timestamp start_at) {
        this.start_at = start_at;
    }

    public Timestamp getFinish_at() {
        return finish_at;
    }

    public void setFinish_at(Timestamp finish_at) {
        this.finish_at = finish_at;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
