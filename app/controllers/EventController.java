package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import response.EventCreationResponse;
import service.EventService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vijay on 18/7/15.
 */
public class EventController extends Controller {

    public static Result index() {
        return ok(
                "success"
        );
    }

    public static Result createEvent( String name, String people, String latitude, String longitude){
        EventService eventService = new EventService();
        List<String> mobileNos = Arrays.asList(people.split(","));
        EventCreationResponse eventCreationResponse = eventService.createEvent(name,mobileNos, latitude, longitude );
        return ok(Json.toJson(eventCreationResponse));
    }

    public static Result updateStatus( Long uid, Long eid, String latitude, String longitude, Boolean accept){
        EventService eventService = new EventService();
        eventService.updateEventStatus(uid,eid, latitude, longitude,accept );
        return ok();
    }

}
