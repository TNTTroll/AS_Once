package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.objects1;
import static com.example.onceuponatime.Scene.getResId;
import static com.example.onceuponatime.Scene.setPuzzleUsed;

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
import com.example.onceuponatime.RoomOne2;

import java.lang.reflect.Array;

public class FirstBookcase extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back;
    int activeBook = -1;

    int[] posX = _PUZZLES.firstBooksPosX;
    int posY = _PUZZLES.firstBooksPosY;

    int[] needBook = _PUZZLES.firstBooksSequence;
    int[] usedBook = new int[needBook.length];
    Object[] books = new Object[needBook.length];

    public FirstBookcase() {
    }

    public static FirstBookcase newInstance(String param1, String param2) {
        FirstBookcase fragment = new FirstBookcase();
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

        for (ObjectInfo book : objects1.get(1)) {
            int resID = getResId(book.getName(), R.id.class);

            if (resID == v.getId()) {
                Object obj = (Object) view.findViewById(resID);

                int currentBook = Integer.parseInt( "" + obj.getName().charAt(obj.getName().length()-1) ) - 1;

                if (activeBook == -1)
                    activeBook = currentBook;

                else {
                    Object swap = books[currentBook];
                    books[currentBook] = books[activeBook];
                    books[activeBook] = swap;

                    books[activeBook].setName("firstBook" + (activeBook + 1));
                    books[currentBook].setName("firstBook" + (currentBook + 1));

                    int swapInt = usedBook[currentBook];
                    usedBook[currentBook] = usedBook[activeBook];
                    usedBook[activeBook] = swapInt;

                    activeBook = -1;

                    redrawBooks();
                }

                break;
            }
        }

        if (checkBooks()) {
            MainActivity.firstBooksDone = true;

            for (Object book : books)
                book.setEnabled(false);
        }

        switch (v.getId()) {
            case (R.id.firstBookcaseBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne2()).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_bookcase, container, false);

        back = (Object) view.findViewById(R.id.firstBookcaseBack);
        back.setOnClickListener(this);

        int bookPos = 0;
        for (ObjectInfo object : objects1.get(1)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                if ( object.getName().startsWith("firstBook") ) {
                    obj.setParam(object.getName(), object.getIcon());
                    obj.setOnClickListener(this);

                    books[bookPos] = obj;

                    usedBook[bookPos] = bookPos + 1;

                    bookPos += 1;
                }

            }
            catch(NullPointerException ignored) {}
        }

        redrawBooks();

        return view;
    }

    private void redrawBooks() {
        if (!MainActivity.firstBooksDone)
            for (int x = 0; x < books.length; x++) {
                books[x].setY( posY );
                books[x].setX( posX[0] + posX[1] * x  );
            }

        else
            for (int x = 0; x < books.length; x++) {
                books[x].setEnabled(false);

                books[x].setY( posY );
                books[x].setX( posX[0] + posX[1] * (needBook[x] - 1) );
            }
    }

    private boolean checkBooks() {
        for (int x = 0; x < usedBook.length; x++)
            if (usedBook[x] != needBook[x])
                return false;

        return true;
    }
}