package service;

import com.avaje.ebean.EbeanServer;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
        List<String> alreadyJoined = new ArrayList();
        List<Status> newMembers = new ArrayList<Status>();
        for (int i = 0; i < mobiles.size(); i++) {
            User user = userService.getUser(mobiles.get(i));
            if (i == 0) {
                newMembers.add(new Status(event.getId(), user.getId(), Status.Type.accepted, latitude, longitude));
            } else {
                if (null == user)
                    notRegistered.add(mobiles.get(i));
                else {
                    newMembers.add(new Status(event.getId(), user.getId(), Status.Type.pending, null, null));
                }
            }
        }
        database.save(newMembers);
        return new EventCreationResponse(event.getId(), notRegistered, alreadyJoined);
    }

    public EventCreationResponse inviteToEvent(Long id, List<String> mobiles) {
        Event event = findEvent(id);
        //TodO: add exception
        List<String> notRegistered = new ArrayList();
        List<String> alreadyJoined = new ArrayList();

        for (int i = 0; i < mobiles.size(); i++) {
            User user = userService.getUser(mobiles.get(i));
            if (null == user)
                notRegistered.add(mobiles.get(i));
            else {
                database.save(new Status(event.getId(), user.getId(), Status.Type.pending, null, null));
            }
        }
        return new EventCreationResponse(event.getId(), notRegistered, alreadyJoined);
    }

    public void updateEventStatus(Long userId, Long eventId, String latitude, String longitude, Boolean accept) {
        database.beginTransaction();
        try {
            Event event = findEvent(eventId);
            if (null == event) {
                //TodO: add exception
            }

            int acceptedMemberCount = database.find(Status.class).where().
                    eq("eid", event.getId()).
                    eq("status", 1).findRowCount();

            Status status = database.find(Status.class).where().eq("uid", userId).eq("eid", eventId).findUnique();

            Status.Type oldStatus = null;
            Float oldStatusLatitude = null, oldStatusLongitude = null,
                    newEventLongitude = 0F, newEventLatitude = 0F;


            if (null == status) {
                status = new Status(eventId, userId, Status.Type.accepted, latitude, longitude);
            } else {
                oldStatus = status.getStatus();
                oldStatusLatitude = null == status.getLatitude() ? null : Float.parseFloat(status.getLatitude());
                oldStatusLongitude = null == status.getLongitude() ? null : Float.parseFloat(status.getLongitude());
                status.setLatitude(latitude);
                status.setLongitude(longitude);
            }

            if (accept) {
                status.setStatus(Status.Type.accepted);
                if (null != oldStatus && oldStatus.compareTo(Status.Type.accepted) == 0) {
                    newEventLatitude = (Float.parseFloat(event.getLatitude()) * acceptedMemberCount - oldStatusLatitude + Float.parseFloat(latitude)) / acceptedMemberCount;
                    newEventLongitude = (Float.parseFloat(event.getLongitude()) * acceptedMemberCount - oldStatusLongitude + Float.parseFloat(longitude)) / acceptedMemberCount;
                } else {
                    newEventLatitude = (Float.parseFloat(event.getLatitude()) * acceptedMemberCount + Float.parseFloat(latitude)) / (acceptedMemberCount + 1);
                    newEventLongitude = (Float.parseFloat(event.getLongitude()) * acceptedMemberCount + Float.parseFloat(longitude)) / (acceptedMemberCount + 1);
                }

            } else {
                if (null != oldStatus && oldStatus.compareTo(Status.Type.accepted) == 0) {
                    newEventLatitude = (Float.parseFloat(event.getLatitude()) * acceptedMemberCount - oldStatusLatitude) / (acceptedMemberCount - 1);
                    newEventLongitude = (Float.parseFloat(event.getLongitude()) * acceptedMemberCount - oldStatusLongitude) / (acceptedMemberCount - 1);
                }
                status.setStatus(Status.Type.rejected);
            }

            event.setLatitude(newEventLatitude.toString());
            event.setLongitude(newEventLongitude.toString());
            if (null != oldStatus) {
                database.update(status);
            } else {
                database.save(status);
            }
            database.update(event);
            database.commitTransaction();
        } finally {
            database.endTransaction();
        }
    }

    private Event findEvent(Long id) {
        return database.find(Event.class, id);
    }

//    private void updateEventLocation(Long userId, Event event, String latitude, String longitude, Boolean accept) {
//        Float newLongitude = 0F, newLatitude = 0F,
//                curLongitude = Float.parseFloat(event.getLongitude()),
//                curLatitude = Float.parseFloat(event.getLatitude());
//
//        Status status = database.find(Status.class).where().
//                eq("eid", event.getId()).
//                eq("status", 1).
//                eq("uid", userId).findUnique();
//
//        if (accept) {
//            if (null != status.getLatitude())
//        }
//        event.setLatitude(centroidX.toString());
//        event.setLongitude(centroidY.toString());
//        database.update(event);
//    }
}
