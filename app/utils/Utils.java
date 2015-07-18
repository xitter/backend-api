package utils;

import bean.Location;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by vijay on 18/7/15.
 */
public class Utils {

    public static JsonNode getJson(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response);
    }

    public static Location centroid(List<Location> locations) {
        double centroidX = 0, centroidY = 0;

        for (Location knot : locations) {
            centroidX += knot.getLatitude();
            centroidY += knot.getLongitude();
        }
        return new Location(centroidX / locations.size(), centroidY / locations.size());
    }

}
