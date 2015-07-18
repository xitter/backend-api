package bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by vijay on 18/7/15.
 */
@AllArgsConstructor
public class Location {

    @Getter
    private final Double latitude;

    @Getter
    private final Double longitude;

}
