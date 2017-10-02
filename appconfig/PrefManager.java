package com.firminapp.smsanonyme.appconfig;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.HashMap;

@SuppressWarnings("unused")
public  class PrefManager {
    private static PrefManager sessionManager;

    public static final String KEY_VERIF_CODE = "ccom.firminapp.smsanonyme.VERIF_CODE";

    public static final String KEY_PHONE_NUMBER = "ccom.firminapp.smsanonyme.KEY_PHONE_NUMBER";
    public static final String KEY_IS_NEW = "ccom.firminapp.smsanonyme.KEY_IS_NEW";
    public static final String KEy_USER_PSEUDO = "ccom.firminapp.smsanonyme.USER_PSEUDO";
    public static final String KEY_TOKEN = "ccom.firminapp.smsanonyme.KEY_TOKEN";
    public static final String KEY_TOTAL_SMS = "ccom.firminapp.smsanonyme.TOTALSMS";
    public static final String KEY_SENDED_SMS = "ccom.firminapp.smsanonyme.SENDED_SMS";

    //paramlettre pour l'api parametters values
    public static final String KEY_API_USER = "ccom.firminapp.smsanonyme.API_USER";
    public static final String KEY_API_PASS = "ccom.firminapp.smsanonyme.API_PASS";
    public static final String KEY_API_TOKEN = "ccom.firminapp.smsanonyme.API_TOKEN";
    public static final String API_FROM = "ccom.firminapp.smsanonyme.API_FROM";
    public static final String API_TO = "ccom.firminapp.smsanonyme.API_TO";
    public static final String API_BODY = "ccom.firminapp.smsanonyme.API_BODY";
    public static final String API_APP = "ccom.firminapp.smsanonyme.API_APP";
    public static final String API_OP = "ccom.firminapp.smsanonyme.API_OP";
   // public static final String KEY_SENDED_SMS = "ccom.firminapp.smsanonyme.SENDED_SMS";
    //api parametters key
   public static final String USER_KEY = "ccom.firminapp.smsanonyme.API_USER_KEY";
    public static final String TOKEN_KEY = "ccom.firminapp.smsanonyme.API_TOKEN_KEY";
    public static final String PASS_KEY = "ccom.firminapp.smsanonyme.API_PASS_KEY";
    //api needed parametters to send sms
    public static final String FROM_KEY = "ccom.firminapp.smsanonyme.API_MSG_FROM";
    public static final String TO_KEY = "ccom.firminapp.smsanonyme.API_MSG_TO";
    public static final String BODY_KEY = "ccom.firminapp.smsanonyme.API_MSG_BODY";
    public static final String OP_KEY = "ccom.firminapp.smsanonyme.API_OP_KEY";
    public static final String APP_KEY = "ccom.firminapp.smsanonyme.API_APP_KEY";


    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    SmsApp smsapp;

    private PrefManager(Context context){
        this._context = context;
        smsapp = (SmsApp) context.getApplicationContext();
        pref = _context.getSharedPreferences("com.firminapp.smsanonyme", 0);
        editor = pref.edit();
        editor.apply();
        initSession("inconnu","Anonyme",0,0);
        //new Update_profile();
    }

    public static void initInstance(Context context) {
        if (sessionManager == null)
            sessionManager = new PrefManager(context);
    }

    public static PrefManager getInstance() {
        return sessionManager;
    }

    public void setRequiredSignInSingUpInfo(String phoneNumber, String pseudo){
        editor.putString(KEY_PHONE_NUMBER,phoneNumber);
        editor.putString(KEy_USER_PSEUDO, pseudo);
        editor.commit();
    }

    public HashMap<String, String> getRequiredSignInSingUpInfo(){
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_PHONE_NUMBER, pref.getString(KEY_PHONE_NUMBER, null));
        user.put(KEy_USER_PSEUDO, pref.getString(KEy_USER_PSEUDO, null));
        user.put(KEY_TOTAL_SMS, pref.getString(KEY_TOTAL_SMS, null));
        user.put(KEY_SENDED_SMS, pref.getString(KEY_SENDED_SMS, null));

