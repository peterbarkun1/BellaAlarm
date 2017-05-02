package petersworkshop.animationbuttonplus;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    //обявление переменных для анимации меню
    FloatingActionButton fab_inst,fab_twi,fab_vel, fab_inf;
    Animation FabOpen,FabClose,FabRotateClockwise,FabAntiRotateClockwise;
    boolean isOpen = false;
    Dialog dialog;

    //функции для фб
  // private final List<String> fb_permissions = new ArrayList<>(Arrays.asList("public_profile"));
    //private CallbackManager callbackManager; // facebook callback

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        //переход на скролл_активити

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


        //переход к графику
        public void onClickButtDial (View view) {
            //___________________________






            //__________________________

            //создание диалога
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mViev = getLayoutInflater().inflate(R.layout.dialog_layout,null);
            final EditText mBirthday = (EditText) mViev.findViewById(R.id.etBirthday);
            Button input = (Button) mViev.findViewById(R.id.btnInput);

            mBuilder.setView(mViev);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!mBirthday.getText().toString().isEmpty())
                    {
                        dialog.cancel();
                    //Toast.makeText(MainActivity.this, R.string.inputSuccessful,Toast.LENGTH_SHORT).show();

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        intent.putExtra(Constants.KEY_ANIM_TYPE, Constants.TransitionType.ExplodeJava);
                        startActivity(intent, options.toBundle());

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, R.string.err_msg,Toast.LENGTH_SHORT).show();
                    }
                    // создаем файл и записывем информацию
                    try {
                        FileOutputStream fileOutputStream = openFileOutput("Birthday.txt", MODE_PRIVATE);
                        OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);
                        outputWriter.write(mBirthday.getText().toString());
                        outputWriter.close();

                        // создаем всплывающее окно c результатом выволнения записи в файл
                        // Toast.makeText(getBaseContext(), mBirthday.getText(),Toast.LENGTH_LONG).show();
                        // выводим полный путь расположения файла
                        Toast.makeText(getBaseContext(), getFilesDir().getAbsolutePath(),Toast.LENGTH_LONG).show();


                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }

        //переход к информации
        public void onClick1(View view) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            intent.putExtra(Constants.KEY_ANIM_TYPE, Constants.TransitionType.ExplodeJava);
            startActivity(intent, options.toBundle());
        }

        //переход к будильнику
        public void onClick4(View view) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(MainActivity.this,AlarmActivity.class);
            intent.putExtra(Constants.KEY_ANIM_TYPE, Constants.TransitionType.ExplodeJava);
            startActivity(intent, options.toBundle());
        }

         //public void Login (View v) {
         //      LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, fb_permissions);
       // }

}

