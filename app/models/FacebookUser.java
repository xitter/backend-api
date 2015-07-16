package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by vijay on 19/5/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class FacebookUser {

    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("verified")
    private String verified;

    @JsonProperty("location")
    private String location;

    @JsonProperty("hometown")
    private String hometown;

    @JsonProperty("picture")
    private Map<String,Map<String,String>> picture;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getVerified() {
        return verified;
    }

    public String getLocation() {
        return location;
    }

    public String getHometown() {
        return hometown;
    }

    public Map<String,Map<String,String>> getPicture() {
        return picture;
    }
}

