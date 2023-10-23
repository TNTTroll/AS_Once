package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.player;
import static com.example.onceuponatime.MainActivity.puzzles1;
import static com.example.onceuponatime.MainActivity.puzzles2;
import static com.example.onceuponatime.MainActivity.puzzles3;
import static com.example.onceuponatime.MainActivity.thisContext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Scene extends AppCompatActivity implements View.OnClickListener {

    private View roomView;

    public static Dialog dialog_texting;

    @SuppressLint("StaticFieldLeak")
    private static ImageButton[] btn_invs = new ImageButton[4];
    public static int current_Item = -1;

    public static Object[] inventory = new Object[4];

    public static void reloadInventory() {
        for (int x = 0; x < btn_invs.length; x++) {
            btn_invs[x].setImageResource(R.drawable.black);

            if (inventory[x] != null)
                btn_invs[x].setImageDrawable(inventory[x].icon);
        }

        active_item = null;
        selectButton();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        roomView = (View) findViewById(R.id.roomView);

        dialog_texting = new Dialog(this);

        ImageButton bg = (ImageButton) findViewById(R.id.sceneBG);
        bg.setEnabled(false);

        switch (player.getLevel()) {
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne4()).commit();
                break;

            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo4()).commit();
                break;

            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree4()).commit();
                break;
        }

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

    public static void showText(String text) {
        dialog_texting.setContentView(R.layout.fragment_texting);

        Object back = (Object) dialog_texting.findViewById(R.id.textingBack);
        back.setOnClickListener(v -> dialog_texting.dismiss());

        TextView lor = (TextView) dialog_texting.findViewById(R.id.textingText);
        lor.setText(text);

        dialog_texting.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_texting.show();
    }
}