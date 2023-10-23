package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.objects1;
import static com.example.onceuponatime.MainActivity.puzzles1;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponatime.Puzzles.FirstChest;
import com.example.onceuponatime.Puzzles.FirstCloset;

public class RoomOne3 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object bg;

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

            case (R.id.first3Window):
                Object window = view.findViewById(R.id.first3Window);

                if (MainActivity.firstWindowOpen)
                    window.setIcon("room1_window_close");
                else
                    window.setIcon("room1_window_open");

                MainActivity.firstWindowOpen = !MainActivity.firstWindowOpen;

                break;

            case (R.id.first3Closet):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstCloset()).addToBackStack(null).commit();
                break;

            case (R.id.first3Chest):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstChest()).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_one3, container, false);

        bg = (Object) view.findViewById(R.id.first3BG);
        bg.setEnabled(false);

        for (ObjectInfo object : objects1.get(2)) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

                if (obj.name.trim().equals("first3Bird")) {
                    if (!MainActivity.firstWindowOpen || MainActivity.birds[0] == 1)
                        obj.setVisibility(View.GONE);

                } else if (obj.name.trim().equals("first3Window"))
                    if (MainActivity.firstWindowOpen) {
                        obj.setEnabled(false);
                        obj.setIcon("room1_window_open");
                        MainActivity.birds[0] = 1;
                    }


            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles1.get(2)) {
            try {
                int resID = getResId(puzzle.name, R.id.class);
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                puzz.setParam(puzzle.name, puzzle.scene, puzzle.icon);
                puzz.setOnClickListener(this);

                if (puzzle.used)
                    puzz.setEnabled(false);

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }
}