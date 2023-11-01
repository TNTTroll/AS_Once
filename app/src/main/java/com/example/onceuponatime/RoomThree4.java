package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.objects3;
import static com.example.onceuponatime.MainActivity.puzzles3;
import static com.example.onceuponatime.MainActivity.setLevel;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.onceuponatime.Puzzles.ThirdClock;
import com.example.onceuponatime.Puzzles.ThirdDoor;

public class RoomThree4 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    public RoomThree4() {
    }

    public static RoomThree4 newInstance(String param1, String param2) {
        RoomThree4 fragment = new RoomThree4();
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
            case (R.id.third4Left):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree3()).addToBackStack(null).commit();
                break;

            case (R.id.third4Right):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree1()).addToBackStack(null).commit();
                break;

            case (R.id.third4Door):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdDoor()).addToBackStack(null).commit();
                break;

            case (R.id.third4Clock):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdClock()).addToBackStack(null).commit();
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_three4, container, false);

        setLevel(3);

        Scene.showText(2);

        Object bg = (Object) view.findViewById(R.id.third4BG);
        bg.setEnabled(false);

        for (ObjectInfo object : objects3.get(3)) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

                if (obj.name.trim().equals("third4Bird")) {
                    if (MainActivity.birds[2] == 0)
                        obj.setVisibility(View.GONE);
                } else if (obj.name.trim().equals("third4Bird_1")) {
                    if (MainActivity.birds[0] == 0)
                        obj.setVisibility(View.GONE);
                } else if (obj.name.trim().equals("third4Bird_2")) {
                    if (MainActivity.birds[1] == 0)
                        obj.setVisibility(View.GONE);
                }
            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles3.get(3)) {
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