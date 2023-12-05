package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.holders3;
import static com.example.onceuponatime.MainActivity.objects3;
import static com.example.onceuponatime.Scene.current_Item;
import static com.example.onceuponatime.Scene.getResId;
import static com.example.onceuponatime.Scene.inventory;

import android.graphics.Point;
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
import com.example.onceuponatime.RoomThree2;
import com.example.onceuponatime.RoomThree4;
import com.example.onceuponatime.Scene;

import java.util.Arrays;

public class ThirdEasel extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object bg;

    Holder palette;

    String needCup = "thirdCups_1";
    int needTap = 1;

    boolean[] needPixels = _PUZZLES.thirdEaselSequence;

    Object[] pixels = new Object[28];
    boolean[] pixelColored = new boolean[pixels.length];

    public ThirdEasel() {
    }

    public static ThirdEasel newInstance(String param1, String param2) {
        ThirdEasel fragment = new ThirdEasel();
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
            case (R.id.thirdEaselBack):
                MainActivity.thirdEaselPixelColored = pixelColored;
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree2()).addToBackStack(null).commit();
                break;

            case (R.id.thirdEaselPalette):
                if (current_Item != -1 && inventory[current_Item] != null) {

                    if ( inventory[current_Item].getName().trim().equals(needCup) && MainActivity.thirdCupsTookTap == needTap ) {
                        MainActivity.thirdEaselPaletteDone = true;

                        palette.setVisibility(View.GONE);

                        for (Object pixel : pixels)
                            pixel.setVisibility(View.VISIBLE);
                    }

                    Arrays.fill(MainActivity.thirdCupsTook, false);

                    inventory[current_Item] = null;
                    current_Item = -1;

                    MainActivity.thirdCupsTookTap = -1;
                }

                break;
        }

        if (MainActivity.thirdEaselPaletteDone)
            for (int index = 1; index <= pixels.length; index++) {
                int resID = getResId("thirdEasel_" + index, R.id.class);

                if (resID == v.getId()) {
                    Object pixel = (Object) view.findViewById(resID);

                    if (pixelColored[index - 1])
                        pixel.setIcon("none");
                    else
                        pixel.setIcon("yes");

                    pixelColored[index - 1] = !pixelColored[index - 1];

                    break;
                }
            }

        if (!MainActivity.thirdEaselDone) {
            if (checkEasel()) {
                MainActivity.thirdEaselDone = true;

                for (Object pixel : pixels)
                    pixel.setVisibility(View.GONE);

                bg.setIcon("bg_easel_2");

            } else if (!MainActivity.getAchievement(8))
                if (achieveEasel())
                    MainActivity.setAchievement(8);
        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_easel, container, false);

        bg = (Object) view.findViewById(R.id.thirdEaselBG);
        bg.setEnabled(false);

        if (MainActivity.thirdEaselDone)
            bg.setIcon("bg_easel_2");

        Object back = (Object) view.findViewById(R.id.thirdEaselBack);
        back.setOnClickListener(this);

        pixelColored = MainActivity.thirdEaselPixelColored;

        for (int index = 1; index <= pixels.length; index++) {
            try {
                int resID = getResId("thirdEasel_" + index, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam("thirdEasel_" + index, "none");
                obj.setOnClickListener(this);

                if ( obj.getName().trim().startsWith("thirdEasel_")  ) {

                    if (!MainActivity.thirdEaselPaletteDone || MainActivity.thirdEaselDone)
                        obj.setVisibility(View.GONE);

                    pixels[index - 1] = obj;

                    if (pixelColored[index - 1])
                        obj.setIcon("yes");
                }

                setPosition(obj);

            }
            catch(NullPointerException ignored) {}
        }

        for (HolderInfo holder : holders3.get(1)) {
            try {
                int resID = getResId(holder.getName(), R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.getName(), holder.getNeed(), holder.getIcon());
                hold.setOnClickListener(this);

                if (hold.getName().trim().equals("thirdEaselPalette")) {
                    palette = hold;

                    if (MainActivity.thirdEaselPaletteDone)
                        hold.setVisibility(View.GONE);
                }
            }
            catch(NullPointerException ignored) {}
        }

        palette.setVisibility(View.GONE);

        return view;
    }

    private boolean checkEasel() {
        for (int index = 0; index < pixels.length; index++)
            if ( pixelColored[index] != needPixels[index] )
                return false;

        return true;
    }

    private boolean achieveEasel() {
        for (int index = 0; index < pixels.length; index++)
            if (!pixelColored[index])
                return false;

        return true;
    }

    private void setPosition(Object obj) {

        Point size = new Point();
        MainActivity.display.getSize(size);

        double width = size.x * 0.15;
        double height = size.y * 0.05;

        obj.setX( (int) width );
        obj.setY( (int) height );
    }
}