package service;

import com.avaje.ebean.EbeanServer;
import models.Event;
import models.Status;
import models.User;
import response.EventCreationResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vijay on 18/7/15.
 */
public class EventService {

    private EbeanServer database;

    private UserService userService;

    public EventService() {
        database = DataProvider.getServer(DataProvider.Server.MEET_UP);
        userService = new UserService();
    }

    public EventCreationResponse createEvent(String name, List<String> mobiles, String latitude, String longitude) {
        Event event = new Event();
        event.setName(name);
        event.setDate(new Date());
        event.setLatitude(latitude);
        event.setLongitude(longitude);
        database.save(event);

        List<String> notRegistered = new ArrayList();

        for (int i = 0; i < mobiles.size(); i++) {
            User user = userService.getUser(mobiles.get(i));
            if (i == 0) {
                database.save(new Status(event.getId(), user.getId(), Status.Type.accepted, latitude, longitude));
            } else {
                if (null == user)
                    notRegistered.add(mobiles.get(i));
                else {
                    database.save(new Status(event.getId(), user.getId(), Status.Type.pending, null, null));
                }
            }
        }
        return new EventCreationResponse(event.getId(), notRegistered);
    }

    public EventCreationResponse inviteToEvent(Long id, List<String> mobiles) {
        Event event = findEvent(id);
        //TodO: add exception
        List<String> notRegistered = new ArrayList();

        for (int i = 0; i < mobiles.size(); i++) {
            User user = userService.getUser(mobiles.get(i));
            if (null == user)
                notRegistered.add(mobiles.get(i));
            else {
                database.save(new Status(event.getId(), user.getId(), Status.Type.pending, null, null));
            }
        }
        return new EventCreationResponse(event.getId(), notRegistered);
    }

    public void updateEventStatus(Long userId, Long eventId, String latitude, String longitude, Boolean accept) {

        //Event event = findEvent(eventId);
//        if (null == event) {
//            //TodO: add exception
//        }

        Status status = database.find(Status.class).where().eq("uid", userId).eq("eid", eventId).findUnique();

        if (null == status) {
            status = new Status(eventId, userId, Status.Type.accepted, latitude, longitude);
        }

        if (accept) {
            status.setStatus(Status.Type.accepted);
        } else {
            status.setStatus(Status.Type.rejected);
        }

        database.update(status);
        updateEventLocation(eventId);
    }

    private Event findEvent(Long id) {
        return database.find(Event.class, id);
    }

    private void updateEventLocation(Long eventId) {
        Float centroidX = 0F, centroidY = 0F;
        List<Status> statusList = database.find(Status.class).where().eq("eid", eventId).eq("status", 1).findList();
        for (Status status : statusList) {
            centroidX += Float.parseFloat(status.getLatitude());
            centroidY += Float.parseFloat(status.getLongitude());
        }
        Event event = database.find(Event.class, eventId);
        event.setLatitude(centroidX.toString());
        event.setLongitude(centroidY.toString());
        database.update(event);
    }
}
