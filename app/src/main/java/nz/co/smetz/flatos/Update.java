package nz.co.smetz.flatos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sara on 9/3/2015.
 */
public class Update {
    public String id;
    public String actor;
    public String type;
    public String message;

    public String getId() {
        return id;
    }

    public String getActor() {
        return actor;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }

    public Date time;

    public Update(String id, String actor, String type, String message, String time) {
        this.id = id;
        this.actor = actor;
        this.type = type;
        this.message = message;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        try {
            this.time = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return message;
    }

}
