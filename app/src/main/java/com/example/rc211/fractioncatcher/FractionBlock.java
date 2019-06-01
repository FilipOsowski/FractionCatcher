package com.example.rc211.fractioncatcher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewManager;
import android.widget.TextView;

import java.util.ArrayList;

public class FractionBlock extends ConstraintLayout {
    final static int width = 75;
    final static int height = 175;
    float speed = (float)3.5;
    final int delay = 10;
    final static int SCREENHEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    int id;
    Handler handler = new Handler();
    Runnable runnable;
    ArrayList<PlayerBlock> playerBlocks;
    RandomFraction randomFraction;
    TextView numerator;
    TextView denominator;

    public FractionBlock(Context context, ArrayList<PlayerBlock> playerBlocks) {
        super(context);

        this.setY(-height);
        float xPos = (float) (Math.random() * (Resources.getSystem().getDisplayMetrics().widthPixels - 3 * width - 2 * PlayerBlock.width)) + width + PlayerBlock.width;
        this.setX(xPos);
        this.setLayoutParams(new ConstraintLayout.LayoutParams(width, height));

        randomFraction = new RandomFraction();

        numerator = new TextView(context);
        numerator.setText("" + randomFraction.getNumerator());
        numerator.setGravity(Gravity.CENTER);
        numerator.setBackgroundColor(Color.rgb(225, 229, 238));
        numerator.setTypeface(Typeface.DEFAULT_BOLD);
        numerator.setTextSize(19);
        numerator.setHeight(height/2);
        numerator.setWidth(width);
        this.addView(numerator);

        denominator = new TextView(context);
        denominator.setText("" + randomFraction.getDenominator());
        denominator.setGravity(Gravity.CENTER);
        denominator.setBackgroundColor(Color.rgb(225, 229, 238));
        denominator.setTypeface(Typeface.DEFAULT_BOLD);
        denominator.setTextSize(19);
        denominator.setHeight(height/2);
        denominator.setWidth(width);
        denominator.setY(height/2);
        this.addView(denominator);

        View fractionBar = new View(context);
        fractionBar.setBackgroundColor(Color.BLACK);
        fractionBar.setLayoutParams(new LayoutParams(width, (int) (height * 0.05)));
        fractionBar.setY((float) (height * 0.5));
        this.addView(fractionBar);

        this.id = id;
        this.playerBlocks = playerBlocks;

        runnable = new Runnable() {
            @Override
            public void run() {
                update();
            }
        };
        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.RED);
        super.onDraw(canvas);
    }

    public boolean inPlayerBlock() {
        for(PlayerBlock playerBlock : playerBlocks) {
            if (this.getY() + height >= playerBlock.getY() && this.getY() <= playerBlock.getY() + playerBlock.getHeight()) {
                if (this.getX() + width >= playerBlock.getX() && this.getX() <= playerBlock.getX() + playerBlock.getWidth()) {
                    playerBlock.onCollision(this);
                    return true;
                }
            }
        }
        return false;
    }

    public void kill() {
        ((GameActivity) getContext()).activeFractionBlocks.remove(this);
        handler.removeCallbacks(runnable);
        ViewManager viewManager = (ViewManager) this.getParent();
        viewManager.removeView(this);
    }

    private void update() {
        this.setY(this.getY() + speed);

        if (inPlayerBlock()) {
            kill();
        }
        else if (this.getY() > SCREENHEIGHT) {
            ((GameActivity) getContext()).updateScore(-10);
            kill();
        }
        else {
            handler.postDelayed(runnable, delay);
        }
    }
}