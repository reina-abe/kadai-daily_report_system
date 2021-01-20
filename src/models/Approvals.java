package models;

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

@Table(name = "approvals")
@NamedQueries({
    @NamedQuery(
            name = "getAllUnapproved",
            query = "SELECT r FROM Report AS r WHERE r.approval = :unapproved AND r.employee.position <= :login_employee AND r.employee.id <> :login_id ORDER BY r.updated_at DESC"
            ),
    @NamedQuery(
            name = "getUnapprovedCount",
            query = "SELECT COUNT(r) FROM Report AS r WHERE r.approval = :unapproved AND r.employee.position <= :login_employee AND r.employee.id <> :login_id"
            ),
    @NamedQuery(
            name = "getApprovedHistry",
            query = "SELECT a FROM Approvals AS a, Report AS r WHERE r.id = a.report.id AND a.report.id = :report ORDER BY a.id DESC"
            ),
    @NamedQuery(
            name = "getUnpprovedHistry",
            query = "SELECT a FROM Approvals AS a, Report AS r WHERE a.approval = 2 AND r.id = a.report.id AND a.report.id = :report ORDER BY a.id DESC"
            )
})
@Entity
public class Approvals {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "approval", nullable = false)
    private Integer approval;

    @Column(name = "comment", length = 255, nullable = false)
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getApproval() {
        return approval;
    }

    public void setApproval(Integer approval) {
        this.approval = approval;
    }

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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
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

}

