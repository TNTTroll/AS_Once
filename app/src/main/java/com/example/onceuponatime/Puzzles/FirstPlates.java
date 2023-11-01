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
import com.example.onceuponatime.RoomOne2;
import com.example.onceuponatime.Scene;

import java.util.ArrayList;

public class FirstPlates extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object arrow, wall;

    int[] needPlate = _PUZZLES.firstPlatesSequence;

    int clicked = 0;
    ArrayList<Integer> usedPlate = new ArrayList<>();
    Object[] plates = new Object[needPlate.length];

    int[] achievePlate = {2, 4, 3, 5, 1};

    public FirstPlates() {
    }

    public static FirstPlates newInstance(String param1, String param2) {
        FirstPlates fragment = new FirstPlates();
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

        for (Object plate : plates) {
            int resID = getResId(plate.getName(), R.id.class);

            if (resID == v.getId()) {
                Object obj = (Object) view.findViewById(resID);

                int currentPlate = Integer.parseInt( "" + obj.getName().charAt(obj.getName().length()-1) );

                usedPlate.add(currentPlate);

                break;
            }
        }

        if (!MainActivity.firstPlatesDone) {
            if (checkPlates()) {
                MainActivity.firstPlatesDone = true;

                arrow.setVisibility(View.VISIBLE);

                for (Object plate : plates)
                    plate.setEnabled(false);

                plates[0].setIcon("plates_1_open");
            }

            else if (!MainActivity.getAchievement(3))
                if (checkAchieve())
                    MainActivity.setAchievement(3);
        }

        switch (v.getId()) {
            case (R.id.firstPlatesBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne2()).addToBackStack(null).commit();
                break;

            case (R.id.firstHourArrow):
                if (arrow.setToInventory()) {
                    arrow.setVisibility(View.GONE);
                    MainActivity.firstTookHourArrow = true;

                }

                break;
        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_plates, container, false);

        wall = (Object) view.findViewById(R.id.firstPlatesWallBG);
        wall.setEnabled(false);

        int plateCount = 0;
        for (ObjectInfo object : objects1.get(1)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if ( object.getName().startsWith("firstHourArrow") ) {
                    arrow = obj;

                    if (MainActivity.firstTookHourArrow || !MainActivity.firstPlatesDone)
                        arrow.setVisibility(View.GONE);

                } else if ( object.getName().startsWith("firstPlate_") ) {

                    if (MainActivity.firstPlatesDone)
                        obj.setEnabled(false);

                    plates[plateCount] = obj;

                    plateCount += 1;
                }

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }

    private boolean checkPlates() {
        clicked += 1;

        if (clicked < needPlate.length)
            return false;

        for (int x = 0; x < needPlate.length; x++)
            if (needPlate[x] != usedPlate.get(clicked - needPlate.length + x))
                return false;

        return true;
    }

    private boolean checkAchieve() {
        if (clicked < achievePlate.length)
            return false;

        for (int x = 0; x < achievePlate.length; x++)
            if (achievePlate[x] != usedPlate.get(clicked - achievePlate.length + x))
                return false;

        return true;
    }
}