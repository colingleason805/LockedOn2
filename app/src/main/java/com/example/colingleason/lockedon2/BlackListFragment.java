package com.example.colingleason.lockedon2;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.colingleason.lockedon2.dummy.DummyContent;
import com.example.colingleason.lockedon2.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;
import com.example.colingleason.lockedon2.BlackListItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BlackListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BlackListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BlackListFragment newInstance(int columnCount) {
        BlackListFragment fragment = new BlackListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blacklist_fragment, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            //choose which layout manager to use based on number of columns in list
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //set the adapter with a list of installed apps, a callback to the parent activity,
            //and the context.
            List<ApplicationInfo> list = getInstalledApps();
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(list, mListener, getActivity()));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //ensure that the parent activity has implemented the OnListFragmentInteractionListener
        //interface so that we can send data back to the parent activity
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Returns a re-formatted list of all installed apps
    public List<ApplicationInfo> getInstalledApps(){

        //get the installed applications
        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> applist = pm.getInstalledApplications
                (PackageManager.GET_META_DATA);

        //TODO:Also filter the given apps so that the list is only user-installed applications

        return applist;
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
    //TODO: Needs to return a list of chosen blacklisted apps to Main Activity
    public interface OnListFragmentInteractionListener {

        //TODO: fix to match the List of things I need, i think its package names
        //TODO: Change this implementation and BlackListFragment's interface to send/receive a list
        void onListFragmentInteraction(ApplicationInfo item);
    }
}
