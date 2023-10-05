package com.example.onceuponatime;

import static com.example.onceuponatime.Scene.getResId;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.content.res.ResourcesCompat;

public class Puzzle extends androidx.appcompat.widget.AppCompatButton {
    String name;
    String scene;

    public Puzzle(Context _context) {
        super(_context);
    }

    public Puzzle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Puzzle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setParam(String _name, String _scene, String _icon) {
        name = _name;
        scene = _scene;
        this.setBackground( ResourcesCompat.getDrawable(getResources(), getResId(_icon, R.drawable.class), null) );
    }

}
