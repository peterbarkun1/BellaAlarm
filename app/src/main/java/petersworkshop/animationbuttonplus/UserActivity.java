package petersworkshop.animationbuttonplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class UserActivity extends AppCompatActivity {

    Constants.TransitionType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdata);

        //инициализация функций анимации
        //__________________________________________________________________________________________
        initPage();
        initAnimation();
    }

    private void initPage() {
        type = (Constants.TransitionType) getIntent().getSerializableExtra(Constants.KEY_ANIM_TYPE);
        getSupportActionBar();

    }
    @Override
    public boolean onSupportNavigateUp(){
        finishAfterTransition();
        return true;
    }

    private void initAnimation() {
        switch (type){
            case ExplodeJava:{
                Explode enterTransition = new Explode();
                enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                getWindow().setEnterTransition(enterTransition);
                break;
            }
        }
    }
    //______________________________________________________________________________________________

    //запись в файл посредствам нажатий на кнопки
    public void good (View view) {
        try {
            double good = 0.5;
            FileOutputStream fileOutputStream = openFileOutput("Moods.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);
            outputWriter.write(String.valueOf(good));
            outputWriter.close();

            // создаем всплывающее окно c результатом выволнения записи в файл
            // Toast.makeText(getBaseContext(), mBirthday.getText(),Toast.LENGTH_LONG).show();
            // выводим полный путь расположения файла
            //Toast.makeText(getBaseContext(), getFilesDir().getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this,"Good mood? Ok)",Toast.LENGTH_LONG).show();
    }

    public void poker (View view) {
        try {
            double poker = 0;
            FileOutputStream fileOutputStream = openFileOutput("Moods.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);
            outputWriter.write(String.valueOf(poker));
            outputWriter.close();

            // создаем всплывающее окно c результатом выволнения записи в файл
            // Toast.makeText(getBaseContext(), mBirthday.getText(),Toast.LENGTH_LONG).show();
            // выводим полный путь расположения файла
            //Toast.makeText(getBaseContext(), getFilesDir().getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this,"Poker face? Ok",Toast.LENGTH_LONG).show();
    }

    public void sad (View view) {
        try {
            double sad = -0.5;
            FileOutputStream fileOutputStream = openFileOutput("Moods.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);
            outputWriter.write(String.valueOf(sad));
            outputWriter.close();

            // создаем всплывающее окно c результатом выволнения записи в файл
            // Toast.makeText(getBaseContext(), mBirthday.getText(),Toast.LENGTH_LONG).show();
            // выводим полный путь расположения файла
            //Toast.makeText(getBaseContext(), getFilesDir().getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this,"sad? Don't worry)",Toast.LENGTH_LONG).show();
    }

    public void tot (View view) {
        try {
            double tot = -1;
            FileOutputStream fileOutputStream = openFileOutput("Moods.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);
            outputWriter.write(String.valueOf(tot));
            outputWriter.close();

            // создаем всплывающее окно c результатом выволнения записи в файл
            // Toast.makeText(getBaseContext(), mBirthday.getText(),Toast.LENGTH_LONG).show();
            // выводим полный путь расположения файла
            //Toast.makeText(getBaseContext(), getFilesDir().getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this,"You are dead?",Toast.LENGTH_LONG).show();
    }
    //______________________________________________________________________________________________

}