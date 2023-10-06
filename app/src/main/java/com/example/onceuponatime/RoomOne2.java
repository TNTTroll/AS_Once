package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.objects1;
import static com.example.onceuponatime.MainActivity.puzzles1;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponatime.Puzzles.FirstBookcase;
import com.example.onceuponatime.Puzzles.FirstPlates;

public class RoomOne2 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    public RoomOne2() {
    }

    public static RoomOne2 newInstance(String param1, String param2) {
        RoomOne2 fragment = new RoomOne2();
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
            case (R.id.first2Left):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne1()).addToBackStack(null).commit();
                break;

            case (R.id.first2Right):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne3()).addToBackStack(null).commit();
                break;

            case (R.id.firstBookcase):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstBookcase()).addToBackStack(null).commit();
                break;

            case (R.id.firstPlates):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstPlates()).addToBackStack(null).commit();
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_one2, container, false);

        for (ObjectInfo object : objects1.get(1)) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles1.get(1)) {
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