        return user;
    }

    
    public void setIsNew(boolean isNew) {
        editor.putBoolean(KEY_IS_NEW, isNew);
        editor.commit();
    }

    public boolean isNew() {
        return pref.getBoolean(KEY_IS_NEW, true);
    }


    public void initSession(String tel,String pseudo,int totalsms,int sendedsms) {
        setPseudo(pseudo);
        setTelephone(tel);
        setTotalsms(totalsms);
        setSendedsms(sendedsms);
    }
    public void clearSession() {
        editor.clear();
        editor.commit();
    }

    public void setPseudo(String pseudo){
        editor.putString(KEy_USER_PSEUDO,pseudo);
        editor.commit();
    }
   
    public void setTelephone(String tel){
        editor.putString(KEY_PHONE_NUMBER,tel);
        editor.commit();
    }
    
    public void setTotalsms(int totalsms){
        editor.putInt(KEY_TOTAL_SMS,totalsms);
        editor.commit();
    }
    public void setSendedsms(int sendedsms){
        editor.putInt(KEY_SENDED_SMS,sendedsms);
        editor.commit();
    }
    public void setToken(String token){
        editor.putString(KEY_TOKEN,token);
        editor.commit();
    }
    public void setApiUser(String user){
        editor.putString(KEY_API_USER,user);
        editor.commit();
    }
    public void setApiPass(String pass){
        editor.putString(KEY_API_PASS,pass);
        editor.commit();
    }
    public void setApiToken(String apitoken){
        editor.putString(KEY_API_TOKEN,apitoken);
        editor.commit();
    }
    public void setApifrom(String from){
        editor.putString(API_FROM,from);
        editor.commit();
    }
    public void setApito(String to){
        editor.putString(API_TO,to);
        editor.commit();
    }
    public void setApibody(String body){
        editor.putString(API_BODY,body);
        editor.commit();
    }

    public void setApiuserkey(String userkey){
        editor.putString(USER_KEY,userkey);
        editor.commit();
    }
    public void setApipasskey(String passkey){
        editor.putString(PASS_KEY,passkey);
        editor.commit();
    }
    public void setApitokenkey(String tokenkey){
        editor.putString(TOKEN_KEY,tokenkey);
        editor.commit();
    }
    public void setApifromkey(String fromkey){
        editor.putString(FROM_KEY,fromkey);
        editor.commit();
    }
    public void setApitokey(String tokey){
        editor.putString(TO_KEY,tokey);
        editor.commit();
    }
    public void setApibodykey(String bodykey){
        editor.putString(BODY_KEY,bodykey);
        editor.commit();
    }

    public void setApiApp(String app){
        editor.putString(API_APP,app);
        editor.commit();
    }
    public void setApiOp(String op){
        editor.putString(API_OP,op);
        editor.commit();
    }
    public void setApiAppKey(String appkey){
        editor.putString(APP_KEY,appkey);
        editor.commit();
    }
    public void setApiOpKey(String opkey){
        editor.putString(OP_KEY,opkey);
        editor.commit();
    }
    public void setVerifCode(String code){
        editor.putString(KEY_VERIF_CODE,code);
        editor.commit();
    }
    public String getPseudo(){
        return pref.getString(KEy_USER_PSEUDO,null);
    }
    public String getTelephone(){
        return pref.getString(KEY_PHONE_NUMBER,null);
    }
    public int getKeySendedSms(){
        return pref.getInt(KEY_SENDED_SMS,0);
    }

    public int getTotalsms(){
        return pref.getInt(KEY_TOTAL_SMS,0);
    }
    public String getToken(){
        return pref.getString(KEY_TOKEN,"0");
    }

    public String getApiUser(){
        return pref.getString(KEY_API_USER,null);
    }
    public String getApiPass(){
        return pref.getString(KEY_API_PASS,null);
    }
    public String getApiToken(){
        return pref.getString(KEY_API_TOKEN,null);
    }

    public String getApiuserkey(){
        return pref.getString(USER_KEY,"");
    }
    public String getApipasskey(){
        return pref.getString(PASS_KEY,"");
    }

    public String getApitokenkey(){
        return pref.getString(TOKEN_KEY,null);
    }
    public String getApifromkey(){
        return pref.getString(FROM_KEY,null);
    }
    public String getApitokey(){
        return pref.getString(TO_KEY,null);
    }
    public String getApibodykey(){
        return pref.getString(BODY_KEY,null);
    }
    public String getApiappkey(){
        return pref.getString(APP_KEY,null);
    }
    public String getApiopkey(){
        return pref.getString(OP_KEY,null);
    }
    public String getApiapp(){
        return pref.getString(API_APP,null);
    }
    public String getApiop(){
        return pref.getString(API_OP,null);
    }
    public String getVerifCode(){
        return pref.getString(KEY_VERIF_CODE,null);
    }
}