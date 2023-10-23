package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.objects2;
import static com.example.onceuponatime.MainActivity.puzzles2;
import static com.example.onceuponatime.Scene.getResId;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RoomTwo1 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;
    Object windowR, windowL, bg;

    public RoomTwo1() {
    }

    public static RoomTwo1 newInstance(String param1, String param2) {
        RoomTwo1 fragment = new RoomTwo1();
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
            case (R.id.second1Left):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo4()).addToBackStack(null).commit();
                break;

            case (R.id.second1Right):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo2()).addToBackStack(null).commit();
                break;

            case (R.id.second1WindowL):
                if (MainActivity.secondWindowsOpen[0])
                    windowL.setIcon("window_close");
                else
                    windowL.setIcon("window_open");

                MainActivity.secondWindowsOpen[0] = !MainActivity.secondWindowsOpen[0];

                break;

            case (R.id.second1WindowR):
                if (MainActivity.secondWindowsOpen[1])
                    windowR.setIcon("window_close");
                else
                    windowR.setIcon("window_open");

                MainActivity.secondWindowsOpen[1] = !MainActivity.secondWindowsOpen[1];

                break;
        }

        if (MainActivity.secondWindowsOpen[0] && MainActivity.secondWindowsOpen[1])
            bg.setIcon("bg_wall");
        else
            bg.setIcon("bg_room11");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_two1, container, false);

        bg = (Object) view.findViewById(R.id.second1BG);
        bg.setEnabled(false);

        windowL = (Object) view.findViewById(R.id.second1WindowL);
        windowL.setOnClickListener(this);

        windowR = (Object) view.findViewById(R.id.second1WindowR);
        windowR.setOnClickListener(this);

        for (ObjectInfo object : objects2.get(0)) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles2.get(0)) {
            try {
                int resID = getResId(puzzle.name, R.id.class);
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                puzz.setParam(puzzle.name, puzzle.scene, puzzle.icon);
                puzz.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        if (MainActivity.secondWindowsOpen[0])
            windowL.setIcon("window_open");

        if (MainActivity.secondWindowsOpen[1])
            windowR.setIcon("window_open");

        if (MainActivity.secondWindowsOpen[0] && MainActivity.secondWindowsOpen[1]) {
            bg.setIcon("bg_wall");
        }

        return view;
    }
}