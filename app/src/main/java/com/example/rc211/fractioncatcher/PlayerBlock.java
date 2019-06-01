package com.example.rc211.fractioncatcher;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import android.widget.ImageView;import android.view.View;

public class PlayerBlock extends android.support.v7.widget.AppCompatImageView {
    final static int width = 100;
    final static int height = 100;
    float role = 0;
    Intent gameOverIntent;
    Bundle transitionBundle;
    GameActivity parentGameActivity;

    public PlayerBlock(Context context) {
        super(context);
        this.setLayoutParams(new ConstraintLayout.LayoutParams(width, height));

        parentGameActivity = ((GameActivity) this.getContext());
    }


    public void onCollision(FractionBlock fractionBlock) {
        if (role == 1) {
            greaterBlockActivity(fractionBlock);
        }
        else {
            lesserBlockActivity(fractionBlock);
        }
    }

    public void onCorrect() {
        parentGameActivity.updateScore(10);
    }

    public void onIncorrect(FractionBlock fractionBlock, String condition) {
        RandomFraction fraction = fractionBlock.randomFraction;
        parentGameActivity.endGameActivity(fraction.getNumerator() + "/" + fraction.getDenominator(),
                String.format("%.3f", fraction.getDecimal()),
                condition);

    }

    public void greaterBlockActivity(FractionBlock fractionBlock) {
        if (fractionBlock.randomFraction.getDecimal() > 1.5) {
            //debugTextView.setText("CORRECT");
            onCorrect();

        }
        else {
            //debugTextView.setText("INCORRECT");
            onIncorrect(fractionBlock, "is greater than");
        }
    }

    public void lesserBlockActivity(FractionBlock fractionBlock) {
        if (fractionBlock.randomFraction.getDecimal() < 1.5) {
            //debugTextView.setText("CORRECT");
            onCorrect();
        }
        else {
            //debugTextView.setText("INCORRECT");
            onIncorrect(fractionBlock, "is less than");
        }
    }
}
