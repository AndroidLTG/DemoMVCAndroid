package CommonLib;

/**
 * Created by My PC on 26/11/2015.
 */
public class EventBase {
    public static enum Type {
        BEGIN,
        TEXT,
        CHECK,
        STRING,
        BOOLEAN,
        END};
    public Type type;
    public EventBase(Type type) {
        this.type = type;
    }
}

