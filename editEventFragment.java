package com.example.calendartutorial;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editEventFragment extends Fragment {

//    EventsDatabaseHelper edh = new EventsDatabaseHelper(null);
//    EventObject defaultEvent = edh.getEventById();

    private static final String ARG_PARAM1 = "01/11/1993";
    private static final String ARG_PARAM2 = "Event failed to load";
    private static final String ARG_PARAM3 = "0";
    private static final String TAG = "editEventFragment.java";

    private String mParamDate;
    private String mParamName;
    private String mParamDatabaseId;

    TextView nameBox;
    TextView dateBox;
    TextView locationBox;
    TextView detailsBox;
    FrameLayout frame;
    FragmentCloser editEventFrag;

    Button add_name_button;
    Button add_location_button;
    Button add_details_button;
    Button delete_event_button;

    String userValues_name;
    String userValues_location;
    String userValues_details;

    EventsDatabaseHelper edh;
    EventObject event;

    public interface FragmentCloser {
        public void removeFragment();
    }


    public editEventFragment() {
        // Required empty public constructor
    }

    public static editEventFragment newInstance(String date, String param_name, String database_id) {
        editEventFragment fragment = new editEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, date);
        args.putString(ARG_PARAM2, param_name);
        args.putString(ARG_PARAM3, database_id);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentCloser){
            editEventFrag = (FragmentCloser) context;
        } else {
            Log.d(TAG, "The interface was not implemented in the parent Activtiy!");
        }
        edh = new EventsDatabaseHelper(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamDate = getArguments().getString(ARG_PARAM1);
            mParamName = getArguments().getString(ARG_PARAM2);
            mParamDatabaseId = getArguments().getString(ARG_PARAM3);

            Log.d(TAG, "initialising event Object in editEventFragment.java, mParamDatabaseId = " + mParamDatabaseId);
            event = edh.getEventByDatabaseId(mParamDatabaseId); //TODO find why this failed
            Log.d(TAG, "We have an event!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_event, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        frame = view.findViewById(R.id.fragEditEvent_frame_FL);

        dateBox= view.findViewById(R.id.EditEventFrag_date_TV);
        nameBox = view.findViewById(R.id.EditEventFrag_name_TV);
        locationBox = view.findViewById(R.id.EditEventFrag_location_TV);
        detailsBox = view.findViewById(R.id.EditEventFrag_details_TV);

        add_name_button = view.findViewById(R.id.frag_editName_addButton_BU);
        add_location_button = view.findViewById(R.id.frag_editLocation_addButton_BU);
        add_details_button = view.findViewById(R.id.frag_editDetails_addButton_BU);
        delete_event_button = view.findViewById((R.id.EditEventFrag_Delete_BU));


        Log.d(TAG, "dateBox text = " + mParamDate);

        dateBox.setText(mParamDate);
        nameBox.setText(mParamName);

        //edit the name
        add_name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    userValues_name = nameBox.getText().toString();
                } catch (Exception e) {
                    Log.d(TAG, "AddNameButton: error getting user values from testText object");
                    e.printStackTrace();
                }
                Log.d(TAG, "AddNameButton: button has been pressed");
                edh.editEventName(event, userValues_name);

                Log.d(TAG, "AddNameButton: Database changed! Name " + userValues_name + " added to database.");
            }
        });

        delete_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "edh.deleteEvent() firing");
                edh.deleteEvent(event);
                Log.d(TAG, "editEventFrag.removeFragment() firing");
                editEventFrag.removeFragment();
            }
        });

        // on frame click close fragment
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                frame.setBackgroundColor(getResources().getColor(R.color.teal_200, null));
                editEventFrag.removeFragment();
            }
        });

    }

}

//TODO close the fragment logic
// create a functional interface with fragment operations
// listener event should fire on DayActivity via an interface method:
// define an interface type in the fragment
// make the activity implement the interface:
// - define a listener of the interface type in the Activity
// - - this will be the Activity's instance of/reference to the fragment
// - define interface
// - - declare events/methods to fire
// - @Override onAttach() method to initialise the listener as the 'context' passed as a param to onAttach().
// - - N.B. may need to cast this to the interface type.
// - - N.B. this checks the listener (context from the onAttach() method) does implement the interface.
// - define onCLickListener()
// // - - fire events/methods declared earlier as part of interface
// in Activity, implement interface
// in onCreate() initialise fragment, getSupportFragmentManager().findFragmentById(R.id.frag_name);
// @Override the interface's method being fired (in Activity, onClickListener) logic
// - so...
// begin transaction with fragManager
// identify this editEventFragment object instance - findFragmentById()  ***
// call remove() passing the fragment instance
// commit transaction