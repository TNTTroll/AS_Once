package com.example.onceuponatime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class RoomOne3 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    ImageButton left, right;

    public RoomOne3() {
    }

    public static RoomOne3 newInstance(String param1, String param2) {
        RoomOne3 fragment = new RoomOne3();
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
            case (R.id.first3Left):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne2()).addToBackStack(null).commit();
                break;

            case (R.id.first3Right):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne4()).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_one3, container, false);

        left = (ImageButton) view.findViewById(R.id.first3Left);
        left.setOnClickListener(this);

        right = (ImageButton) view.findViewById(R.id.first3Right);
        right.setOnClickListener(this);

        return view;
    }
}