package nz.co.smetz.flatos;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class UpdateFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Update>>{

    private static final String TAG = "UpdateFragment";
    private UpdateArrayAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private RecyclerView mUpdateview;
    private LinearLayoutManager mLayoutManager;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_update_list,
                container, false);

        //set the adapter to load the gridview data
        mUpdateview = (RecyclerView) rootView.findViewById(R.id.updateview);
        mAdapter = new UpdateArrayAdapter(getActivity());
        mUpdateview.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mUpdateview.setLayoutManager(mLayoutManager);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");

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
