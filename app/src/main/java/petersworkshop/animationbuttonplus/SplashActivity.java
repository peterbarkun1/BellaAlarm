package petersworkshop.animationbuttonplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView iv = (ImageView) findViewById(R.id.iv1);
        final ImageView iv2 = (ImageView) findViewById(R.id.iv2);

        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
        final Animation an3 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.scale);

        iv2.startAnimation(an3);
        iv.startAnimation(an);

        // анимация круга

        an3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv2.startAnimation(an3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // Анимация будильника

        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2);
                finish();
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
