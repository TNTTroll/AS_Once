package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.Scene.getResId;

import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onceuponatime.MainActivity;
import com.example.onceuponatime.Object;
import com.example.onceuponatime.R;
import com.example.onceuponatime.RoomThree3;
import com.example.onceuponatime.Scene;

import java.util.Random;

public class ThirdPaint extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object arrow;

    int paintCount = 16;

    int[] paintAngle = new int[paintCount];
    int[] paintAngleCorrect = {1, 0, 0, 0,
                              0, 1, 0, 0,
                              0, 0, 1, 0,
                              0, 0, 0, 1 };

    public ThirdPaint() {
    }

    public static ThirdPaint newInstance(String param1, String param2) {
        ThirdPaint fragment = new ThirdPaint();
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
        if (v.getId() == R.id.thirdPaintBack) {
            MainActivity.thirdPaintsAngles = paintAngle;
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree3()).addToBackStack(null).commit();

        } else if (v.getId() == R.id.thirdMinuteArrow) {
            if (arrow.setToInventory()) {
                arrow.setVisibility(View.GONE);
                MainActivity.thirdTookMinuteArrow = true;

            }
        }

        for (int index = 1; index <= paintCount; index++) {
            int resID = getResId("thirdPaint_" + index, R.id.class);

            if (resID == v.getId()) {
                Object paint = (Object) view.findViewById(resID);

                paint.setRotation((paintAngle[index - 1] + 1) * 90);
                paintAngle[index - 1] = (paintAngle[index - 1] + 1) % 4;

                break;
            }
        }

        if (!MainActivity.thirdPaintDone)
            if (checkPaints()) {
                MainActivity.thirdPaintDone = true;

                for (int index = 1; index <= paintCount; index++) {
                    Object paint = (Object) view.findViewById(getResId("thirdPaint_" + index, R.id.class));

                    paint.setVisibility(View.GONE);
                }

                arrow.setVisibility(View.VISIBLE);
            }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_paint, container, false);

        Object bg = (Object) view.findViewById(R.id.thirdPaintBG);
        bg.setEnabled(false);

        arrow = (Object) view.findViewById(R.id.thirdMinuteArrow);
        arrow.setParam("thirdMinuteArrow", "arrow_minute");
        arrow.setOnClickListener(this);

        if (!MainActivity.thirdPaintDone || MainActivity.thirdTookMinuteArrow)
            arrow.setVisibility(View.GONE);

        Object back = (Object) view.findViewById(R.id.thirdPaintBack);
        back.setOnClickListener(this);

        paintAngle = MainActivity.thirdPaintsAngles;

        boolean flag = true;
        for (int a : paintAngle)
            if (a != 0) {
                flag = false;
                break;
            }

        if (flag)
            generatePaints();

        for (int index = 1; index <= paintCount; index++) {
            Object paint = (Object) view.findViewById(getResId("thirdPaint_" + index, R.id.class));

            paint.setParam("thirdPaint_" + index, "crystal");
            paint.setOnClickListener(this);

            setPosition(paint);
            paint.setRotation((paintAngle[index - 1]) * 90);

            if (MainActivity.thirdPaintDone)
                paint.setEnabled(false);
        }

        return view;
    }

    private void generatePaints() {
        Random random = new Random();
        for (int index = 0; index < paintCount; index++)
            paintAngle[index] = random.nextInt(4);
    }

    private boolean checkPaints() {
        for (int x = 0; x < paintAngle.length; x++) {
            if (paintAngle[x] != paintAngleCorrect[x])
                return false;
        }

        return true;
    }

    private void setPosition(Object obj) {

        Point size = new Point();
        MainActivity.display.getSize(size);

        double width = size.x * 0.2;
        double height = size.y * 0.13;

        obj.setX( (int) width );
        obj.setY( (int) height );
    }
}