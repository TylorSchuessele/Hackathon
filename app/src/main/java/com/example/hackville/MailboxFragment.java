package com.example.hackville;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MailboxFragment extends Fragment {


    // RecyclerView of interests
    private RecyclerView recyclerViewMail;

    // adapter for interests
    private GenericListAdapter mAdapter;

    // List of interests
    private  LinkedList<String> contacts = new LinkedList<>();

    // New chat button
    private Button newButton;

    public MailboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_mailbox, container, false);

        String[] nameArray = {"Nick", "Thomas", "Tylor", "Mark", "Davis Campus", "Ahmed", "Philips Hue", "Connor", "Justine"};
        for (int i = 0; i < nameArray.length; i++) {
            contacts.addLast(nameArray[i]);

        }

        // Connects recyclerView
        recyclerViewMail = view.findViewById(R.id._recyclerViewMailBox);
        newButton = view.findViewById(R.id.new_button);

        mAdapter = new GenericListAdapter(getActivity(), contacts);

        recyclerViewMail.setAdapter(mAdapter);

        recyclerViewMail.setLayoutManager(new LinearLayoutManager(getActivity()));


        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.container, new ChooseTopicFragment()).commit();
            }
        });

        return view;
    }
    public void newClick(View view) {
    }

}