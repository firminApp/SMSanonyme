package com.firminapp.smsanonyme.appconfig;

/**
 * Created by firmin on 12/09/17.
 */

public  final class WebsServices {
    public static final String URL_ROOT="http://192.168.8.102/tplusbackend/web/app.php/";
    //public static final String APISMS="http://193.164.133.231/playsms/index.php";
    public static final String APISMS="http://74.207.224.67/api/http/sendmsg.php";
    public static final String SIGNUP=URL_ROOT+"users";
    public static final String URL_CODE_VERIFICATION=URL_ROOT+"codeverif";
    //public static final String INIT_API_PARAMS="http://192.168.8.100/tplusbackend/web/apiParams";
    public static final String INIT_API_PARAMS=URL_ROOT+"oceanique";
    //param key from remote json

    public static  final String JSON_KEY_API_USER="apiUser";
    public static  final String JSON_KEY_API_PASS="apiPass";
    public static  final String JSON_KEY_API_TOKEN="apiToken";
    public static  final String JSON_KEY_API_FROM="apiFrom";
    public static  final String JSON_KEY_API_TO="apiTo";
    public static  final String JSON_KEY_API_BODY="apiBody";
    public static  final String JSON_KEY_API_APP="apiApp";
    public static  final String JSON_KEY_API_OP="apiOp";

    public static  final String JSON_KEY_API_USER_KEY="apiUserKey";
    public static  final String JSON_KEY_API_PASS_KEY="apiPassKey";
    public static  final String JSON_KEY_API_TOKEN_KEY="apiTokenKey";
    public static  final String JSON_KEY_API_FROM_KEY="apiFromKey";
    public static  final String JSON_KEY_API_TO_KEY="apiToKey";
    public static  final String JSON_KEY_API_BODY_KEY="apiBodyKey";
    public static  final String JSON_KEY_API_APP_KEY="apiAppKey";
    public static  final String JSON_KEY_API_OP_KEY="apiOpKey";
}
