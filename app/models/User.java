package models;

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
@Table(name = "user")
public class User extends Model {

    @Id
    @Column(name = "id")
    @Getter
    private Long id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "last_longitude")
    @Getter
    @Setter
    private String longitude;

    @Column(name = "last_latitude")
    @Getter
    @Setter
    private String latitude;


    @Column(name = "mobile")
    @Getter
    @Setter
    private String mobile;

}
