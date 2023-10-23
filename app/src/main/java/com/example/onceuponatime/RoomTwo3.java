package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.objects2;
import static com.example.onceuponatime.MainActivity.puzzles2;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.onceuponatime.Puzzles.FirstCloset;
import com.example.onceuponatime.Puzzles.SecondTable;

public class RoomTwo3 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    public RoomTwo3() {
    }

    public static RoomTwo3 newInstance(String param1, String param2) {
        RoomTwo3 fragment = new RoomTwo3();
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
            case (R.id.second3Left):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo2()).addToBackStack(null).commit();
                break;

            case (R.id.second3Right):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo4()).addToBackStack(null).commit();
                break;

            case (R.id.second3Window):
                Object window = view.findViewById(R.id.second3Window);

                if (MainActivity.secondWindowOpen)
                    window.setIcon("room1_window_close");
                else
                    window.setIcon("room1_window_open");

                MainActivity.secondWindowOpen = !MainActivity.secondWindowOpen;

                break;

            case (R.id.second3Table):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new SecondTable()).addToBackStack(null).commit();
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_two3, container, false);

        Object bg = (Object) view.findViewById(R.id.second3BG);
        bg.setEnabled(false);

        for (ObjectInfo object : objects2.get(2)) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

                if (obj.name.trim().equals("second3Bird")) {
                    if (!MainActivity.secondWindowOpen || MainActivity.birds[1] == 1)
                        obj.setVisibility(View.GONE);

                } else if (obj.name.trim().equals("second3Window"))
                    if (MainActivity.secondWindowOpen) {
                        obj.setEnabled(false);
                        obj.setIcon("room1_window_open");
                        MainActivity.birds[1] = 1;
                    }

            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles2.get(2)) {
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