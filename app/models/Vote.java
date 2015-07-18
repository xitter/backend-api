package models;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by vijay on 18/7/15.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "preference")
public class Vote {

    @Id
    @Column(name = "eid")
    @Getter
    private Long eventID;

    @Column(name = "uid")
    @Getter
    private Long userId;

    @Column(name = "zid")
    @Getter
    private Long zomatoId;

    @Column(name = "vote")
    @Getter
    private Integer vote;

}
