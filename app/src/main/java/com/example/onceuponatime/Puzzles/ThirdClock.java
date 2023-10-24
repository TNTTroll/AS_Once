package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.holders3;
import static com.example.onceuponatime.MainActivity.objects3;
import static com.example.onceuponatime.Scene.current_Item;
import static com.example.onceuponatime.Scene.getResId;
import static com.example.onceuponatime.Scene.inventory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponatime.Acception;
import com.example.onceuponatime.Holder;
import com.example.onceuponatime.HolderInfo;
import com.example.onceuponatime.MainActivity;
import com.example.onceuponatime.Object;
import com.example.onceuponatime.ObjectInfo;
import com.example.onceuponatime.R;
import com.example.onceuponatime.RoomThree4;
import com.example.onceuponatime.RoomTwo4;
import com.example.onceuponatime.Scene;

public class ThirdClock extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object hour, minute, bg, back;
    Holder clock;
    Object[] crystals = new Object[3];

    public ThirdClock() {
    }

    public static ThirdClock newInstance(String param1, String param2) {
        ThirdClock fragment = new ThirdClock();
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
            case (R.id.thirdClockBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree4()).addToBackStack(null).commit();
                break;

            case (R.id.thirdClockPlacement):

                if (current_Item != -1 && inventory[current_Item] != null) {
                    if ( inventory[current_Item].getName().trim().equals("thirdHourArrow") ) {
                        inventory[current_Item] = null;
                        current_Item = -1;

                        MainActivity.thirdHourArrowPlaced = true;

                        clock.setIcon("clock_display_hour");

                    } else if ( inventory[current_Item].getName().trim().equals("thirdMinuteArrow") ) {
                        inventory[current_Item] = null;
                        current_Item = -1;

                        MainActivity.thirdMinuteArrowPlaced = true;

                        clock.setIcon("clock_display_minute");
                    }
                }

                if (MainActivity.thirdMinuteArrowPlaced && MainActivity.thirdHourArrowPlaced) {
                    clock.setVisibility(View.GONE);

                    Log.d("CLOCK", "Placed");
                    for (Object crystal : crystals)
                        crystal.setVisibility(View.VISIBLE);
                }

                break;

        }

        for (Object crystal : crystals) {
            int resID = getResId(crystal.getName(), R.id.class);

            if (resID == v.getId()) {
                Object obj = (Object) view.findViewById(resID);

                if (obj.setToInventory()) {
                    obj.setVisibility(View.GONE);

                    int currentCrystal = Integer.parseInt( "" + obj.getName().charAt(obj.getName().length()-1) ) - 1;

                    MainActivity.thirdClockTookCrystal[currentCrystal] = true;

                    hour.setVisibility(View.GONE);
                    minute.setVisibility(View.GONE);

                    bg.setIcon("bg_wall");
                }

                break;
            }
        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_third_clock, container, false);

        bg = (Object) view.findViewById(R.id.thirdClockBG);
        bg.setEnabled(false);

        int crystalCount = 0;
        for (ObjectInfo object : objects3.get(3)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if ( object.getName().trim().equals("thirdClockHour") ) {
                    hour = obj;

                    if (!MainActivity.thirdHourArrowPlaced)
                        obj.setVisibility(View.GONE);

                } else if ( object.getName().trim().equals("thirdClockMinute") ) {
                    minute = obj;

                    if (!MainActivity.thirdMinuteArrowPlaced)
                        obj.setVisibility(View.GONE);

                } else if ( object.getName().trim().equals("thirdClockBack")  )
                    back = obj;

                else if ( object.getName().trim().startsWith("thirdClockCrystal_")  ) {

                    int currentCrystal = Integer.parseInt( "" + obj.getName().charAt(obj.getName().length()-1) ) - 1;

                    if (!MainActivity.thirdHourArrowPlaced || !MainActivity.thirdMinuteArrowPlaced || MainActivity.thirdClockTookCrystal[currentCrystal])
                        obj.setVisibility(View.GONE);

                    crystals[crystalCount] = obj;

                    crystalCount += 1;
                }

            }
            catch(NullPointerException ignored) {}
        }

        if (MainActivity.thirdMinuteArrowPlaced && MainActivity.thirdHourArrowPlaced) {
            bg.setIcon("bg_wall");

            hour.setVisibility(View.GONE);
            minute.setVisibility(View.GONE);
        }

        for (HolderInfo holder : holders3.get(3)) {
            try {
                int resID = getResId(holder.getName(), R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.getName(), holder.getNeed(), holder.getIcon());
                hold.setOnClickListener(this);

                if ( holder.getName().trim().equals("thirdClockPlacement") ) {
                    clock = hold;

                    if (MainActivity.thirdHourArrowPlaced && MainActivity.thirdMinuteArrowPlaced)
                        hold.setVisibility(View.GONE);
                }

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }
}