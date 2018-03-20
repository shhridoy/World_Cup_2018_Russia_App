package com.shhridoy.worldcup2018russia.myUtilities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Dream Land on 3/20/2018.
 */

public class AnimationUtil {

    public static void animate(View view , boolean goesDown){
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", goesDown ? 200 : -200, 0);
        animatorTranslateY.setDuration(1000);

        //ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView,"translationX",-50,50,-30,30,-20,20,-5,5,0);
        //animatorTranslateX.setDuration(1000);

        //animatorSet.playTogether(animatorTranslateX,animatorTranslateY);

        animatorSet.playTogether(animatorTranslateY);
        animatorSet.start();

    }

    public static void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }
}