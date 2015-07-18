package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

/**
 * Created by vijay on 19/5/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookUser {

    @Getter
    @JsonProperty("id")
    private String id;

    @Getter
    @JsonProperty("email")
    private String email;

    @Getter
    @JsonProperty("first_name")
    private String firstName;

    @Getter
    @JsonProperty("last_name")
    private String lastName;

    @Getter
    @JsonProperty("name")
    private String name;

    @Getter
    @JsonProperty("gender")
    private String gender;

    @Getter
    @JsonProperty("verified")
    private String verified;

    @Getter
    @JsonProperty("location")
    private String location;

    @Getter
    @JsonProperty("hometown")
    private String hometown;

    @Getter
    @JsonProperty("picture")
    private Map<String,Map<String,String>> picture;

}

