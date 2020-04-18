package kz.dev.home.flos.datamodels;

public class MyJwt {
    private static String parsedValueUid;
    private static String parsedValueFname;
    private static String parsedValueLname;
    private static String parsedValueEmail;
    private static String parsedValueRoleID;
    private static String parsedValueRoleName;
    private static String parsedValueUphone;

    public MyJwt(){

    }

    public static String getParsedValueUid() {
        return parsedValueUid;
    }

    public void setParsedValueUid(String parsedValueUid) {
        MyJwt.parsedValueUid = parsedValueUid;
    }

    public static String getParsedValueFname() {
        return parsedValueFname;
    }

    public void setParsedValueFname(String parsedValueFname) {
        MyJwt.parsedValueFname = parsedValueFname;
    }

    public static String getParsedValueLname() {
        return parsedValueLname;
    }

    public void setParsedValueLname(String parsedValueLname) {
        MyJwt.parsedValueLname = parsedValueLname;
    }

    public static String getParsedValueEmail() {
        return parsedValueEmail;
    }

    public void setParsedValueEmail(String parsedValueEmail) {
        MyJwt.parsedValueEmail = parsedValueEmail;
    }

    public static String getParsedValueRoleID() {
        return parsedValueRoleID;
    }

    public void setParsedValueRoleID(String parsedValueRoleID) {
        MyJwt.parsedValueRoleID = parsedValueRoleID;
    }

    public static String getParsedValueRoleName() {
        return parsedValueRoleName;
    }

    public void setParsedValueRoleName(String parsedValueRoleName) {
        MyJwt.parsedValueRoleName = parsedValueRoleName;
    }

    public static String getParsedValueUphone() {
        return parsedValueUphone;
    }

    public void setParsedValueUphone(String parsedValueUphone) {
        MyJwt.parsedValueUphone = parsedValueUphone;
    }
}
