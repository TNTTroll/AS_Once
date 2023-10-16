package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.objects1;
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
import com.example.onceuponatime.RoomOne3;
import com.example.onceuponatime.Scene;

import java.util.ArrayList;

public class FirstCloset extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object arrow, bg, wall;

    int[] needColors = _PUZZLES.firstClosetSequence;

    int clicked = 0;
    ArrayList<Integer> usedColors = new ArrayList<>();
    Object[] colors = new Object[6];

    int posX[] = _PUZZLES.firstClosetPosX;
    int posY = _PUZZLES.firstClosetPosY;

    public FirstCloset() {
    }

    public static FirstCloset newInstance(String param1, String param2) {
        FirstCloset fragment = new FirstCloset();
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

        switch(v.getId()) {
            case (R.id.firstClosetBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne3()).addToBackStack(null).commit();
                break;

            case (R.id.firstMinuteArrow):
                if (arrow.setToInventory()) {
                    arrow.setVisibility(View.GONE);
                    MainActivity.firstTookMinuteArrow = true;

                }

                break;
        }

        if (!MainActivity.firstClosetDone) {
            for (Object color : colors) {
                int resID = getResId(color.getName(), R.id.class);

                if (resID == v.getId()) {
                    Object obj = (Object) view.findViewById(resID);

                    int currentPlate = Integer.parseInt("" + obj.getName().charAt(obj.getName().length() - 1));

                    usedColors.add(currentPlate);

                    break;
                }
            }

            if (checkColors()) {
                MainActivity.firstClosetDone = true;

                for (Object obj : colors)
                    obj.setVisibility(View.GONE);

                bg.setIcon("bg_closet_open");

                arrow.setVisibility(View.VISIBLE);
            }
        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_closet, container, false);

        wall = (Object) view.findViewById(R.id.firstClosetWallBG);
        wall.setEnabled(false);

        bg = (Object) view.findViewById(R.id.firstClosetBG);
        bg.setEnabled(false);
        if (MainActivity.firstClosetDone)
            bg.setIcon("bg_closet_open");

        int colorCount = 0;
        for (ObjectInfo object : objects1.get(2)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if ( object.getName().startsWith("firstMinuteArrow") ) {
                    arrow = obj;

                    if (MainActivity.firstTookMinuteArrow || !MainActivity.firstClosetDone)
                        arrow.setVisibility(View.GONE);

                } else if ( object.getName().startsWith("firstCloset_") ) {
                    if (MainActivity.firstClosetDone)
                        obj.setVisibility(View.GONE);

                    colors[colorCount] = obj;

                    colorCount += 1;

                    setPosition(obj);
                }

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }

    private boolean checkColors() {
        clicked += 1;

        if (clicked < needColors.length)
            return false;

        for (int x = 0; x < needColors.length; x++)
            if (needColors[x] != usedColors.get(clicked - needColors.length + x))
                return false;

        return true;
    }

    private void setPosition(Object obj) {
        for (int x = 0; x < colors.length; x++) {
            colors[x].setY( posY );
            colors[x].setX( posX[0] + posX[1] * x );
        }
    }
}