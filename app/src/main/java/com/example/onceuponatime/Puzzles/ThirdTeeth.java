package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.Scene.getResId;

import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponatime.MainActivity;
import com.example.onceuponatime.Object;
import com.example.onceuponatime.R;
import com.example.onceuponatime.RoomThree1;

import java.util.ArrayList;

public class ThirdTeeth extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back, restart;

    int platesCount = 9;

    int progress = 0;

    ArrayList<Integer> usedPlates = new ArrayList<>();
    int[] needPlates = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    Object[] clickPlates = new Object[platesCount];

    Object[] showPlates = new Object[platesCount];
    int[] needShowPlates = {9, 8, 7, 6, 5, 4, 3, 2, 1};

    Handler handler = new Handler();

    public ThirdTeeth() {
    }

    public static ThirdTeeth newInstance(String param1, String param2) {
        ThirdTeeth fragment = new ThirdTeeth();
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
        if (v.getId() == R.id.thirdTeethBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree1()).addToBackStack(null).commit();

        else if (v.getId() == R.id.thirdTeethRestart)
            restartGame();

        int click = 0;
        for (int index = 1; index <= platesCount; index++) {
            Object obj = (Object) view.findViewById(getResId("thirdTeethClick_" + index, R.id.class));

            if (v.getId() == obj.getId()) {
                int currentPlate = Integer.parseInt("" + obj.getName().charAt(obj.getName().length() - 1)) - 1;

                usedPlates.add(currentPlate);

                click = index;

                break;
            }
        }

        if (click == needPlates[progress]) {
            clickPlates[click - 1].setIcon("black");

            if (usedPlates.size() == needPlates.length) {
                MainActivity.thirdTeethDone = true;

                for (Object obj : showPlates)
                    obj.setIcon("black");

                for (Object obj : clickPlates) {
                    obj.setIcon("black");
                    obj.setEnabled(false);
                }

            } else
                handler.postDelayed(this::showNextPlate, 500);

        } else
            restartGame();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_teeth, container, false);

        back = (Object) view.findViewById(R.id.thirdTeethBack);
        back.setOnClickListener(this);

        restart = (Object) view.findViewById(R.id.thirdTeethRestart);
        restart.setOnClickListener(this);

        for (int index = 1; index <= platesCount; index++) {
            Object plate = (Object) view.findViewById(getResId("thirdTeethClick_" + index, R.id.class));

            plate.setParam("thirdTeethClick_" + index, "plate");

            plate.setOnClickListener(this);

            clickPlates[index - 1] = plate;

            if (MainActivity.thirdTeethDone) {
                plate.setIcon("black");
                plate.setEnabled(false);
            }

            setPosition(plate);
        }

        for (int index = 1; index <= platesCount; index++) {
            Object plate = (Object) view.findViewById(getResId("thirdTeethShow_" + index, R.id.class));

            plate.setParam("thirdTeethShow_" + index, "plate");

            plate.setOnClickListener(this);

            showPlates[index - 1] = plate;

            if (MainActivity.thirdTeethDone) {
                plate.setIcon("black");
                plate.setEnabled(false);
            }

            setPosition(plate);
        }

        return view;
    }

    private void setPosition(Object obj) {

        Point size = new Point();
        MainActivity.display.getSize(size);

        double width = size.x * 0.3;
        double height = size.y * 0.18;

        obj.setX( (int) width );
        obj.setY( (int) height );
    }

    private void restartGame() {
        progress = 0;

        usedPlates.clear();

        for (Object obj : showPlates)
            obj.setIcon("plate");

        for (Object obj : clickPlates) {
            obj.setIcon("plate");
            obj.setEnabled(true);
        }
    }

    private void showNextPlate() {
        Object nextPlate = showPlates[needShowPlates[progress] - 1];

        nextPlate.setIcon("black");

        progress += 1;
    }
}