package controllers;

import models.User;
import play.libs.Json;
import play.mvc.*;
import service.UserService;
import service.ZomatoService;

public class Application extends Controller {

    public static Result index() {
        return ok(
                "success"
        );
    }

    public static Result getLocality() throws Exception {
        return ok(Json.toJson(ZomatoService.getLocality("28.557706", "77.205879")));
    }

    public static Result login(String mobile, String latitude, String longitude) {
        User user = new UserService().login(mobile, latitude, longitude);
        if (null == user) {
            return ok("not registered");
        }
        return ok(Json.toJson(user));
    }

    public static Result register(String name, String mobile, String latitude, String longitude) {
        return ok(Json.toJson(new UserService().register(name, mobile, latitude, longitude)));
    }
}
