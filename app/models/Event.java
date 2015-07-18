package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by vijay on 18/7/15.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "event_info")
public class Event extends Model {

    @Id
    @Column(name = "id")
    @Getter
    private Long id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "date")
    @Getter
    @Setter
    private Date date;

    @Column(name = "center_latitude")
    @Getter
    @Setter
    private String latitude;

    @Column(name = "center_longitude")
    @Getter
    @Setter
    private String longitude;

}
