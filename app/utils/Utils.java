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

        int locationCount = locations.size();

        if (locationCount == 1) {
            return locations.get(0);
        }

        double x = 0, y = 0, z = 0;

        for (Location location : locations) {

            double latitude = location.getLatitude() * Math.PI / 180;
            double longitude = location.getLongitude() * Math.PI / 180;

            x += Math.cos(latitude) * Math.cos(longitude);
            y += Math.cos(latitude) * Math.sin(longitude);
            z += Math.sin(latitude);

        }

        x /= locationCount;
        y /= locationCount;
        z /= locationCount;

        double centerLat = Math.atan2(y, x),
                centerSqrt = Math.sqrt(x * x + y * y),
                centerLong = Math.atan2(z, centerSqrt);

        return new Location(centerLat * 180 / Math.PI, centerLong * 180 / Math.PI);
    }

}
