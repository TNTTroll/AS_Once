package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.objects2;
import static com.example.onceuponatime.Scene.current_Item;
import static com.example.onceuponatime.Scene.getResId;
import static com.example.onceuponatime.Scene.inventory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponatime.MainActivity;
import com.example.onceuponatime.Object;
import com.example.onceuponatime.ObjectInfo;
import com.example.onceuponatime.R;
import com.example.onceuponatime.RoomOne4;
import com.example.onceuponatime.RoomTwo2;
import com.example.onceuponatime.Scene;

import java.util.ArrayList;

public class SecondPiano extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object arrow, slot;

    int[] needPlate = {1, 2, 3, 4};

    int clicked = 0;
    ArrayList<Integer> usedPlate = new ArrayList<>();
    Object[] plates = new Object[7];

    public SecondPiano() {
    }

    public static SecondPiano newInstance(String param1, String param2) {
        SecondPiano fragment = new SecondPiano();
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
            case (R.id.secondPianoBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo2()).addToBackStack(null).commit();
                break;

            case (R.id.secondMinuteArrow):
                if (arrow.setToInventory()) {
                    arrow.setVisibility(View.GONE);
                    MainActivity.secondTookMinuteArrow = true;
                }

                break;
        }

        for (Object plate : plates) {
            int resID = getResId(plate.getName(), R.id.class);

            if (resID == v.getId()) {
                Object obj = (Object) view.findViewById(resID);

                int currentPlate = Integer.parseInt( "" + obj.getName().charAt(obj.getName().length()-1) );

                usedPlate.add(currentPlate);

                if (!MainActivity.getAchievement(6))
                    if (current_Item != -1 && inventory[current_Item] != null)
                        if ( inventory[current_Item].getName().trim().equals("secondTableHammer") )
                            MainActivity.setAchievement(6);

                break;
            }
        }

        if (!MainActivity.secondPianoDone)
            if (checkPlates()) {
                MainActivity.secondPianoDone = true;

                arrow.setVisibility(View.VISIBLE);

                slot.setVisibility(View.GONE);

                for (Object plate : plates)
                    plate.setEnabled(false);
            }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second_piano, container, false);

        Object wall = (Object) view.findViewById(R.id.secondPianoBG);
        wall.setEnabled(false);

        slot = (Object) view.findViewById(R.id.secondPianoSlot);
        slot.setEnabled(false);

        if (MainActivity.secondPianoDone)
            slot.setVisibility(View.GONE);

        int plateCount = 0;
        for (ObjectInfo object : objects2.get(1)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if ( object.getName().startsWith("secondMinuteArrow") ) {
                    arrow = obj;

                    if (MainActivity.secondTookMinuteArrow || !MainActivity.secondPianoDone)
                        arrow.setVisibility(View.GONE);

                } else if ( object.getName().startsWith("secondPiano_") ) {

                    if (MainActivity.secondPianoDone)
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
}