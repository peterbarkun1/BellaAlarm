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
import java.util.TimeZone;

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

        //расчет биоритмов

        Date ts = new Date();
        System.out.println(ts);//дата сегодня
        double millis_segodn = ts.getTime();
        //Toast.makeText(this,(String.valueOf(millis)), Toast.LENGTH_LONG).show();
        //во время открытия  активити выводится на экран количество  миллисекунд от 1970 до сегодня


        TimeZone tz = TimeZone.getTimeZone("Europe/Moscow");

        Calendar tr = Calendar.getInstance(tz);
        tr.setLenient(false);

        tr.set(Calendar.MILLISECOND, 0);
        System.out.println(tr.getTimeInMillis());//обнуление



        //** ЗДЕСЬ НУЖНО ВСТАВИТЬ МОДУЛЬ ВВЕДЕНИЯ ДАТЫ ИЗ DIALOG



        tr.set(1998, Calendar.AUGUST, 7);//левая дата
        System.out.println(tr.getTimeInMillis());

        double millis_rozhden = tr.getTimeInMillis();
        //Toast.makeText(this,(String.valueOf(millis1)), Toast.LENGTH_LONG).show();
        // во время открытия, выводит к-во миллисеекунд от 1970 до 1998 августа 7-го 12 30
        double raz = millis_segodn - millis_rozhden;
        //Toast.makeText(this,(String.valueOf(raz)), Toast.LENGTH_LONG).show();
        //во время открытия, выводит разницу миллисекунд сегодняшнего дня от 7 августа 98
        double perev = raz/86400000;
        //Toast.makeText(this,(String.valueOf(perev)), Toast.LENGTH_LONG).show();
        //переводит разницу из миллисекунд в дни

        double[] razdney = new double[10];
        for(int i1 = 0; i1 < razdney.length; i1++) {
            razdney[i1] = perev++;
        }
        //создаем массив для отрисовки графика
//__________________________________________________________________________________________________

        //объявления переменных для графика и расчеты
        double x,x1,x2,x3,y,y1,y2,y3;

        //отрисовка графиков
        GraphView graph = (GraphView) findViewById(R.id.graph);


        // первый график - биоритм физический

        x = 0;

        series = new LineGraphSeries<DataPoint>();
            for (int i = 0; i<7; i++)
            {
                x=x+1;
                y=Math.sin(2*Math.PI*razdney[i]/23);
                series.appendData(new DataPoint(x,y),true,7);
            }
        graph.addSeries(series);
            series.setColor(Color.BLACK);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);
            series.setThickness(8);

        // второй график - биоритм эмоциональный

        x2 = 0;

        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i<7; i++)
        {
            x2=x2+1;
            y2=Math.sin(2*Math.PI*razdney[i]/28);
            series.appendData(new DataPoint(x2,y2),true,7);
        }
        graph.addSeries(series);
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        // третий график - биоритм интеллектуальный

        x3 = 0;

        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i<7; i++)
        {
            x3=x3+1;
            y3=Math.sin(2*Math.PI*razdney[i]/33);
            series.appendData(new DataPoint(x3,y3),true,7);
        }
        graph.addSeries(series);
        series.setColor(Color.WHITE);
        series.setDrawDataPoints(true);
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
//__________________________________________________________________________________________________

}

