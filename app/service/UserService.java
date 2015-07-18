package service;

import com.avaje.ebean.EbeanServer;
import models.User;

import javax.persistence.OptimisticLockException;

/**
 * Created by vijay on 18/7/15.
 */
public class UserService {

    private EbeanServer database;

    public UserService() {
        database = DataProvider.getServer(DataProvider.Server.MEET_UP);
    }

    public User getUser(String mobile) {
        User user = database.find(User.class).where().eq("mobile", mobile).findUnique();
        return user;
    }

    public User login(String mobile, String latitude, String longitude) {
        User user = getUser(mobile);
        if (null != user) {
            user.setLatitude(latitude);
            user.setLongitude(longitude);
        }
        return user;
    }

    public User register(String name, String mobile, String latitude, String longitude) {

        User user = getUser(mobile);
        if (null == user) {
            user = new User();
            user.setName(name);
            user.setMobile(mobile);
            user.setLatitude(latitude);
            user.setLongitude(longitude);
        }

        try {
            database.save(user);
        } catch (OptimisticLockException e) {
            database.refresh(user);
            database.save(user);
        }

        return user;
    }
}
