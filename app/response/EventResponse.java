package response;

import bean.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import models.User;

import java.util.List;

/**
 * Created by vijay on 19/7/15.
 */
@AllArgsConstructor
public class EventResponse {
    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private List<Member> members;
}
