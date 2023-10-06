package com.example.onceuponatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onceuponatime.Puzzles._PUZZLES;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // <<< Scene 1
    public static ArrayList<ArrayList<HolderInfo>> holders1 = new ArrayList<>();
    public static ArrayList<ArrayList<ObjectInfo>> objects1 = new ArrayList<>();
    public static ArrayList<ArrayList<PuzzleInfo>> puzzles1 = new ArrayList<>();

    // <<< Scene 2
    public static ArrayList<ArrayList<HolderInfo>> holders2 = new ArrayList<>();
    public static ArrayList<ArrayList<ObjectInfo>> objects2 = new ArrayList<>();
    public static ArrayList<ArrayList<PuzzleInfo>> puzzles2 = new ArrayList<>();

    // <<< Scene 3
    public static ArrayList<ArrayList<HolderInfo>> holders3 = new ArrayList<>();
    public static ArrayList<ArrayList<ObjectInfo>> objects3 = new ArrayList<>();
    public static ArrayList<ArrayList<PuzzleInfo>> puzzles3 = new ArrayList<>();

    // <<< Additional
    public static boolean firstWindowsDone = false;
    public static boolean firstTookBook = false;

    public static boolean firstPlatesDone = false;
    public static boolean firstTookHourArrow = false;

    public static boolean firstBooksPlaced = false;
    public static boolean firstBooksDone = false;

    public static boolean firstWindowOpen = false;
    public static boolean firstTookMinuteArrow = false;
    public static boolean firstClosetDone = false;

    public static boolean firstChestDone = false;

    public static boolean firstHourArrowPlaced = false;
    public static boolean firstMinuteArrowPlaced = false;

    // <<< Birds
    public static boolean firstBird1Saw = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            getItems();
        } catch (IOException ignored) {}

        Button play = (Button) findViewById(R.id.menu_btn_play);
        play.setOnClickListener(view -> {
            Intent scene = new Intent(MainActivity.this, Scene.class);
            startActivity(scene);
        });
    }

    private void getItems() throws IOException {

        String text = "";

        try {
            InputStream file = getAssets().open("Items.txt");
            byte[] buffer = new byte[file.available()];

            file.read(buffer);

            text = new String(buffer);
            String[] words = text.split("\n");

            ArrayList<ObjectInfo> bufObj = new ArrayList<>();
            ArrayList<HolderInfo> bufHold = new ArrayList<>();
            ArrayList<PuzzleInfo> bufPuzz = new ArrayList<>();

            int count = 0;
            int isObjects = 1;
            for (int i = 0; i < words.length; i++) {

                String word = words[i].trim();

                if ( word.equals("") ) {}

                else if ( word.equals("=scene") )
                    count += 1;
                else if ( word.equals("-objects") )
                    isObjects = 1;
                else if ( word.equals("-holders") )
                    isObjects = 2;
                else if ( word.equals("-puzzles") )
                    isObjects = 3;
                else if ( word.equals("^side") ) {
                    switch (count) {
                        case 1:
                            if (isObjects == 1)      objects1.add(bufObj);
                            else if (isObjects == 2) holders1.add(bufHold);
                            else                     puzzles1.add(bufPuzz);
                            break;
                        case 2:
                            if (isObjects == 1)      objects2.add(bufObj);
                            else if (isObjects == 2) holders2.add(bufHold);
                            else                     puzzles2.add(bufPuzz);
                            break;
                        case 3:
                            if (isObjects == 1)      objects3.add(bufObj);
                            else if (isObjects == 2) holders3.add(bufHold);
                            else                     puzzles3.add(bufPuzz);
                            break;
                    }

                    bufObj = new ArrayList<>();
                    bufHold = new ArrayList<>();
                    bufPuzz = new ArrayList<>();
                }

                else {

                    String[] parameter = word.split("//");
                    if (isObjects == 1)
                        bufObj.add(new ObjectInfo(parameter[0], parameter[1]));
                    else if
                        (isObjects == 2) bufHold.add(new HolderInfo(parameter[0], parameter[1], parameter[2]));
                    else
                        bufPuzz.add(new PuzzleInfo(parameter[0], parameter[1], parameter[2]));
                }

            }

            file.close();

        } catch (IOException ignored) {}

    }

}