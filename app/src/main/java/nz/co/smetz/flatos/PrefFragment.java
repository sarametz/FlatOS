package nz.co.smetz.flatos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.RingtonePreference;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sara on 12/3/2015.
 */
public class PrefFragment extends PreferenceFragment {

    private static final String TAG = "PrefFragment";
    private static Context mContext;

    public static PrefFragment newInstance(Context c) {
        PrefFragment fragment = new PrefFragment();
        mContext = c;
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PrefFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        // populate the wifi networks
        ListPreference wifiPref = (ListPreference)findPreference("notification_limit_wifi");
        WifiManager wifiManager = (WifiManager)mContext.getSystemService(mContext.WIFI_SERVICE);
        List<WifiConfiguration> knownWifis = wifiManager.getConfiguredNetworks();
        CharSequence[] ssids = new CharSequence[knownWifis.size()+1];
        ssids[0] = "None";
        for (int i = 0; i < knownWifis.size(); i++){
            WifiConfiguration wc = knownWifis.get(i);
            //remove quotation marks
            ssids[i+1] = wc.SSID.substring(1,wc.SSID.length()-1);
        }
        wifiPref.setEntries(ssids);
        wifiPref.setEntryValues(ssids);
        Log.d(TAG,"wifiPref:"+ Arrays.toString(wifiPref.getEntries()));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).setTitle(getString(R.string.title_settings));
    }
}
