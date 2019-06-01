/*
package com.example.rc211.fractioncatcher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;

public class PlayerControls extends ConstraintLayout {
    float lastPosition = 0;
    public PlayerBlock lesserBlock;
    public PlayerBlock greaterBlock;

    public PlayerControls(Context context) {
        super(context);

        lesserBlock = new PlayerBlock(context);
        this.addView(lesserBlock);
        lesserBlock.setX(200);

        greaterBlock = new PlayerBlock(context);
        this.addView(greaterBlock);
        greaterBlock.setX(0);

        this.setLayoutParams(new LayoutParams(PlayerBlock.width * 3, PlayerBlock.height));

        this.setX(Resources.getSystem().getDisplayMetrics().widthPixels/2 - PlayerBlock.width);
        this.setY((float) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.75));
    }

    public void move(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            lastPosition = motionEvent.getX();
        }
        else {
            float offset = motionEvent.getX() - lastPosition;
            this.setX(this.getX() + offset);
            lastPosition = motionEvent.getX();
        }
    }
}
*/
