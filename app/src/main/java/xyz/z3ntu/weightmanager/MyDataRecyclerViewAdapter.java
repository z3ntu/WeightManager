package xyz.z3ntu.weightmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.z3ntu.weightmanager.DataFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DateWeightEntry} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDataRecyclerViewAdapter extends RecyclerView.Adapter<MyDataRecyclerViewAdapter.ViewHolder> {

    private final List<DateWeightEntry> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyDataRecyclerViewAdapter(List<DateWeightEntry> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public boolean addItem(DateWeightEntry entry) {
        return mValues.add(entry);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDateView.setText(mValues.get(position).getDate());
        holder.mWeightView.setText(String.valueOf(mValues.get(position).getWeight()));

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDateView;
        public final TextView mWeightView;
        public DateWeightEntry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = (TextView) view.findViewById(R.id.date);
            mWeightView = (TextView) view.findViewById(R.id.weight);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mWeightView.getText() + "'";
        }
    }
}
