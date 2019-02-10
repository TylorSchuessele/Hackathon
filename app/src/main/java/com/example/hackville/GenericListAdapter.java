package com.example.hackville;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class GenericListAdapter extends  RecyclerView.Adapter<GenericListAdapter.InterestViewHolder>  {

    private LinkedList<String> mInterestList;
    private LayoutInflater mInflater;


    // Contructor
    public GenericListAdapter(Context context, LinkedList<String> interestList) {
        mInflater = LayoutInflater.from(context);
        this.mInterestList = interestList;
    }

    class InterestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public final TextView interestItemView;
        final GenericListAdapter mAdapter;



        public InterestViewHolder(View itemView, GenericListAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);
            interestItemView = itemView.findViewById(R.id._interest);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {



            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            String element = mInterestList.get(mPosition);


            // String resource array for topics
            String[] interestsArray = v.getResources().getStringArray(R.array.interests);

            // Checking if the current fragment is Choose topic
            if(mInterestList.get(0).equals(interestsArray[0])){
                // Change the word in the mWordList.
                mInterestList.set(mPosition, "Clicked! ");
                // Notify the adapter, that the data has changed so it can
                // update the RecyclerView to display the data.
                mAdapter.notifyDataSetChanged();
            }
            else{


            }



        }
    }

    @Override
    public InterestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.interests_item, parent, false);
        return new InterestViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(InterestViewHolder holder, int position) {
        String mCurrent = mInterestList.get(position);
        holder.interestItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mInterestList.size();
    }
}

