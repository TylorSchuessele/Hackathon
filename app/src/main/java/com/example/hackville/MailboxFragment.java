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
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MailboxFragment extends Fragment {

    //TTS string
    private final String TTS_MESSAGE = "Green button = Find a new pen pal.";

    // RecyclerView of interests
    private RecyclerView recyclerViewMail;

    // adapter for interests
    private GenericListAdapter mAdapter;

    // List of interests
    private LinkedList<String> contacts = new LinkedList<>();

    // New chat button
    private Button newButton;
    private ImageButton btnSpeak;

    public MailboxFragment() {
        // Required empty public constructor
    }

    //Set up callback
    CallBackInterface callBackInterface;
    public void setCallBackInterface(CallBackInterface callBackInterface){
        this.callBackInterface = callBackInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mailbox, container, false);


        final String[] nameArray = {"Nick", "Thomas", "Tylor", "Mark", "Davis Campus", "Ahmed", "Philips Hue", "Connor", "Justine"};
        for (int i = 0; i < nameArray.length; i++) {
            contacts.addLast(nameArray[i]);

        }

        // Connects recyclerView
        recyclerViewMail = view.findViewById(R.id._recyclerViewMailBox);
        newButton = view.findViewById(R.id.new_button);

        btnSpeak = view.findViewById(R.id.button_speak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackInterface != null) {
                    callBackInterface.textToSpeech(TTS_MESSAGE);

                    //Says all names
                    for(int i = 0; i < nameArray.length; i++) {
                        callBackInterface.textToSpeech(Integer.toString(i) + "." + nameArray[i]);
                    }
                }
            }
        });

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

    public void fetchPals() {
        String email = MainActivity.Email;
        DocumentReference mDocRef = FirebaseFirestore.getInstance().document("Users/" + email + "/PenPals/");
        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Map<String, Object> myData = documentSnapshot.getData();

                }
            }
        });

    }
}




