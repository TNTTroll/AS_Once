package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.objects1;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.onceuponatime.Puzzles._PUZZLES;

import java.util.ArrayList;

public class RoomOne1 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    ImageButton left, right, windowL, windowR;
    Object book;

    int[] needWindows = _PUZZLES.firstWindowsSequence;
    int clicked = 0;
    ArrayList<Integer> usedWindows = new ArrayList<>();

    public RoomOne1() {
    }

    public static RoomOne1 newInstance(String param1, String param2) {
        RoomOne1 fragment = new RoomOne1();
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
            case (R.id.first1Left):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne4()).addToBackStack(null).commit();
                break;

            case (R.id.first1Right):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne2()).addToBackStack(null).commit();
                break;

            case (R.id.firstWindowL):
                if (!MainActivity.firstWindows)
                    usedWindows.add(1);

                break;

            case (R.id.firstWindowR):
                if (!MainActivity.firstWindows)
                    usedWindows.add(2);

                break;

            case (R.id.firstBook):
                if (book.setToInventory()) {
                    book.setVisibility(View.GONE);
                    MainActivity.firstTookBook = true;
                }

                break;
        }

        if (!MainActivity.firstWindows)
            if (checkWindows()) {
                MainActivity.firstWindows = true;

                book.setVisibility(View.VISIBLE);
            }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_one1, container, false);

        left = (ImageButton) view.findViewById(R.id.first1Left);
        left.setOnClickListener(this);

        right = (ImageButton) view.findViewById(R.id.first1Right);
        right.setOnClickListener(this);

        windowL = (ImageButton) view.findViewById(R.id.firstWindowL);
        windowL.setOnClickListener(this);

        windowR = (ImageButton) view.findViewById(R.id.firstWindowR);
        windowR.setOnClickListener(this);

        for (ObjectInfo object : objects1.get(0)) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

                if (obj.name.trim().equals("firstBook"))
                    book = obj;

            }
            catch(NullPointerException ignored) {}
        }

        if (MainActivity.firstTookBook || !MainActivity.firstWindows)
            book.setVisibility(View.GONE);

        return view;
    }

    private boolean checkWindows() {
        clicked += 1;

        if (clicked < needWindows.length)
            return false;

        for (int x = 0; x < needWindows.length; x++)
            if (needWindows[x] != usedWindows.get(clicked - needWindows.length + x))
                return false;

            return true;
    }
}