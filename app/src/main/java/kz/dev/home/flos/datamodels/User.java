package kz.dev.home.flos.datamodels;


/**
 * Created by Belal on 9/5/2017.
 */


//this is very simple class and it only contains the user attributes, a constructor and the getters
// you can easily do this by right click -> generate -> constructor and getters
public class User {

    private  String uid;
    private  String fname;
    private  String lname;
    private  String email;
    private  String roleID;
    private  String rolename;
    private  String uphone;

    public User( String uid,String fname,String lname,String email,String roleID,String rolename,String uphone) {
        this.uid = uid;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.roleID = roleID;
        this.rolename = rolename;
        this.uphone = uphone;
    }




    public String getUid() {
        return uid;
    }

    public  String getFname() {
        return fname;
    }

    public  String getLname() {
        return lname;
    }

    public  String getEmail() {
        return email;
    }

    public  String getRoleID() {
        return roleID;
    }

    public  String getRolename() {
        return rolename;
    }

    public  String getUphone() {
        return uphone;
    }
}
