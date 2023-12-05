package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.holders1;
import static com.example.onceuponatime.MainActivity.holders2;
import static com.example.onceuponatime.MainActivity.objects2;
import static com.example.onceuponatime.Scene.current_Item;
import static com.example.onceuponatime.Scene.getResId;
import static com.example.onceuponatime.Scene.inventory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponatime.Holder;
import com.example.onceuponatime.HolderInfo;
import com.example.onceuponatime.MainActivity;
import com.example.onceuponatime.Object;
import com.example.onceuponatime.ObjectInfo;
import com.example.onceuponatime.R;
import com.example.onceuponatime.RoomTwo2;
import com.example.onceuponatime.Scene;

import java.util.ArrayList;

public class SecondDesk extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object arrow, bg;
    Holder[] places = new Holder[3];

    String[] pinSequence = {"secondTablePin_1", "secondTablePin_2", "secondTablePin_3"};


    int[] needPins = {1, 3, 2};

    int clicked = 0;
    ArrayList<Integer> usedPin = new ArrayList<>();
    Holder[] pins = new Holder[needPins.length];

    public SecondDesk() {
    }

    public static SecondDesk newInstance(String param1, String param2) {
        SecondDesk fragment = new SecondDesk();
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
            case (R.id.secondDeskBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo2()).addToBackStack(null).commit();
                break;

            case (R.id.secondHourArrow):
                if (arrow.setToInventory()) {
                    arrow.setVisibility(View.GONE);
                    MainActivity.secondTookHourArrow = true;
                }

                break;
        }

        for (HolderInfo holder : holders2.get(1)) {
            int resID = getResId(holder.getName(), R.id.class);

            if (resID == v.getId()) {
                Holder hold = (Holder) view.findViewById(resID);

                if (holder.getName().startsWith("secondDesk_")) {
                    if (!holder.getUsed()) {
                        if (current_Item != -1 && inventory[current_Item] != null) {
                            int currentPlace = Integer.parseInt( "" + hold.getName().charAt(hold.getName().length()-1) ) - 1;

                            hold.setKeep(inventory[current_Item]);
                            hold.setIcon(inventory[current_Item].getIcon());

                            holder.setUsed(true);

                            MainActivity.secondDeskPlacedPins[currentPlace] = inventory[current_Item];

                            inventory[current_Item] = null;
                            current_Item = -1;
                        }

                    } else {
                        if (hold.getKeep().setToInventory()) {
                            hold.setIcon("no");

                            holder.setUsed(false);
                        }
                    }

                } else if (holder.getName().startsWith("secondDeskPin_")) {
                    if (current_Item != -1 && inventory[current_Item] != null) {
                        int currentPlate = Integer.parseInt( "" + hold.getName().charAt(hold.getName().length()-1) );

                        usedPin.add(currentPlate);

                        clicked += 1;
                    }
                }

                break;
            }
        }

        if (!MainActivity.secondDeskDone)
            if (checkPlaces()) {
                MainActivity.secondDeskDone = true;

                for (int index = 1; index <= 3; index++) {
                    int resID = getResId("secondDesk_" + index, R.id.class);
                    Holder hold = (Holder) view.findViewById(resID);

                    hold.setVisibility(View.GONE);
                }

                for (Holder pin : pins)
                    pin.setVisibility(View.VISIBLE);
            }

        if (!MainActivity.secondDeskPinsDone)
            if (checkPins()) {
                MainActivity.secondDeskPinsDone = true;

                bg.setIcon("bg_board_2");

                arrow.setVisibility(View.VISIBLE);

                for (Holder pin : pins)
                    pin.setVisibility(View.GONE);

                for (Object item : inventory)
                    if (item != null)
                        if (item.getName().equals("secondTableHammer")) {
                            inventory[current_Item] = null;
                            current_Item = -1;
                        }

            }

        Scene.reloadInventory();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second_desk, container, false);

        bg = (Object) view.findViewById(R.id.secondDeskBG);
        bg.setEnabled(false);

        if (MainActivity.secondDeskPinsDone)
            bg.setIcon("bg_board_2");

        for (ObjectInfo object : objects2.get(1)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if ( object.getName().startsWith("secondHourArrow") ) {
                    arrow = obj;

                    if (MainActivity.secondTookHourArrow || !MainActivity.secondDeskDone)
                        arrow.setVisibility(View.GONE);

                }

            }
            catch(NullPointerException ignored) {}
        }

        int placesCount = 0;
        int pinCount = 0;
        for (HolderInfo holder : holders2.get(1)) {
            try {
                int resID = getResId(holder.getName(), R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.getName(), holder.getNeed(), holder.getIcon());
                hold.setOnClickListener(this);

                if ( holder.getName().trim().startsWith("secondDesk_") ) {

                    if (MainActivity.secondDeskPlacedPins[placesCount] != null) {
                        hold.setKeep(MainActivity.secondDeskPlacedPins[placesCount]);
                        hold.setIcon(MainActivity.secondDeskPlacedPins[placesCount].getIcon());

                        holder.setUsed(true);
                    }

                    if (MainActivity.secondDeskDone)
                        hold.setVisibility(View.GONE);

                    places[placesCount] = hold;

                    placesCount += 1;

                } else if ( holder.getName().startsWith("secondDeskPin_") ) {

                    if (!MainActivity.secondDeskDone)
                        hold.setVisibility(View.GONE);

                    pins[pinCount] = hold;

                    pinCount += 1;
                }

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }

    private boolean checkPlaces() {
        for (int index = 0; index < pinSequence.length; index++) {
            if (places[index].getKeep() != null) {
                if (!places[index].getKeep().getName().trim().equals(pinSequence[index]))
                    return false;

            } else
                return false;
        }

        return true;
    }

    private boolean checkPins() {
        if (clicked < needPins.length)
            return false;

        for (int x = 0; x < needPins.length; x++)
            if (needPins[x] != usedPin.get(clicked - needPins.length + x))
                return false;

        return true;
    }
}