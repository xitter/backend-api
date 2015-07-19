package service;

import bean.Member;
import com.avaje.ebean.EbeanServer;
import models.Event;
import models.EventMember;
import models.User;
import response.EventCreationResponse;
import response.EventResponse;

import java.util.*;

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
        List<EventMember> newMembers = new ArrayList<EventMember>();
        for (int i = 0; i < mobiles.size(); i++) {
            User user = userService.getUser(mobiles.get(i));
            if (i == 0) {
                newMembers.add(new EventMember(event.getId(), user.getId(), EventMember.Status.accepted, latitude, longitude));
            } else {
                if (null == user)
                    notRegistered.add(mobiles.get(i));
                else {
                    newMembers.add(new EventMember(event.getId(), user.getId(), EventMember.Status.pending, null, null));
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
                database.save(new EventMember(event.getId(), user.getId(), EventMember.Status.pending, null, null));
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

            int acceptedMemberCount = database.find(EventMember.class).where().
                    eq("eid", event.getId()).
                    eq("status", 1).findRowCount();

            EventMember member = database.find(EventMember.class).where().eq("uid", userId).eq("eid", eventId).findUnique();

            EventMember.Status oldStatus = null;
            Float oldStatusLatitude = null, oldStatusLongitude = null,
                    newEventLongitude = 0F, newEventLatitude = 0F;


            if (null == member) {
                member = new EventMember(eventId, userId, null, null, null);
            } else {
                oldStatus = member.getStatus();
                oldStatusLatitude = null == member.getLatitude() ? null : Float.parseFloat(member.getLatitude());
                oldStatusLongitude = null == member.getLongitude() ? null : Float.parseFloat(member.getLongitude());
            }
            member.setLatitude(latitude);
            member.setLongitude(longitude);
            if (accept) {
                member.setStatus(EventMember.Status.accepted);
                if (null != oldStatus && oldStatus.compareTo(EventMember.Status.accepted) == 0) {
                    newEventLatitude = (Float.parseFloat(event.getLatitude()) * acceptedMemberCount - oldStatusLatitude + Float.parseFloat(latitude)) / acceptedMemberCount;
                    newEventLongitude = (Float.parseFloat(event.getLongitude()) * acceptedMemberCount - oldStatusLongitude + Float.parseFloat(longitude)) / acceptedMemberCount;
                } else {
                    newEventLatitude = (Float.parseFloat(event.getLatitude()) * acceptedMemberCount + Float.parseFloat(latitude)) / (acceptedMemberCount + 1);
                    newEventLongitude = (Float.parseFloat(event.getLongitude()) * acceptedMemberCount + Float.parseFloat(longitude)) / (acceptedMemberCount + 1);
                }

            } else {
                if (null != oldStatus && oldStatus.compareTo(EventMember.Status.accepted) == 0) {
                    newEventLatitude = (Float.parseFloat(event.getLatitude()) * acceptedMemberCount - oldStatusLatitude) / (acceptedMemberCount - 1);
                    newEventLongitude = (Float.parseFloat(event.getLongitude()) * acceptedMemberCount - oldStatusLongitude) / (acceptedMemberCount - 1);
                }
                member.setStatus(EventMember.Status.rejected);
            }

            event.setLatitude(newEventLatitude.toString());
            event.setLongitude(newEventLongitude.toString());
            if (null != oldStatus) {
                database.update(member);
            } else {
                database.save(member);
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

    public EventResponse getEventDetails(Long id) {

        Event event = findEvent(id);

        List<EventMember> eventMembers = database.find(EventMember.class)
                .where().eq("eid", event.getId())
                .findList();

        Set<Long> memberIds = new HashSet<Long>();
        Map<Long, EventMember.Status> memberStatus = new HashMap<Long, EventMember.Status>();
        for (EventMember eventMember : eventMembers) {
            memberIds.add(eventMember.getUserId());
            memberStatus.put(eventMember.getUserId(), eventMember.getStatus());
        }

        UserService userService = new UserService();
        List<User> users = userService.getUsers(memberIds);

        List<Member> members = new ArrayList<Member>();
        for (User user : users) {
            members.add(
                    new Member(user.getId(),
                            user.getName(),
                            user.getMobile(),
                            memberStatus.get(user.getId()),
                            user.getLatitude(),
                            user.getLongitude()));
        }

        return new EventResponse(event.getId(), event.getName(), members);
    }

}
