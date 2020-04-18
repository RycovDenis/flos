package kz.dev.home.flos.datamodels;

public class Message {
    private int uid;
    private String message;
    private String sentAtid;
    private String name;

    public Message(int uid, String message, String sentAtid, String name) {
        this.uid = uid;
        this.message = message;
        this.sentAtid = sentAtid;
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public String getMessage() {
        return message;
    }

    public String getSentAtid() {
        return sentAtid;
    }

    public String getName() {
        return name;
    }
}
