package nz.co.smetz.flatos;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import nz.co.smetz.flatos.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class UpdateFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Update>>{

    private static final String TAG = "UpdateFragment";
    private UpdateArrayAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public static UpdateFragment newInstance() {
        Log.d(TAG, "new Instance");
        UpdateFragment fragment = new UpdateFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UpdateFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        setEmptyText("No updates");

        mAdapter = new UpdateArrayAdapter(getActivity());
        setListAdapter(mAdapter);
        // Start out with a progress indicator.
        setListShown(false);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    public UpdateArrayAdapter getAdapter(){
        return mAdapter;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "attach!");
        ((MainActivity) activity).setTitle(getString(R.string.title_update_list));
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.i("FragmentUpdateList", "Item clicked: " + id);
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(((Update)l.getItemAtPosition(position)).getId());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    // Loader manager
    /**
     * Creates a new loader to load assets from the database
     */
    @Override
    public Loader<List<Update>> onCreateLoader(int arg0, Bundle arg1) {
        return new AsyncUpdateListLoader(getActivity());
    }

    /**
     * Shows the retrieved assets once they have been loaded
     */
    @Override
    public void onLoadFinished(Loader<List<Update>> arg0, List<Update> data) {
        if (data != null) {
            mAdapter.setData(data);
            Log.d(TAG, "Data:" + data.toString());
            // The list should now be shown.
            if (isResumed()) {
                setListShown(true);
            } else {
                setListShownNoAnimation(true);
            }
        }
    }
    /**
     * Resets data shown when the update loader is reset
     */
    @Override
    public void onLoaderReset(Loader<List<Update>> arg0) {
        mAdapter.setData(null);
    }

    private void reloadList() {
        getLoaderManager().restartLoader(0, null, this);
    }
}
