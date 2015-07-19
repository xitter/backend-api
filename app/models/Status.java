package models;

import com.avaje.ebean.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by vijay on 18/7/15.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "event_status")
@AllArgsConstructor
public class Status extends Model {

    @Id
    @Column(name = "eid")
    @Getter
    private Long eventId;

    @Column(name = "uid")
    @Getter
    private Long userId;

    @Column(name = "status")
    @Getter
    @Setter
    private Type status;

    @Column(name = "latitude")
    @Getter
    @Setter
    private String latitude;

    @Column(name = "longitude")
    @Getter
    @Setter
    private String longitude;

    public enum Type{
            @EnumValue("1")
            accepted,
            @EnumValue("2")
            rejected,
            @EnumValue("3")
            pending
    };

}
