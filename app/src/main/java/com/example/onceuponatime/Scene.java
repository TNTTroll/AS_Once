package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.puzzles1;
import static com.example.onceuponatime.MainActivity.puzzles2;
import static com.example.onceuponatime.MainActivity.puzzles3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Scene extends AppCompatActivity implements View.OnClickListener {

    private View roomView;

    @SuppressLint("StaticFieldLeak")
    private static ImageButton[] btn_invs = new ImageButton[5];
    public static int current_Item = -1;

    public static Object[] inventory = new Object[5];

    public static void reloadInventory() {
        for (int x = 0; x < btn_invs.length; x++) {
            btn_invs[x].setImageResource(R.drawable.black);

            if (inventory[x] != null)
                btn_invs[x].setImageDrawable(inventory[x].icon);
        }

        active_item = null;
        selectButton();

    }

    public void setRoomView(String room){
        switch (room) {
            case "1":
                getSupportFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne1()).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        roomView = (View) findViewById(R.id.roomView);

        for (int x = 0; x < btn_invs.length; x++) {
            int resID = getResId("inv_" + (x+1), R.id.class);
            ImageButton btn = (ImageButton) findViewById(resID);
            btn.setBackground(null);
            btn.setOnClickListener(this);

            btn_invs[x] = btn;
        }

    }

    private static ImageButton active_item;
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        for (int x = 0; x < btn_invs.length; x++) {
            ImageButton btn = btn_invs[x];

            if (btn.getId() == v.getId()) {
                if (active_item == btn) {
                    active_item = null;
                    current_Item = -1;
                } else {
                    active_item = btn;
                    current_Item = x;
                }
            }
        }

        selectButton();
    }

    private static void selectButton() {
        for (ImageButton btn : btn_invs)
            btn.setBackground(null);

        if (active_item != null)
            active_item.setBackgroundColor(Color.BLACK);
    }

    // ----- Helpers
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            return -1;
        }
    }

    public static void setPuzzleUsed(String puzzleScene, int room) {

        ArrayList<PuzzleInfo> puzzles;

        if (room == 1) {
            puzzles = puzzles1.get(0);
            puzzles.addAll( puzzles1.get(1) );
            puzzles.addAll( puzzles1.get(2) );
            puzzles.addAll( puzzles1.get(3) );
        } else if (room == 2) {
            puzzles = puzzles2.get(0);
            puzzles.addAll( puzzles2.get(1) );
            puzzles.addAll( puzzles2.get(2) );
            puzzles.addAll( puzzles2.get(3) );
        } else {
            puzzles = puzzles3.get(0);
            puzzles.addAll( puzzles3.get(1) );
            puzzles.addAll( puzzles3.get(2) );
            puzzles.addAll( puzzles3.get(3) );
        }


        for (PuzzleInfo puzzle : puzzles)
            if (puzzle.scene.trim().equals(puzzleScene))
                puzzle.used = true;

    }

}