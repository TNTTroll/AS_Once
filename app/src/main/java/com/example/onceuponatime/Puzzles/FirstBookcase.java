package com.example.onceuponatime.Puzzles;

import static com.example.onceuponatime.MainActivity.holders1;
import static com.example.onceuponatime.MainActivity.objects1;
import static com.example.onceuponatime.Scene.current_Item;
import static com.example.onceuponatime.Scene.getResId;
import static com.example.onceuponatime.Scene.inventory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;

import com.example.onceuponatime.Holder;
import com.example.onceuponatime.HolderInfo;
import com.example.onceuponatime.MainActivity;
import com.example.onceuponatime.Object;
import com.example.onceuponatime.ObjectInfo;
import com.example.onceuponatime.R;
import com.example.onceuponatime.RoomOne2;
import com.example.onceuponatime.Scene;

public class FirstBookcase extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object solved, bg;
    Holder bookPlace;

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

        if (v.getId() == R.id.firstBookcaseBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne2()).addToBackStack(null).commit();

        if (MainActivity.firstBooksPlaced)
            for (ObjectInfo book : objects1.get(1)) {
                int resID = getResId(book.getName(), R.id.class);

                if ( resID == v.getId() && book.getName().startsWith("firstBook_") ) {
                    Object obj = (Object) view.findViewById(resID);

                    int currentBook = Integer.parseInt( "" + obj.getName().charAt(obj.getName().length()-1) ) - 1;

                    if (activeBook == -1)
                        activeBook = currentBook;

                    else {
                        Object swap = books[currentBook];
                        books[currentBook] = books[activeBook];
                        books[activeBook] = swap;

                        books[activeBook].setName("firstBook_" + (activeBook + 1));
                        books[currentBook].setName("firstBook_" + (currentBook + 1));

                        int swapInt = usedBook[currentBook];
                        usedBook[currentBook] = usedBook[activeBook];
                        usedBook[activeBook] = swapInt;

                        activeBook = -1;

                        redrawBooks();
                    }

                    break;
                }
            }

        for (HolderInfo holder : holders1.get(1)) {
            int resID = getResId(holder.getName(), R.id.class);

            if (resID == v.getId()) {
                Holder hold = (Holder) view.findViewById(resID);

                if (current_Item != -1 && inventory[current_Item] != null)
                    if (hold.setItem(inventory[current_Item])) {
                        inventory[current_Item] = null;
                        current_Item = -1;

                        holder.setUsed(true);

                        hold.setVisibility(View.GONE);
                        books[0].setVisibility(View.VISIBLE);

                        MainActivity.firstBooksPlaced = true;
                    }
            }
        }

        if (checkBooks()) {
            MainActivity.firstBooksDone = true;

            for (Object book : books)
                book.setEnabled(false);

            solved.setIcon("bookcase_open");
        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_bookcase, container, false);

        bg = (Object) view.findViewById(R.id.firstBookcaseBG);
        bg.setEnabled(false);

        int bookPos = 0;
        for (ObjectInfo object : objects1.get(1)) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), object.getIcon());
                obj.setOnClickListener(this);

                if ( object.getName().startsWith("firstBook_") ) {
                    books[bookPos] = obj;

                    usedBook[bookPos] = bookPos + 1;

                    bookPos += 1;

                } else if ( object.getName().trim().equals("firstBookSolved") ) {
                    solved = obj;

                    if (MainActivity.firstBooksDone)
                        solved.setIcon("bookcase_open");
                }

            }
            catch(NullPointerException ignored) {}
        }

        if (!MainActivity.firstBooksPlaced)
            books[0].setVisibility(View.GONE);

        for (HolderInfo holder : holders1.get(1)) {
            try {
                int resID = getResId(holder.getName(), R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.getName(), holder.getNeed(), holder.getIcon());
                hold.setOnClickListener(this);

                if (MainActivity.firstBooksPlaced)
                    hold.setVisibility(View.GONE);

                if ( holder.getName().trim().equals("firstBookPlacement") )
                    bookPlace = hold;

            }
            catch(NullPointerException ignored) {}
        }

        redrawBooks();

        return view;
    }

    private void redrawBooks() {
        if (!MainActivity.firstBooksDone) {
            bookPlace.setY(posY);
            bookPlace.setX(posX[0]);

            for (int x = 0; x < books.length; x++) {
                books[x].setY(posY);
                books[x].setX(posX[0] + posX[1] * x);
            }

        } else
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