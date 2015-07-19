package response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Created by vijay on 18/7/15.
 */
@AllArgsConstructor
public class EventCreationResponse {

    @Getter
    private Long id;

    @Getter
    List<String> notRegistered;

    @Getter
    List<String> alreadyJoined;
}
