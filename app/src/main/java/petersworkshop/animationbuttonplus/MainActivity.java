package petersworkshop.animationbuttonplus;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //обявление переменных для анимации меню
    FloatingActionButton fab_inst,fab_twi,fab_vel, fab_inf;
    Animation FabOpen,FabClose,FabRotateClockwise,FabAntiRotateClockwise;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //нахождение элементов на xml файле

        findViewById(R.id.bellaAlarmText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                ActivityOptionsCompat optionCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,findViewById(R.id.bellaAlarmText),"MyText");

            startActivity(intent, optionCompat.toBundle());
            }
        });



        //для анимации меню
        fab_inst = (FloatingActionButton)findViewById(R.id.fab_inst);
        fab_twi = (FloatingActionButton)findViewById(R.id.fab_twi);
        fab_vel = (FloatingActionButton)findViewById(R.id.fab_vel);
        fab_inf = (FloatingActionButton)findViewById(R.id.fab_inf);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        FabAntiRotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);
        //условия работы анимации
        fab_inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen)
                {
                    fab_vel.startAnimation(FabClose);
                    fab_twi.startAnimation(FabClose);
                    fab_inf.startAnimation(FabClose);
                    fab_inst.startAnimation(FabAntiRotateClockwise);
                    fab_twi.setClickable(false);
                    fab_vel.setClickable(false);
                    fab_inf.setClickable(false);
                    isOpen = false;
                }
                else
                {
                    fab_vel.startAnimation(FabOpen);
                    fab_twi.startAnimation(FabOpen);
                    fab_inf.startAnimation(FabOpen);
                    fab_inst.startAnimation(FabRotateClockwise);
                    fab_twi.setClickable(true);
                    fab_vel.setClickable(true);
                    fab_inf.setClickable(true);
                    isOpen = true;
                }
            }
        });
    }
        //обработчики кнопок меню
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
        }
        public void onClick1(View view) {
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        }
        public void onClick4(View view) {
        Intent intent = new Intent(MainActivity.this,alarm_Activity.class);
        startActivity(intent);

    }
}

