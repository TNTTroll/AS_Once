package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.objects1;
import static com.example.onceuponatime.MainActivity.puzzles1;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.onceuponatime.Puzzles.FirstClock;
import com.example.onceuponatime.Puzzles.FirstCloset;

public class RoomOne4 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object bg;

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

            case (R.id.first4Clock):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstClock()).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_one4, container, false);

        bg = (Object) view.findViewById(R.id.first4BG);
        bg.setEnabled(false);

        for (ObjectInfo object : objects1.get(3)) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

                if (obj.name.trim().equals("first4Bird")) {
                    if (!MainActivity.firstBird1Saw)
                        obj.setVisibility(View.GONE);
                }
            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles1.get(3)) {
            try {
                int resID = getResId(puzzle.name, R.id.class);
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                puzz.setParam(puzzle.name, puzzle.scene, puzzle.icon);
                puzz.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }
}