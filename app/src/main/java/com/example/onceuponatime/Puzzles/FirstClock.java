package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.holders1;
import static com.example.onceuponatime.MainActivity.objects1;
import static com.example.onceuponatime.MainActivity.setLevel;
import static com.example.onceuponatime.Scene.current_Item;
import static com.example.onceuponatime.Scene.getResId;
import static com.example.onceuponatime.Scene.inventory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onceuponatime.Acception;
import com.example.onceuponatime.Holder;
import com.example.onceuponatime.HolderInfo;
import com.example.onceuponatime.MainActivity;
import com.example.onceuponatime.Object;
import com.example.onceuponatime.ObjectInfo;
import com.example.onceuponatime.R;
import com.example.onceuponatime.RoomOne1;
import com.example.onceuponatime.RoomOne4;
import com.example.onceuponatime.RoomTwo4;
import com.example.onceuponatime.Scene;

public class FirstClock extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object hour, minute, bg, btnH, btnM, back, time;
    Holder clock;

    int[] arrowRotation = {1, 0};

    public FirstClock() {
    }

    public static FirstClock newInstance(String param1, String param2) {
        FirstClock fragment = new FirstClock();
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
            case (R.id.firstClockBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne4()).addToBackStack(null).commit();
                break;

            case (R.id.firstClockPlacement):

                if (current_Item != -1) {
                    if ( inventory[current_Item].getName().trim().equals("firstHourArrow") ) {
                        inventory[current_Item] = null;
                        current_Item = -1;

                        MainActivity.firstHourArrowPlaced = true;

                        clock.setIcon("clock_display_hour");

                    } else if ( inventory[current_Item].getName().trim().equals("firstMinuteArrow") ) {
                        inventory[current_Item] = null;
                        current_Item = -1;

                        MainActivity.firstMinuteArrowPlaced = true;

                        clock.setIcon("clock_display_minute");
                    }
                }

                if (MainActivity.firstMinuteArrowPlaced && MainActivity.firstHourArrowPlaced) {
                    clock.setVisibility(View.GONE);
                    hour.setVisibility(View.VISIBLE);
                    minute.setVisibility(View.VISIBLE);

                    btnH.setEnabled(true);
                    btnM.setEnabled(true);
                }

                break;

            case (R.id.firstClockBtnHour):
                hour.setRotation((arrowRotation[0] + 1) * 30);
                arrowRotation[0] = (arrowRotation[0] + 1) % 12;

                break;

            case (R.id.firstClockBtnMinute):
                minute.setRotation((arrowRotation[1] + 1) * 30);
                arrowRotation[1] = (arrowRotation[1] + 1) % 12;

                break;

            case (R.id.firstClockNewTime):
                new Acception().show(getParentFragmentManager(), "acceptation");
                break;
        }

        if ( _PUZZLES.firstTime[0] == arrowRotation[0] && _PUZZLES.firstTime[1] == arrowRotation[1] ) {
            btnH.setEnabled(false);
            btnM.setEnabled(false);

            time.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_clock, container, false);

        bg = (Object) view.findViewById(R.id.firstClockBG);
        bg.setEnabled(false);

        for (ObjectInfo object : objects1.get(3)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if ( object.getName().trim().equals("firstClockHour") ) {
                    hour = obj;

                    if (!MainActivity.firstHourArrowPlaced)
                        obj.setVisibility(View.GONE);

                } else if ( object.getName().trim().equals("firstClockMinute") ) {
                    minute = obj;

                    if (!MainActivity.firstMinuteArrowPlaced)
                        obj.setVisibility(View.GONE);

                } else if ( object.getName().trim().equals("firstClockBtnHour") )
                    btnH = obj;
                else if ( object.getName().trim().equals("firstClockBtnMinute") )
                    btnM = obj;
                else if ( object.getName().trim().equals("firstClockBack")  )
                    back = obj;
                else if ( object.getName().trim().equals("firstClockNewTime")  ) {
                    time = obj;
                    back.setVisibility(View.GONE);
                    //time.setVisibility(View.GONE);
                }

            }
            catch(NullPointerException ignored) {}
        }

        for (HolderInfo holder : holders1.get(3)) {
            try {
                int resID = getResId(holder.getName(), R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.getName(), holder.getNeed(), holder.getIcon());
                hold.setOnClickListener(this);

                if ( holder.getName().trim().equals("firstClockPlacement") ) {
                    clock = hold;

                    if (MainActivity.firstHourArrowPlaced && MainActivity.firstMinuteArrowPlaced)
                        hold.setVisibility(View.GONE);
                    else {
                        btnH.setEnabled(false);
                        btnM.setEnabled(false);
                    }
                }

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }
}