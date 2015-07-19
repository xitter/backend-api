package bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import models.EventMember;

/**
 * Created by vijay on 19/7/15.
 */
@AllArgsConstructor
public class Member {
    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private String mobile;

    @Getter
    private EventMember.Status status;

    @Getter
    private String latitude;

    @Getter
    private String longitude;
}
