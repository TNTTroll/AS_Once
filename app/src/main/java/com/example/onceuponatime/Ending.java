package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.player;
import static com.example.onceuponatime.MainActivity.setLevel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Ending extends AppCompatActivity {

    Object back, bg;
    TextView text;
    int counter = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        back = (Object) findViewById(R.id.endBack);
        back.setOnClickListener(v -> {
            setLevel(1);
            startActivity( new Intent(this, MainActivity.class) );
        });
        back.setVisibility(View.GONE);

        text = (TextView) findViewById(R.id.endText);
        text.setText("Thank you for playing, " + player.getName());
        text.setVisibility(View.GONE);

        bg = (Object) findViewById(R.id.endBG);
        bg.setIcon("ending_0");
        bg.setOnClickListener(v -> {
            counter += 1;
            bg.setIcon("ending_" + counter);

            if (counter == 6) {
                bg.setEnabled(false);
                bg.setIcon("bg_wall");

                back.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
            }
        });

    }
}