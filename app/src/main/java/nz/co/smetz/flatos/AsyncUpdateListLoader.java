package nz.co.smetz.flatos;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sara on 11/3/2015.
 */
public class AsyncUpdateListLoader extends AsyncTaskLoader<List<Update>> {
        List<Update> mUpdates;
        FlatOSUtils utils = FlatOSUtils.INSTANCE;
        Context mContext;
        private static final String TAG = "AsyncUpdateLoader";

        public AsyncUpdateListLoader(Context c){
            super(c);
            mContext = c;
            Log.d(TAG, "constructor");
        }

        @Override
        public List<Update> loadInBackground() {
            Log.d(TAG, "loadInBg");
            List<Update> result = new ArrayList<Update>();
            String token = utils.getToken(mContext);

            try {
                URL u = new URL(utils.getUpdate_url());

                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization","Token "+token);
                conn.setRequestProperty("Www-Authenticate","Token "+token);

                conn.connect();
                InputStream is = conn.getInputStream();

                // Read the stream
                byte[] b = new byte[1024];
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                while ( is.read(b) != -1)
                    outputStream.write(b);

                String JSONResp = new String(outputStream.toByteArray());
                Log.d(TAG, "JSONResponse:"+JSONResp);

                JSONArray arr = new JSONArray(JSONResp);
                for (int i=0; i < arr.length(); i++) {
                    result.add(convertUpdate(arr.getJSONObject(i)));
                }

                return result;
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            return null;
        }

        private Update convertUpdate(JSONObject obj) throws JSONException {
            String id = obj.getString("id");
            String actor = obj.getString("actor");
            String type = obj.getString("type");
            String message = obj.getString("message");
            String time = obj.getString("time");

            return new Update(id, actor, type, message, time);
        }

    /**
     * Called when there is new data to send to the asset list view
     * (The super class will take care of delivering this data)
     */
    @Override public void deliverResult(List<Update> listOfUpdates) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (listOfUpdates != null) {
                onReleaseResources(listOfUpdates);
            }
        }
        List<Update> oldUpdates = listOfUpdates;
        mUpdates = listOfUpdates;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(listOfUpdates);
        }

        // At this point we can release the resources associated with
        // 'oldUpdates' if needed; now that the new result has been delivered
        if (oldUpdates != null) {
            onReleaseResources(oldUpdates);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override protected void onStartLoading() {
        if (mUpdates != null) {
            // If we currently have a result available - deliver it
            deliverResult(mUpdates);
        }

        if (takeContentChanged() || mUpdates == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(List<Update> updates) {
        super.onCanceled(updates);
        // Release assets associated with the cancelled load
        onReleaseResources(updates);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Make sure loading is not happening
        onStopLoading();

        // release the resources associated with the list of assets if needed
        if (mUpdates != null) {
            onReleaseResources(mUpdates);
            mUpdates = null;
        }
    }

    /**
     * Helper function to release resources associated with the loaded asset list
     * @param updates       The list of loaded assets to release
     */
    protected void onReleaseResources(List<Update> updates) {}
}
