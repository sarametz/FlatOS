package nz.co.smetz.flatos;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * Created by Sara on 12/3/2015.
 */
public class PrefFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String TAG = "PrefFragment";

    public static PrefFragment newInstance() {
        PrefFragment fragment = new PrefFragment();
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
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        Log.d(TAG, "onSHaredPRefsChanged:" + key);
        if (key.equals(getString(R.string.reg_id_key))) {
            Preference gcmIdPref = findPreference(getString(R.string.reg_id_key));
            // Set summary to be the user-description for the selected value
            gcmIdPref.setSummary(sharedPreferences.getString(key, ""));
            Log.d(TAG,"gcmIdPref:"+gcmIdPref.getSummary());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).setTitle(getString(R.string.title_settings));
    }
}
