package kz.dev.home.flos.services;

import kz.dev.home.flos.datamodels.URL_ch;

/**
 * Created by Belal on 9/5/2017.
 */

public class URLs {
//    public static final String HOST_PRIMAR ="http://16srb5pk.ddns.net/";
    public static final String HOST_SECOND ="http://pch38.tk/";
//    public static final String HOST_SECOND ="http://10.0.2.2/";


    private static final String ROOT_URL = HOST_SECOND;
//user management
    public static final String URL_LOGIN = ROOT_URL+"signin?";
    public static final String URL_REGISTER = ROOT_URL+"signup?" ;
    public static final String URL_CNFNU = ROOT_URL+"cfnewuser?";
    public static final String URL_VERIFYSMS = ROOT_URL+"verifysms?";
    public static final String URL_GETUINFO = ROOT_URL+"readuinfo?";
    public static final String URL_SETUINFO = ROOT_URL+"writeuinfo?";
//admin management
    public static final String URL_ADM_GETUINFO = ROOT_URL+"getadmuinfo?";
    public static final String URL_ADM_SETUINFO = ROOT_URL+"setadmuinfo?";
    public static final String URL_SEND_MULTIPLE_PUSH = ROOT_URL +"sendbums?";
//chat management
    public static final String URL_STORE_TOKEN  = ROOT_URL+"storegcmtoken?";
    public static final String URL_FETCH_MESSAGES  = ROOT_URL+"messages?";
    public static final String URL_SEND_MESSAGE  = ROOT_URL+"send?";
//tickets management
    public static final String URL_TICKETS= ROOT_URL+"tickets?";
    public static final String URL_NEW_TICKET = ROOT_URL+"newti?";
    public static final String URL_TICKETS_EXEC = ROOT_URL+"execti?";
    public static final String URL_TICKETS_RETR = ROOT_URL+"retrti?";
//tasks management
    public static final String URL_TASKS= ROOT_URL+"tickets?";
    public static final String URL_NEW_TASK = ROOT_URL+"newti?";
    public static final String URL_SET_TASK = ROOT_URL+"newti?";
//update app
    public static final String URL_GET_VERSION = ROOT_URL+"Version.txt";
    public static final String URL_GET_UPDATE = ROOT_URL+"newapk/";
}