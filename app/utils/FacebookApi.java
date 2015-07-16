package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import models.FacebookUser;
import play.Configuration;

import java.io.IOException;

/**
 *
 */
public class FacebookApi {
    private final String MY_APP_ID;
    private final String MY_APP_SECRET;
    private final String REQUEST_FIELDS = "id, first_name, name, last_name, email, gender, verified, location, hometown, picture";
    private final Version REST_FB_VERSION = Version.VERSION_2_2;

    /**
     * [FacebookApi description]
     * @param
     * @return
     */
    public FacebookApi() {

        // Initializing the facebook app config
        Configuration config = Configuration.root();

        //need to put followings in config file
        MY_APP_ID = config.getString("fb.app.id");
        MY_APP_SECRET = config.getString("fb.app.secretKey");

    }
    /**
     * [getExtendedAccessToken description]
     * @param
     * @return
     */
    public AccessToken getExtendedAccessToken(String accessToken) {

        // Tells Facebook to extend the lifetime of MY_ACCESS_TOKEN.
        // Facebook may return the same token or a new one.
        AccessToken newAccessToken =
                new DefaultFacebookClient(REST_FB_VERSION).obtainExtendedAccessToken(MY_APP_ID,
                        MY_APP_SECRET, accessToken);

        return newAccessToken;
    }

    /**
     * [getUserDetails description]
     * @param
     * @return
     */
    public FacebookUser getUserDetails(String accessToken) throws IOException {

        // Get added security by using your app secret:
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, MY_APP_SECRET, REST_FB_VERSION);

        JsonObject jsonObject =  facebookClient.fetchObject("me", JsonObject.class, Parameter.with("fields", REQUEST_FIELDS) );

        ObjectMapper mapper = new ObjectMapper();
        FacebookUser facebookUser = mapper.readValue(jsonObject.toString(), FacebookUser.class);

        return facebookUser;
    }
}