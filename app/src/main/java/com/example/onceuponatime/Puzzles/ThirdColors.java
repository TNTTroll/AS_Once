package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.objects3;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponatime.MainActivity;
import com.example.onceuponatime.Object;
import com.example.onceuponatime.ObjectInfo;
import com.example.onceuponatime.R;
import com.example.onceuponatime.RoomThree1;
import com.example.onceuponatime.RoomTwo2;

public class ThirdColors extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object book;
    boolean page = false;

    public ThirdColors() {
    }

    public static ThirdColors newInstance(String param1, String param2) {
        ThirdColors fragment = new ThirdColors();
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
        if (v.getId() == R.id.thirdColorsBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree1()).addToBackStack(null).commit();

        else if (v.getId() == R.id.thirdColorsBook) {
            if (page)
                book.setIcon("colors_1");
            else
                book.setIcon("colors_2");

            page = !page;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_colors, container, false);

        book = (Object) view.findViewById(R.id.thirdColorsBook);
        book.setParam("book", "colors_1");
        book.setOnClickListener(this);

        Object bg = (Object) view.findViewById(R.id.thirdColorsBG);
        bg.setEnabled(false);

        for (ObjectInfo object : objects3.get(0)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }
}