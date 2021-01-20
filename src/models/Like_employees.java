package models;

import java.sql.Timestamp; //秒まで

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

@Table(name = "like_employees")
@NamedQueries({
    @NamedQuery(
            name = "getAllLikeEmployees",
            query = "SELECT le FROM Like_employees AS le WHERE le.report = :report ORDER BY le.created_at DESC"
            ),
    @NamedQuery(
            name = "getLikeEmployeesCount",
            query = "SELECT COUNT(le) FROM Like_employees AS le WHERE le.report = :report"
            ),
    @NamedQuery(
            name = "checkLikeEmployees",
            query = "SELECT COUNT(le) FROM Like_employees AS le WHERE le.report = :report AND le.employee = :employee"
            )
})
@Entity
public class Like_employees {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    //ログインしている従業員の情報をオブジェクトのまま employee フィールドに格納

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;


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
