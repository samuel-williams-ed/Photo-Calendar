package com.example.calendartutorial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private static final String TAG = "editEventFragment.java";

    private String mParamDate;
    private String mParamName;

    TextView nameBox;
    TextView dateBox;
    TextView locationBox;
    TextView detailsBox;
    FrameLayout frame;


    public editEventFragment() {
        // Required empty public constructor
    }

    public static editEventFragment newInstance(String date, String param_name) {
        editEventFragment fragment = new editEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, date);
        args.putString(ARG_PARAM2, param_name);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamDate = getArguments().getString(ARG_PARAM1);
            mParamName = getArguments().getString(ARG_PARAM2);
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

        dateBox= view.findViewById(R.id.EditEventFrag_date_TV);
        nameBox = view.findViewById(R.id.EditEventFrag_name_TV);
        locationBox = view.findViewById(R.id.EditEventFrag_location_TV);
        detailsBox = view.findViewById(R.id.EditEventFrag_details_TV);
        frame = view.findViewById(R.id.fragEditEvent_frame_FL);

        Log.d(TAG, "dateBox text = " + mParamDate);

        dateBox.setText(mParamDate);
        nameBox.setText(mParamName);

        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                frame.setBackgroundColor(getResources().getColor(R.color.teal_200, null));

            }
        });

    }

}