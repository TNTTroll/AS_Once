package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.objects2;
import static com.example.onceuponatime.MainActivity.puzzles2;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.onceuponatime.Puzzles.SecondDesk;
import com.example.onceuponatime.Puzzles.SecondPiano;

public class RoomTwo2 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    public RoomTwo2() {
    }

    public static RoomTwo2 newInstance(String param1, String param2) {
        RoomTwo2 fragment = new RoomTwo2();
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
            case (R.id.second2Left):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo1()).addToBackStack(null).commit();
                break;

            case (R.id.second2Right):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo3()).addToBackStack(null).commit();
                break;

            case (R.id.second2Piano):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new SecondPiano()).addToBackStack(null).commit();
                break;

            case (R.id.second2Desk):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new SecondDesk()).addToBackStack(null).commit();
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_two2, container, false);

        Object bg = (Object) view.findViewById(R.id.second2BG);
        bg.setEnabled(false);

        for (ObjectInfo object : objects2.get(1)) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles2.get(1)) {
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