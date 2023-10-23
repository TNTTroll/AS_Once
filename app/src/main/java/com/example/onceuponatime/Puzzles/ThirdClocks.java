package com.example.onceuponatime.Puzzles;

import static android.content.Context.DOMAIN_VERIFICATION_SERVICE;
import static com.example.onceuponatime.MainActivity.objects3;
import static com.example.onceuponatime.Scene.getResId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponatime.MainActivity;
import com.example.onceuponatime.Object;
import com.example.onceuponatime.ObjectInfo;
import com.example.onceuponatime.R;
import com.example.onceuponatime.RoomOne3;
import com.example.onceuponatime.RoomThree1;
import com.example.onceuponatime.Scene;

public class ThirdClocks extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object bg, arrow;
    Object[] clocks = new Object[4];

    int[] needClocks = {1, 0, 0, 0};

    int[] usedClocks = new int[4];

    public ThirdClocks() {
    }

    public static ThirdClocks newInstance(String param1, String param2) {
        ThirdClocks fragment = new ThirdClocks();
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
            case (R.id.thirdClocksBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree1()).addToBackStack(null).commit();
                break;

            case (R.id.thirdHourArrow):
                if (arrow.setToInventory()) {
                    arrow.setVisibility(View.GONE);
                    MainActivity.thirdTookHourArrow = true;

                }

                break;
        }

        if (!MainActivity.thirdClocksDone)
            for (Object clock : clocks) {
                int resID = getResId(clock.getName(), R.id.class);

                if (resID == v.getId()) {
                    Object obj = (Object) view.findViewById(resID);

                    int currentClock = Integer.parseInt(obj.getName().split("_")[1]) - 1;

                    clocks[currentClock].setRotation((usedClocks[currentClock] + 1) * 90);
                    usedClocks[currentClock] = (usedClocks[currentClock] + 1) % 4;

                    break;
                }
            }

        if (!MainActivity.thirdClocksDone)
            if (checkClocks()) {
                MainActivity.thirdClocksDone = true;

                for (Object clock : clocks)
                    clock.setEnabled(false);

                arrow.setVisibility(View.VISIBLE);
            }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_clocks, container, false);

        bg = (Object) view.findViewById(R.id.thirdClocksBG);
        bg.setEnabled(false);

        int clockCount = 0;
        for (ObjectInfo object : objects3.get(0)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if (object.getName().trim().startsWith("thirdClocks_")) {
                    if (MainActivity.thirdClocksDone) {
                        obj.setEnabled(false);
                        obj.setRotation(needClocks[clockCount] * 90);
                    }

                    clocks[clockCount] = obj;

                    usedClocks[clockCount] = 0;

                    clockCount += 1;
                } else if (object.getName().trim().equals("thirdHourArrow")) {
                    arrow = obj;

                    if (!MainActivity.thirdClocksDone || MainActivity.thirdTookHourArrow)
                        arrow.setVisibility(View.GONE);
                }

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }

    private boolean checkClocks() {
        for (int index = 0; index < needClocks.length; index++) {

            if ( usedClocks[index] != needClocks[index] )
                return false;
        }

        return true;
    }
}