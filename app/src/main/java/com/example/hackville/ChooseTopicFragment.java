package com.example.hackville;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseTopicFragment extends Fragment {

    // RecyclerView of interests
    private RecyclerView recyclerView;

    // adapter for interests
    private GenericListAdapter mAdapter;

    // List of interests
    private final LinkedList<String> interests = new LinkedList<>();


    public ChooseTopicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_choose_topic, container, false);


        String[] interestsArray = getResources().getStringArray(R.array.interests);
        for (int i = 0; i < interestsArray.length; i++) {
            interests.addLast(interestsArray[i]);

        }

        // Connects recyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id._recyclerView);

        mAdapter = new GenericListAdapter(getActivity(), interests);

        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;

    }

}

