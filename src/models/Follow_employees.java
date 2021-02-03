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

@Table(name = "follow_employees")
@NamedQueries({
    @NamedQuery(
            name = "getAllFollowing",
            query = "SELECT fe FROM Follow_employees AS fe WHERE fe.follower = :follower ORDER BY fe.created_at DESC"
            ),
    @NamedQuery(
        name = "getFollowingCount",
        query = "SELECT COUNT(fe) FROM Follow_employees AS fe WHERE fe.follower = :follower"
    ),
    @NamedQuery(
            name = "checkFollowEmployee",
            query = "SELECT COUNT(fe) FROM Follow_employees AS fe WHERE fe.follower = :follower AND fe.followee = :followee"
        ),
    @NamedQuery(
            name = "getAllFollowed",
            query = "SELECT fe FROM Follow_employees AS fe WHERE fe.followee = :followee ORDER BY fe.created_at DESC"
            ),
    @NamedQuery(
            name = "getAllFollowedCount",
            query = "SELECT COUNT(fe) FROM Follow_employees AS fe WHERE fe.followee = :followee"
    ),
    @NamedQuery(
            name = "checkFollowing",
            query = "SELECT COUNT(fe) FROM Follow_employees AS fe WHERE fe.follower = :follower"
        ),
})

@Entity
public class Follow_employees {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "follower", nullable = false)
    private Employee follower;

    @ManyToOne
    @JoinColumn(name = "followee", nullable = false)
    private Employee followee;

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

    public Employee getFollower() {
        return follower;
    }

    public void setFollower(Employee follower) {
        this.follower = follower;
    }

    public Employee getFollowee() {
        return followee;
    }

    public void setFollowee(Employee followee) {
        this.followee = followee;
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
