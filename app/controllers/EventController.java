package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Event;
import play.libs.Akka;
import play.libs.F;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import response.EventCreationResponse;
import scala.concurrent.ExecutionContext;
import service.EventService;
import service.ZomatoService;

import javax.xml.ws.ResponseWrapper;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vijay on 18/7/15.
 */
public class EventController extends Controller {

    private static Integer SUGGESTION_COUNT = 20;

    public static Result index() {
        return ok(
                "success"
        );
    }

    public static Result createEvent(String name, String people, String latitude, String longitude) {
        EventService eventService = new EventService();
        List<String> mobileNos = Arrays.asList(people.split(","));
        EventCreationResponse eventCreationResponse = eventService.createEvent(name, mobileNos, latitude, longitude);
        return ok(Json.toJson(eventCreationResponse));
    }

    public static Result updateStatus(Long uid, Long eid, String latitude, String longitude, Boolean accept) {
        EventService eventService = new EventService();
        eventService.updateEventStatus(uid, eid, latitude, longitude, accept);
        return ok();
    }

    public static Result getEvent(Long id) {
        EventService eventService = new EventService();
        return ok(Json.toJson(eventService.getEventDetails(id)));
    }

    public static F.Promise<Result> getEventSuggestions(final Long id) throws Exception {
        final ExecutionContext elasticExecutionContext = Akka.system().dispatchers()
                .lookup("play.akka.actor.contexts.elastic-context");
        F.Promise<Result> response = F.Promise.promise(new F.Function0<Result>() {
            public Result apply() throws Exception {
                EventService eventService = new EventService();
                Event event = eventService.findEvent(id);
                ZomatoService zomatoService = new ZomatoService();
                return ok(Json.toJson(zomatoService.getSuggestions(event.getLatitude(), event.getLongitude(), SUGGESTION_COUNT)));
            }
        }, elasticExecutionContext);
        return response;
    }
}