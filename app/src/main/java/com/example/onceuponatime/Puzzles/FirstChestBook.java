package com.example.onceuponatime.Puzzles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponatime.Object;
import com.example.onceuponatime.R;

public class FirstChestBook extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back, wall, bg;

    public FirstChestBook() {
    }

    public static FirstChestBook newInstance(String param1, String param2) {
        FirstChestBook fragment = new FirstChestBook();
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
        if (v.getId() == R.id.firstChestBookBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstChest()).addToBackStack(null).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_chest_book, container, false);

        back = (Object) view.findViewById(R.id.firstChestBookBack);
        back.setOnClickListener(this);

        wall = (Object) view.findViewById(R.id.firstChestBookWallBG);
        wall.setEnabled(false);

        bg = (Object) view.findViewById(R.id.firstChestBookBG);
        bg.setEnabled(false);

        return view;
    }
}