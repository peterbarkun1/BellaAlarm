package petersworkshop.animationbuttonplus;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;

public class AboutActivity extends AppCompatActivity {

    Constants.TransitionType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initPage();
        initAnimation();
    }

    private void initPage() {
        type = (Constants.TransitionType) getIntent().getSerializableExtra(Constants.KEY_ANIM_TYPE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
//__________________________________________________________________________________________________
    //отправка на почту c использованием AsyncTask
    MyTask mt;

    public void send (View view)
    {
        mt = new MyTask();//ссыль на класс
        mt.execute();// исполнение
}
    //Работает)

    class MyTask extends AsyncTask<Void, Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params)// метод для создания отдельного потока, не имеет доступ в UI поток (#этоважно)
        {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_SUBJECT, "Bella alarm");
            email.putExtra(Intent.EXTRA_TEXT, "Bella makes every morning nice, I wake up rested and relaxed, try it yourself    https://play.google.com/store/apps/details?id=com.petersworksop.Bellaalarm.pro&hl=ru");

            //для того чтобы запросить email клиент устанавливаем тип
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose email client :"));
            return null;
        }
    }

}
