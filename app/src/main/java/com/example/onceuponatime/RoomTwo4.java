package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.objects2;
import static com.example.onceuponatime.MainActivity.puzzles2;
import static com.example.onceuponatime.MainActivity.setLevel;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.onceuponatime.Puzzles.FirstClock;
import com.example.onceuponatime.Puzzles.SecondClock;

public class RoomTwo4 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    public RoomTwo4() {
    }

    public static RoomTwo4 newInstance(String param1, String param2) {
        RoomTwo4 fragment = new RoomTwo4();
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
            case (R.id.second4Left):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo3()).addToBackStack(null).commit();
                break;

            case (R.id.second4Right):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo1()).addToBackStack(null).commit();
                break;

            case (R.id.second4Clock):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new SecondClock()).addToBackStack(null).commit();
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_two4, container, false);

        setLevel(2);

        Scene.showText(1);

        Object bg = (Object) view.findViewById(R.id.second4BG);
        bg.setEnabled(false);

        for (ObjectInfo object : objects2.get(3)) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

                if (obj.name.trim().equals("second4Bird")) {
                    if (MainActivity.birds[1] == 0)
                        obj.setVisibility(View.GONE);
                } else if (obj.name.trim().equals("second4Bird_1")) {
                    if (MainActivity.birds[0] == 0)
                        obj.setVisibility(View.GONE);
                }
            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles2.get(3)) {
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