package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.objects1;
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

public class FirstChest extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object check, book, bg, wall;

    String[] needSigns = _PUZZLES.firstChestSequence;
    Object[] signs = new Object[5];

    int posX[] = _PUZZLES.firstChestPosX;
    int posY = _PUZZLES.firstChestPosY;

    public FirstChest() {
    }

    public static FirstChest newInstance(String param1, String param2) {
        FirstChest fragment = new FirstChest();
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
            case (R.id.firstChestBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne3()).addToBackStack(null).commit();
                break;

            case (R.id.firstChestCheck):
                if (!MainActivity.firstChestDone)
                    if (checkSigns()) {
                        MainActivity.firstChestDone = true;

                        bg.setIcon("bg_chest_open");

                        check.setVisibility(View.GONE);

                        for (Object sign : signs)
                            sign.setVisibility(View.GONE);

                        book.setVisibility(View.VISIBLE);
                    }

                break;

            case (R.id.firstChestBook):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstChestBook()).addToBackStack(null).commit();
                break;
        }

        if (!MainActivity.firstChestDone)
            for (Object sign : signs) {
                int resID = getResId(sign.getName(), R.id.class);

                if (resID == v.getId()) {
                    Object obj = (Object) view.findViewById(resID);

                    int currentSign = Integer.parseInt(obj.getIcon().split("_")[1]);

                    if (currentSign % 3 == 0)
                        obj.setIcon("symbol_" + (currentSign - 2));
                    else
                        obj.setIcon("symbol_" + (currentSign + 1));

                    break;
                }
            }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_chest, container, false);

        wall = (Object) view.findViewById(R.id.firstChestWallBG);
        wall.setEnabled(false);

        bg = (Object) view.findViewById(R.id.firstChestBG);
        bg.setEnabled(false);
        if (MainActivity.firstChestDone)
            bg.setIcon("bg_chest_open");

        int signCount = 0;
        for (ObjectInfo object : objects1.get(2)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if ( object.getName().startsWith("firstChest_") ) {
                    if (MainActivity.firstChestDone)
                        obj.setVisibility(View.GONE);

                    signs[signCount] = obj;

                    signCount += 1;

                    setPosition(obj);

                } else if ( object.getName().trim().equals("firstChestCheck") ) {
                    check = obj;

                    if (MainActivity.firstChestDone)
                        check.setVisibility(View.GONE);

                } else if ( object.getName().trim().equals("firstChestBook") ) {
                    book = obj;

                    if (!MainActivity.firstChestDone)
                        book.setVisibility(View.GONE);
                }

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }

    private boolean checkSigns() {
        for (int index = 0; index < needSigns.length; index++) {

            if ( !signs[index].getIcon().trim().equals( needSigns[index] ))
                return false;
        }

        return true;
    }

    private void setPosition(Object obj) {
        for (int x = 0; x < signs.length; x++) {
            signs[x].setY( posY );
            signs[x].setX( posX[0] + posX[1] * x );
        }
    }
}