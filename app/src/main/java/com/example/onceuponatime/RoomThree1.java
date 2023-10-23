package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.objects3;
import static com.example.onceuponatime.MainActivity.puzzles3;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.onceuponatime.Puzzles.ThirdClocks;
import com.example.onceuponatime.Puzzles.ThirdColors;
import com.example.onceuponatime.Puzzles.ThirdTeeth;

public class RoomThree1 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    public RoomThree1() {
    }

    public static RoomThree1 newInstance(String param1, String param2) {
        RoomThree1 fragment = new RoomThree1();
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
            case (R.id.third1Left):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree4()).addToBackStack(null).commit();
                break;

            case (R.id.third1Right):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree2()).addToBackStack(null).commit();
                break;

            case (R.id.third1Clocks):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdClocks()).addToBackStack(null).commit();
                break;

            case (R.id.third1Windows):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdTeeth()).addToBackStack(null).commit();
                break;

            case (R.id.third1Colors):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdColors()).addToBackStack(null).commit();
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_three1, container, false);

        Object bg = (Object) view.findViewById(R.id.third1BG);
        bg.setEnabled(false);

        for (ObjectInfo object : objects3.get(0)) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles3.get(0)) {
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