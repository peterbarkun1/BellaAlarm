package petersworkshop.animationbuttonplus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Window;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main2Activity extends AppCompatActivity {

    LineGraphSeries<DataPoint>series;
    Constants.TransitionType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //обьявление функции анимации

        initPage();
        initAnimation();
//__________________________________________________________________________________________________
        //объявления переменных для графика и расчеты

        double x,y,x1,y1,pi;
        Date ts;//дата сегодня

        pi=3.14;

        ts = new Date();
        System.out.println(ts);//дата сегодня

        Calendar tr = new GregorianCalendar(2013, 8, 13);//левая дата рождения


//__________________________________________________________________________________________________
        //отрисовка графиков
        GraphView graph = (GraphView) findViewById(R.id.graph);

        // первый график - биоритмы

        x=0;
        series = new LineGraphSeries<DataPoint>();
            for (int i = 0; i<7; i++)
            {
                x=x+1;
                y=Math.sin(2*x*x/23);
                series.appendData(new DataPoint(x,y),true,7);
            }
        graph.addSeries(series);
            series.setColor(Color.WHITE);
            series.setDrawDataPoints(true);
            series.setDrawBackground(true);
            series.setDataPointsRadius(10);
            series.setThickness(8);

        //косинусоида
        x1=0;
        series = new LineGraphSeries<DataPoint>();
            for (int i = 0; i<7; i++)
            {
                x1=x1+1;
                y1=Math.cos(x1);
                series.appendData(new DataPoint(x1,y1),true,7);
            }
        graph.addSeries(series);
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);
            series.setDrawBackground(true);
            series.setDataPointsRadius(10);
            series.setThickness(8);
    }
//__________________________________________________________________________________________________
    //анимация
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

}

