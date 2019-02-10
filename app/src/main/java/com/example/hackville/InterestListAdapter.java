package com.example.hackville;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class InterestListAdapter extends  RecyclerView.Adapter<InterestListAdapter.InterestViewHolder>  {

    private LinkedList<String> mInterestList;
    private LayoutInflater mInflater;


    // Contructor
    InterestListAdapter(Context context, LinkedList<String> interestList) {
        mInflater = LayoutInflater.from(context);
        this.mInterestList = interestList;
    }

    class InterestViewHolder extends RecyclerView.ViewHolder {

        private final TextView interestItemView;
        final InterestListAdapter mAdapter;

        private InterestViewHolder(View itemView, InterestListAdapter adapter) {
            super(itemView);
            interestItemView = itemView.findViewById(R.id._interest);
            this.mAdapter = adapter;

        }
    }


    @NonNull
    @Override
    public InterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.interests_item, parent, false);
        return new InterestViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestViewHolder holder, int position) {
        String mCurrent = mInterestList.get(position);
        holder.interestItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mInterestList.size();
    }
}
