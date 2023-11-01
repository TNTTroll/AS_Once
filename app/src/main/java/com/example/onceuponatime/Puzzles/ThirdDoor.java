package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.holders3;
import static com.example.onceuponatime.MainActivity.objects3;
import static com.example.onceuponatime.Scene.current_Item;
import static com.example.onceuponatime.Scene.getResId;
import static com.example.onceuponatime.Scene.inventory;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onceuponatime.Ending;
import com.example.onceuponatime.Holder;
import com.example.onceuponatime.HolderInfo;
import com.example.onceuponatime.MainActivity;
import com.example.onceuponatime.Object;
import com.example.onceuponatime.ObjectInfo;
import com.example.onceuponatime.R;
import com.example.onceuponatime.RoomThree1;
import com.example.onceuponatime.RoomThree4;
import com.example.onceuponatime.Scene;

public class ThirdDoor extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object bg, time, back;

    Holder[] diamonds = new Holder[3];

    int[] endingBad = _PUZZLES.thirdEndingBad;
    int[] endingNeutral = _PUZZLES.thirdEndingNeutral;
    int[] endingGood = _PUZZLES.thirdEndingGood;

    public ThirdDoor() {
    }

    public static ThirdDoor newInstance(String param1, String param2) {
        ThirdDoor fragment = new ThirdDoor();
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
            case (R.id.thirdDoorBack):
                    getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree4()).addToBackStack(null).commit();
                    break;

            case (R.id.thirdDoorNewTime):

                if (MainActivity.thirdEnding == 1)
                    Toast.makeText(getActivity(), "BAD ENDING!", Toast.LENGTH_LONG).show();
                else if (MainActivity.thirdEnding == 2)
                    Toast.makeText(getActivity(), "NEUTRAL ENDING!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(), "GOOD ENDING!", Toast.LENGTH_LONG).show();

                if (!MainActivity.getAchievement(MainActivity.thirdEnding - 1))
                    MainActivity.setAchievement(MainActivity.thirdEnding - 1);

                startActivity(new Intent(getActivity(), Ending.class));

                break;
        }

        for (HolderInfo holder : holders3.get(3)) {
            int resID = getResId(holder.getName(), R.id.class);

            if (resID == v.getId()) {
                Holder hold = (Holder) view.findViewById(resID);

                if (holder.getName().startsWith("thirdDoor_")) {
                    if (!holder.getUsed()) {
                        if (current_Item != -1 && inventory[current_Item] != null) {
                            if (inventory[current_Item].getName().startsWith("thirdClockCrystal_")) {
                                hold.setKeep(inventory[current_Item]);
                                hold.setIcon(inventory[current_Item].getIcon());

                                holder.setUsed(true);

                                inventory[current_Item] = null;
                                current_Item = -1;
                            }
                        }

                    } else {
                        if (hold.getKeep().setToInventory()) {
                            hold.setIcon("plate");

                            holder.setUsed(false);
                        }
                    }

                }

                break;
            }
        }

        if (!MainActivity.thirdDoorDone) {
            if (checkDiamonds(endingBad))
                stopClicking(1);

            else if (checkDiamonds(endingNeutral))
                stopClicking(2);

            else if (checkDiamonds(endingGood))
                stopClicking(3);
        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_door, container, false);

        bg = (Object) view.findViewById(R.id.thirdDoorBG);
        bg.setEnabled(false);

        for (ObjectInfo object : objects3.get(3)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if (object.getName().trim().equals("thirdDoorBack")) {
                    back = obj;

                    if (MainActivity.thirdDoorDone)
                        back.setVisibility(View.GONE);

                } else if (object.getName().trim().equals("thirdDoorNewTime")) {
                    time = obj;

                    if (!MainActivity.thirdDoorDone)
                        time.setVisibility(View.GONE);
                }

            }
            catch(NullPointerException ignored) {}
        }

        int diamondCount = 0;
        for (HolderInfo holder : holders3.get(3)) {
            try {
                int resID = getResId(holder.getName(), R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.getName(), holder.getNeed(), holder.getIcon());
                hold.setOnClickListener(this);

                if ( holder.getName().startsWith("thirdDoor_") ) {
                    diamonds[diamondCount] = hold;

                    diamondCount += 1;

                }

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }

    private boolean checkDiamonds(int[] array) {
        for (int index = 0; index < array.length; index++) {

            if (diamonds[index].getKeep() == null)
                return false;

            int currentDiamond = Integer.parseInt(  "" + diamonds[index].getKeep().getName().charAt(diamonds[index].getKeep().getName().length()-1)  );

            if ( currentDiamond != array[index] )
                return false;
        }

        return true;
    }

    private void stopClicking(int ending) {
        MainActivity.thirdEnding = ending;

        MainActivity.thirdDoorDone = true;

        back.setVisibility(View.GONE);
        time.setVisibility(View.VISIBLE);

        for (int index = 1; index <= 3; index++) {
            int resID = getResId("thirdDoor_" + index, R.id.class);
            Holder hold = (Holder) view.findViewById(resID);

            hold.setVisibility(View.GONE);
        }

        Toast.makeText(getContext(), "Ending: " + ending, Toast.LENGTH_LONG).show();
    }
}