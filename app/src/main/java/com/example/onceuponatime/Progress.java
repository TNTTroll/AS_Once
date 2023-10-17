
package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Progress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        Object back = (Object) findViewById(R.id.progressBack);
        back.setOnClickListener(v -> {
            startActivity( new Intent(this, MainActivity.class) );
        });

        TextView text = (TextView) findViewById(R.id.progressText);
        text.setText("Hello, " + player.getName());
    }
}