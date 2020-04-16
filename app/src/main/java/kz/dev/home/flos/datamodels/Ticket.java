package kz.dev.home.flos.datamodels;

//public class Ticket {
//    public Integer userId, tiId, ownerId, priority, status, tiAccept;
//    public String text, title, tiDate, tiEmail, tiPhone;
//}

public class Ticket {

    private Integer tiId;
    private Integer priority;
    private Integer status;
    private Integer tiAccept;
    private String text, title, tiDate, tiEmail, tiPhone;

//    public Ticket(String text, String title,String tiDate,String tiEmail,String tiPhone,
//                  Integer userId,Integer tiId,Integer ownerId,Integer priority,Integer status,Integer tiAccept) {
//
//        this.text = text;
//        this.title = title;
//        this.tiDate = tiDate;
//        this.tiEmail = tiEmail;
//        this.tiPhone = tiPhone;
//        this.userId = userId;
//        this.tiId = tiId;
//        this.ownerId = ownerId;
//        this.priority = priority;
//        this.status = status;
//        this.tiAccept = tiAccept;
//    }

    public Ticket() {

    }

    public void setUserId(Integer userId) {
    }

    public Integer getTiId() {
        return tiId;
    }

    public void setTiId(Integer tiId) {
        this.tiId = tiId;
    }

    public void setOwnerId(Integer ownerId) {
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTiDate() {
        return tiDate;
    }

    public void setTiDate(String tiDate) {
        this.tiDate = tiDate;
    }

    public String getTiEmail() {
        return tiEmail;
    }

    public void setTiEmail(String tiEmail) {
        this.tiEmail = tiEmail;
    }

    public String getTiPhone() {
        return tiPhone;
    }

    public void setTiPhone(String tiPhone) {
        this.tiPhone = tiPhone;
    }
}