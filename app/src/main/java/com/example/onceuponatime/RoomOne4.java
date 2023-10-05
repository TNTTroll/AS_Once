package com.example.onceuponatime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class RoomOne4 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    ImageButton left, right;

    public RoomOne4() {
    }

    public static RoomOne4 newInstance(String param1, String param2) {
        RoomOne4 fragment = new RoomOne4();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.first4Left):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne3()).addToBackStack(null).commit();
                break;

            case (R.id.first4Right):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne1()).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_one4, container, false);

        left = (ImageButton) view.findViewById(R.id.first4Left);
        left.setOnClickListener(this);

        right = (ImageButton) view.findViewById(R.id.first4Right);
        right.setOnClickListener(this);

        return view;
    }
}