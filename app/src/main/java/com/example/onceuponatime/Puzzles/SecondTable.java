package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.objects2;
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
import com.example.onceuponatime.RoomOne2;
import com.example.onceuponatime.RoomTwo3;
import com.example.onceuponatime.Scene;

import java.util.ArrayList;
import java.util.Arrays;

public class SecondTable extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object bg, hammer, hLocker;
    Object[] pins = new Object[3];

    int[] needLocker = _PUZZLES.secondTableLockersSequence;

    int clickedLocker = 0;
    ArrayList<Integer> usedLocker = new ArrayList<>();
    Object[] lockers = new Object[needLocker.length];


    int[] needImage = _PUZZLES.secondTableImagesSequence;

    int clickedImage = 0;
    ArrayList<Integer> usedImage = new ArrayList<>();
    Object[] images = new Object[needImage.length];

    int[] achieveImage = {1, 2, 3, 4, 5};

    public SecondTable() {
    }

    public static SecondTable newInstance(String param1, String param2) {
        SecondTable fragment = new SecondTable();
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
            case (R.id.secondTableBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo3()).addToBackStack(null).commit();
                break;

            case (R.id.secondTableHammer):
                if (hammer.setToInventory()) {
                    hammer.setVisibility(View.GONE);
                    MainActivity.secondTableTookHammer = true;

                }

                break;
        }

        for (Object locker : lockers) {
            int resID = getResId(locker.getName(), R.id.class);

            if (resID == v.getId()) {
                Object obj = (Object) view.findViewById(resID);

                int currentLocker = Integer.parseInt( "" + obj.getName().charAt(obj.getName().length()-1) );

                usedLocker.add(currentLocker);

                clickedLocker += 1;

                break;
            }
        }

        for (Object image : images) {
            int resID = getResId(image.getName(), R.id.class);

            if (resID == v.getId()) {
                Object obj = (Object) view.findViewById(resID);

                int currentImage = Integer.parseInt( "" + obj.getName().charAt(obj.getName().length()-1) );

                usedImage.add(currentImage);

                clickedImage += 1;

                break;
            }
        }

        for (Object pin : pins) {
            int resID = getResId(pin.getName(), R.id.class);

            if (resID == v.getId()) {
                Object obj = (Object) view.findViewById(resID);

                if (obj.setToInventory()) {
                    obj.setVisibility(View.GONE);

                    int currentPin = Integer.parseInt( "" + obj.getName().charAt(obj.getName().length()-1) ) - 1;

                    MainActivity.secondTableTookPins[currentPin] = true;
                }

                break;
            }
        }

        if (!MainActivity.secondTableLockersDone)
            if (checkLocker()) {
                MainActivity.secondTableLockersDone = true;

                for (Object locker : lockers)
                    locker.setVisibility(View.GONE);

                for (Object pin : pins)
                    pin.setVisibility(View.VISIBLE);
            }

        if (!MainActivity.secondTableImagesDone) {
            if (checkImage()) {
                MainActivity.secondTableImagesDone = true;

                for (Object image : images)
                    image.setEnabled(false);

                hammer.setVisibility(View.VISIBLE);

                hLocker.setVisibility(View.GONE);
            }

            else if (!MainActivity.getAchievement(5))
                if (checkAchieve())
                    MainActivity.setAchievement(5);
        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second_table, container, false);

        bg = (Object) view.findViewById(R.id.secondTableBG);
        bg.setEnabled(false);

        hLocker = (Object) view.findViewById(R.id.secondTableHammerLocker);
        hLocker.setEnabled(false);

        if (MainActivity.secondTableImagesDone)
            hLocker.setVisibility(View.GONE);

        int lockerCount = 0;
        int pinCount = 0;
        int imageCount = 0;
        for (ObjectInfo object : objects2.get(2)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if ( object.getName().startsWith("secondTableLocker_") ) {

                    if (MainActivity.secondTableLockersDone)
                        obj.setVisibility(View.GONE);

                    lockers[lockerCount] = obj;

                    lockerCount += 1;

                } else if ( object.getName().startsWith("secondTablePin_") ) {

                    int currentPin = Integer.parseInt( "" + obj.getName().charAt(obj.getName().length()-1) ) - 1;

                    if (MainActivity.secondTableTookPins[currentPin] || !MainActivity.secondTableLockersDone)
                        obj.setVisibility(View.GONE);

                    pins[pinCount] = obj;

                    pinCount += 1;

                } else if ( object.getName().startsWith("secondTableImages_") ) {

                    if (MainActivity.secondTableImagesDone)
                        obj.setEnabled(false);

                    images[imageCount] = obj;

                    imageCount += 1;

                } else if ( object.getName().equals("secondTableHammer") ) {

                    hammer = obj;

                    if (MainActivity.secondTableTookHammer || !MainActivity.secondTableImagesDone)
                        obj.setVisibility(View.GONE);
                }

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }

    private boolean checkLocker() {
        if (clickedLocker < needLocker.length)
            return false;

        for (int x = 0; x < needLocker.length; x++)
            if (needLocker[x] != usedLocker.get(clickedLocker - needLocker.length + x))
                return false;

        return true;
    }

    private boolean checkImage() {
        if (clickedImage < needImage.length)
            return false;

        for (int x = 0; x < needImage.length; x++)
            if (needImage[x] != usedImage.get(clickedImage - needImage.length + x))
                return false;

        return true;
    }

    private boolean checkAchieve() {
        if (clickedImage < achieveImage.length)
            return false;

        for (int x = 0; x < achieveImage.length; x++)
            if (achieveImage[x] != usedImage.get(clickedImage - achieveImage.length + x))
                return false;

        return true;
    }
}