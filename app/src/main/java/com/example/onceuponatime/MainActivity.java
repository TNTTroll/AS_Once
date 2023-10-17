package com.example.onceuponatime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onceuponatime.Puzzles._PUZZLES;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // <<< Savings
    public static final String saveFile = "Player.txt";
    public static Player player = new Player();

    private Dialog dialog_player;

    public static MainActivity thisContext;

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

        thisContext = this;

        dialog_player = new Dialog(this);

        if (!loadInfo()) {
            try {
                openRegistration(findViewById(R.id.menuMain));
            } catch (IOException ignored) {}
        } else
            Toast.makeText(getApplicationContext(), "Welcome back, " + player.getName(), Toast.LENGTH_LONG).show();

        try {
            getItems();
        } catch (IOException ignored) {}

        Button play = (Button) findViewById(R.id.menuBtnPlay);
        play.setOnClickListener(view -> {
            Intent scene = new Intent(MainActivity.this, Scene.class);
            startActivity(scene);
        });

        Button progress = (Button) findViewById(R.id.menuBtnProgress);
        progress.setOnClickListener(view -> {
            Intent scene = new Intent(MainActivity.this, Progress.class);
            startActivity(scene);
        });

        Button clear = (Button) findViewById(R.id.menuBtnClear);
        clear.setVisibility(View.GONE);
        clear.setOnClickListener(view -> {
            setLevel(1);

            Toast.makeText(getApplicationContext(), "Your progress has been deleted", Toast.LENGTH_LONG).show();

            clear.setText("Done");
        });

        Button sure = (Button) findViewById(R.id.menuBtnAccept);
        sure.setOnClickListener(view -> {
            sure.setVisibility(View.GONE);
            clear.setVisibility(View.VISIBLE);
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

    private void openRegistration(View view) throws IOException {
        dialog_player.setContentView(R.layout.fragment_registration);


        Object captcha = (Object) dialog_player.findViewById(R.id.regCaptcha);

        int capAnswer = new Random().nextInt(3) + 1;

        captcha.setIcon("captcha_" + capAnswer);


        Button dialog_done = (Button) dialog_player.findViewById(R.id.regDone);
        dialog_done.setOnClickListener(v -> {

            EditText getName = dialog_player.findViewById(R.id.regName);
            String playerInfo = "";
            playerInfo += getName.getText().toString();

            EditText getCaptcha = dialog_player.findViewById(R.id.regCaptchaAnswer);

            if (getCaptcha.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "Captcha is empty", Toast.LENGTH_SHORT).show();

            else if (playerInfo.equals(""))
                Toast.makeText(getApplicationContext(), "Name is empty", Toast.LENGTH_SHORT).show();

            else {
                if (Integer.parseInt( getCaptcha.getText().toString() ) != _PUZZLES.registrationAnswers[capAnswer-1]) {
                    Toast.makeText(getApplicationContext(), "Captcha is wrong", Toast.LENGTH_SHORT).show();
                    getCaptcha.setText("");

                } else {
                    try {
                        FileOutputStream outputStream = openFileOutput(saveFile, Context.MODE_PRIVATE);

                        player.setParam(playerInfo, 1 );

                        playerInfo = playerInfo + "&1" + '\n';
                        outputStream.write(playerInfo.getBytes());

                        outputStream.close();

                        dialog_player.dismiss();

                    } catch (IOException ignored) {}
                }
            }

        });

        dialog_player.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_player.show();
    }

    private boolean loadInfo() {

        try {

            InputStream inputS = null;
            inputS = new FileInputStream(getFilesDir() + "/" + saveFile);
            BufferedReader bufferReaderPlayer = new BufferedReader(new InputStreamReader(inputS));

            String[] information_Player = bufferReaderPlayer.readLine().split("&");

            player.setParam(information_Player[0], Integer.parseInt(information_Player[1]) );

            inputS.close();

            return information_Player[0].length() != 0;

        } catch (Exception e) {
            new File(getFilesDir() + "/" + saveFile);

            return false;
        }
    }

    // MainActivity.setLevel(lvl);
    public static void setLevel(int level) {

        List<String> playerAchievements = new ArrayList<String>();

        try {

            InputStream inputS = null;
            inputS = new FileInputStream(thisContext.getFilesDir() + "/" + saveFile);
            BufferedReader bufferReaderPlayer = new BufferedReader(new InputStreamReader(inputS));

            List<String> information_Player = new ArrayList<String>();

            while (bufferReaderPlayer.ready()) { information_Player.add( bufferReaderPlayer.readLine() ); }

            if (information_Player.size() > 1)
                for (int i = 1; i < information_Player.size(); i++)
                    playerAchievements.add( information_Player.get(i) );

            inputS.close();

        } catch (Exception ignored) {}

        FileOutputStream outputStream = null;
        try {
            outputStream = thisContext.openFileOutput(saveFile, Context.MODE_PRIVATE);
        } catch (FileNotFoundException ignored) {}

        try {
            player.setParam(player.getName(), level);

            assert outputStream != null;
            String playerInfo = player.getName() + "&" + level + '\n';
            outputStream.write(playerInfo.getBytes());

            for (String str : playerAchievements)
                outputStream.write((str + '\n').getBytes());

            outputStream.close();
        } catch (IOException ignored) {}

    }

    public static boolean getAchievement(String achieve) {
        List<String> playerAchievements = new ArrayList<String>();

        try {

            InputStream inputS = null;
            inputS = new FileInputStream(thisContext.getFilesDir() + "/" + saveFile);
            BufferedReader bufferReaderPlayer = new BufferedReader(new InputStreamReader(inputS));

            List<String> information_Player = new ArrayList<String>();

            while (bufferReaderPlayer.ready()) { information_Player.add( bufferReaderPlayer.readLine() ); }

            if (information_Player.size() > 1)
                for (int i = 1; i < information_Player.size(); i++)
                    playerAchievements.add( information_Player.get(i) );

            inputS.close();

        } catch (Exception ignored) {}

        return playerAchievements.contains(achieve.trim());
    }

    // MainActivity.setAchievement(_PUZZLES.achievements[index]);
    public static void setAchievement(String achieve) {

        List<String> playerAchievements = new ArrayList<String>();

        try {

            InputStream inputS = null;
            inputS = new FileInputStream(thisContext.getFilesDir() + "/" + saveFile);
            BufferedReader bufferReaderPlayer = new BufferedReader(new InputStreamReader(inputS));

            List<String> information_Player = new ArrayList<String>();

            while (bufferReaderPlayer.ready()) { information_Player.add( bufferReaderPlayer.readLine() ); }

            if (information_Player.size() > 1)
                for (int i = 1; i < information_Player.size(); i++)
                    playerAchievements.add( information_Player.get(i) );

            inputS.close();

        } catch (Exception ignored) {}

        if ( !playerAchievements.contains(achieve.trim()) ) {
            FileOutputStream outputStream = null;
            try {
                outputStream = thisContext.openFileOutput(saveFile, Context.MODE_APPEND);
            } catch (FileNotFoundException ignored) {}

            try {
                assert outputStream != null;

                Toast.makeText(thisContext.getApplicationContext(), "Achievement get: " + achieve, Toast.LENGTH_LONG).show();

                achieve += '\n';
                outputStream.write(achieve.getBytes());

                outputStream.close();

            } catch (IOException ignored) {}

        }
    }
}