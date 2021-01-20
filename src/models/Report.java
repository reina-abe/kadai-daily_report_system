package models;

import java.sql.Date; //年月日のみ
import java.sql.Timestamp; //秒まで

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "reports")
@NamedQueries({
    @NamedQuery(  //すべての日報を取得
            name = "getAllReports",
            query = "SELECT r FROM Report AS r ORDER BY r.id DESC"
            ),
    @NamedQuery(  //日報の全件数を取得
            name = "getReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r"
            ),
    @NamedQuery(  //すべての自分の日報を取得
            name = "getMyAllReports",
            query = "SELECT r FROM Report AS r WHERE r.employee = :employee ORDER BY r.id DESC"
            ),
    @NamedQuery(  //自分の日報の全件数を取得
            name = "getMyReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee"
            ),
    @NamedQuery(  //フォローした人の日報を取得
            name = "getFollowReports",
            query = "SELECT r FROM Report AS r, Follow_employees AS f WHERE r.employee = f.followee AND f.follower = :login_employee ORDER BY r.id DESC"
            ),
    @NamedQuery(  //フォローした人の日報件数を取得
            name = "getFollowReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r, Follow_employees AS f WHERE r.employee = f.followee AND f.follower = :login_employee ORDER BY r.id DESC"
        ),
    @NamedQuery(  //前日未承認
            name = "getUnapprovedReports",
            query = "SELECT COUNT(r) FROM Report AS r, Approvals AS a WHERE r.approval = 0 AND r.employee.position < :login_employee AND a.created_at < :today ORDER BY r.id DESC"
        )
})
@Entity
public class Report {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    //ログインしている従業員の情報をオブジェクトのまま employee フィールドに格納

    @Column(name = "report_date", nullable = false) //いつのしごとの日報か
    private Date report_date;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob //改行もデータベースに保存される
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    // いいね数のプロパティを追加
    @Column(name = "like_count", nullable = false)
    private Integer like_count;

    @Column(name = "approval", nullable = false)
    private Integer approval;

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

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    //いいね数のゲッターセッターを追加
    public Integer getLike_count() {
        return like_count;
    }
    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }
}