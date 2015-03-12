package nz.co.smetz.flatos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Sara on 12/3/2015.
 */
public enum FlatOSUtils {
    INSTANCE;
    private static final String base_url = "https://arcane-springs-6803.herokuapp.com";
    private static final String login_url = base_url + "/token/";
    private static final String update_url = base_url + "/updates/";
    private static final String device_url = base_url + "/devices/";
    private String token;
    private SharedPreferences prefs;

    public static String getBase_url() {
        return base_url;
    }

    public static String getLogin_url() {
        return login_url;
    }

    public static String getUpdate_url() {
        return update_url;
    }

    public static String getDevice_url() {
        return device_url;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    /**
     * Gets the stored authentication token from preferences
     * Starts a login activity if token not set
     * @return token string
     */
    public String getToken(Context c){
        if (token != null && !token.isEmpty()) { return token; }

        if (this.prefs == null){
            throw new NullPointerException("Util preferences not set");
        }

        token = this.prefs.getString("token", "");

        if(token.isEmpty()){
            Intent intent=new Intent(c, LoginActivity.class);
            c.startActivity(intent);
            ((Activity)c).finish();
            return null;
        }
        return token;
    }

}
