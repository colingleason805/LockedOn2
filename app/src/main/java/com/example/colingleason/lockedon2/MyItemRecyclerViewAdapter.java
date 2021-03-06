package com.example.colingleason.lockedon2;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.colingleason.lockedon2.BlackListFragment.OnListFragmentInteractionListener;
import com.example.colingleason.lockedon2.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<ApplicationInfo> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;


    public MyItemRecyclerViewAdapter(List<ApplicationInfo> list, OnListFragmentInteractionListener listener, Context context) {
        mValues = list;
        mListener = listener;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blacklist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mAppName.setText(mValues.get(position).name);
        holder.mAppIcon.setImageDrawable(mValues.get(position).loadIcon(context.getPackageManager()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    //Link each of the views comprising a single BlackListItem to the ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mAppName;
        public final ImageView mAppIcon;
        public ApplicationInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAppName = (TextView) view.findViewById(R.id.app_name);
            mAppIcon = (ImageView) view.findViewById(R.id.app_icon);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAppName.getText() + "'";
        }
    }
}
