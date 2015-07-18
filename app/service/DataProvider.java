package service;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import lombok.Getter;

/**
 * @author aritra
 */
public class DataProvider {

    /**
     * @param server
     * @return
     */
    public static EbeanServer getServer(Server server) {
        return Ebean.getServer(server.getId());
    }

    public enum Server {
        MEET_UP("meetup");

        @Getter
        private final String id;

        Server(String id) {
            this.id = id;
        }

    }

}
