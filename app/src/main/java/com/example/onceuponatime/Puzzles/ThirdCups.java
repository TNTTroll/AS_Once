package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.holders3;
import static com.example.onceuponatime.MainActivity.objects3;
import static com.example.onceuponatime.Scene.current_Item;
import static com.example.onceuponatime.Scene.getResId;
import static com.example.onceuponatime.Scene.inventory;

import android.icu.number.Scale;
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
import com.example.onceuponatime.RoomThree1;
import com.example.onceuponatime.RoomThree2;
import com.example.onceuponatime.Scene;

public class ThirdCups extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object[] cups = new Object[4];

    public ThirdCups() {
    }

    public static ThirdCups newInstance(String param1, String param2) {
        ThirdCups fragment = new ThirdCups();
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
        if (v.getId() == R.id.thirdCupsBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree2()).addToBackStack(null).commit();

        if (!MainActivity.thirdEaselPaletteDone) {
            for (Object cup : cups) {
                int resID = getResId(cup.getName(), R.id.class);

                if (resID == v.getId()) {
                    Object obj = (Object) view.findViewById(resID);

                    if (obj.setToInventory()) {

                        int currentCup = Integer.parseInt("" + obj.getName().charAt(obj.getName().length() - 1)) - 1;

                        for (int index = 0; index < 4; index++) {
                            if (inventory[index] != null)
                                if (inventory[index].getName().startsWith("thirdCups_") && Integer.parseInt(inventory[index].getName().split("_")[1]) != (currentCup + 1)) {
                                    int inventoryCup = Integer.parseInt("" + inventory[index].getName().charAt(inventory[index].getName().length() - 1)) - 1;
                                    MainActivity.thirdCupsTook[inventoryCup] = false;

                                    Object cupReturn = (Object) view.findViewById(getResId("thirdCups_" + (inventoryCup + 1), R.id.class));
                                    cupReturn.setVisibility(View.VISIBLE);

                                    MainActivity.thirdCupsTookTap = -1;

                                    inventory[index] = null;
                                    current_Item = -1;
                                }
                        }

                        obj.setVisibility(View.GONE);

                        MainActivity.thirdCupsTook[currentCup] = true;
                    }

                    break;
                }
            }

            for (HolderInfo holder : holders3.get(1)) {
                int resID = getResId(holder.getName(), R.id.class);

                if (resID == v.getId()) {
                    Holder hold = (Holder) view.findViewById(resID);

                    if (MainActivity.thirdCupsTookTap == -1)
                        if (hold.getName().startsWith("thirdCupsTap_")) {
                            if (current_Item != -1 && inventory[current_Item] != null) {
                                MainActivity.thirdCupsTookTap = Integer.parseInt(hold.getName().split("_")[1]);

                                current_Item = -1;
                            }
                        }

                    break;
                }
            }
        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_cups, container, false);

        Object bg = (Object) view.findViewById(R.id.thirdCupsBG);
        bg.setEnabled(false);

        int cupCount = 0;
        for (ObjectInfo object : objects3.get(1)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if (object.getName().trim().startsWith("thirdCups_")) {
                    if (MainActivity.thirdCupsTook[cupCount])
                        obj.setVisibility(View.GONE);

                    cups[cupCount] = obj;

                    cupCount += 1;
                }

            }
            catch(NullPointerException ignored) {}
        }

        for (HolderInfo holder : holders3.get(1)) {
            try {
                int resID = getResId(holder.getName(), R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.getName(), holder.getNeed(), holder.getIcon());
                hold.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }
}