package com.example.onceuponatime;

import static com.example.onceuponatime.MainActivity.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class Acception extends DialogFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    public Acception() {
    }

    public static Acception newInstance(String param1, String param2) {
        Acception fragment = new Acception();
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
        if (v.getId() == R.id.acceptBtnYes) {
            Toast.makeText(getActivity(), "Level " + player.getLevel() + ": COMPLETED!", Toast.LENGTH_LONG).show();

            switch (player.getLevel()) {
                case 1:
                    getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo4()).commit();
                    break;

                case 2:
                    getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree4()).commit();
                    break;
            }

            dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_acception, container, false);

        ImageButton bg = (ImageButton) view.findViewById(R.id.acceptBG);
        bg.setEnabled(false);

        ImageButton yes = (ImageButton) view.findViewById(R.id.acceptBtnYes);
        yes.setOnClickListener(this);

        ImageButton no = (ImageButton) view.findViewById(R.id.acceptBtnNo);
        no.setOnClickListener(v -> dismiss());

        return view;
    }
}