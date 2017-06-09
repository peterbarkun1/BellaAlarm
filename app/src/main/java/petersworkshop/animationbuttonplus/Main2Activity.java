package petersworkshop.animationbuttonplus;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Main2Activity extends AppCompatActivity {

    String s = "";//выводящая переменная из файла даты
    String q = "";//выводящая переменная из файла настроения
    Double vybor;//переменная для превода в тип Double
    double[] nastroy = new double[10]; // массив который строит график настроения
    double[] razdney = new double[10]; // массив который строит график биоритмо

    private static Date birthD = new Date();// конечный тип даты после чтения файла

    LineGraphSeries<DataPoint> series1;
    LineGraphSeries<DataPoint> series2;
    LineGraphSeries<DataPoint> series3;
    LineGraphSeries<DataPoint> series;
    Constants.TransitionType type;

    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }

    private static final int READ_BLOCK_SIZE = 100; // определяем размер буфера при считывании с файла

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button1 = (Button) findViewById(R.id.button1);

        //обьявление функции анимации
        initPage();
        initAnimation();

//__________________________________________________________________________________________________
        //работа с классом asynctask 1

            // не работает onPostExecute
            // ну а так
            //1 min долго компилит  ок!!! ща взорвётся
            // то есть какую надо?  7 08 1998
            // Закинь в onPostExecute всё, что работает с birthD чтобы там всё было сразу, а то я не знаю
            // Чтение файлов и их запись - прервают UI поток - поэтому будильник не работает
            // суть в том, что можно было просто новый поток создать
            // даже если так, ок. У тебя в Async считает читает данные из файла. Только это нужно? Просто правильно считать?
            //стоп там есть этот код уже закомментирован

        class OutBTask extends AsyncTask<Void, Void,Date>
        {
            @Override
            protected Date doInBackground(Void... params)// метод для создания отдельного потока, не имеет доступ в UI поток (#этоважно)
            {
                try {
                    FileInputStream fileInputStream = openFileInput("Birthday.txt");
                    InputStreamReader reader = new InputStreamReader(fileInputStream);

                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    int charRead;

                    // цикл читает данные из файла,
                    while ((charRead = reader.read(inputBuffer)) != -1) {
                        // конвертируем char в строку
                        String rString = String.copyValueOf(inputBuffer, 0, charRead);
                        s += rString;
                    }
                    reader.close();
                    // создаем всплывающее окно c результатом выволнения чтения из файла
                    // Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //перевод тип date
                Date birthD = new Date();
                DateFormat format = new SimpleDateFormat("MMMM.dd.yyyy", Locale.ENGLISH);
                System.out.println(birthD);
                System.out.println();

                try {
                    birthD = format.parse(s);
                }  catch (ParseException e) {
                    e.printStackTrace();
                }
                return birthD;
            }

            @Override
            protected void onPostExecute(Date date) {
                super.onPostExecute(date);

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

                //выводит дату введенную в диалоге

                //birthD.getTime(); // Узнаем количество миллисекунд Unix-time с того-самого-момента.
                // Toast.makeText(this,(String.valueOf(birthD.getTime())), Toast.LENGTH_LONG).show();

                //tr.set(1998, Calendar.AUGUST, 8);//левая дата для первых тестов с вложенными данными
                //System.out.println(tr.getTimeInMillis());

                // double millis_rozhden = tr.getTimeInMillis(); //рабочий
                double millis_rozhden = date.getTime();//проба
                //Toast.makeText(this,(String.valueOf(millis_rozhden)), Toast.LENGTH_LONG).show();
                // во время открытия, выводит к-во миллисеекунд от 1970 до установленной даты
                double raz = millis_segodn - millis_rozhden;
                //Toast.makeText(this,(String.valueOf(raz)), Toast.LENGTH_LONG).show();
                //во время открытия, выводит разницу миллисекунд сегодняшнего дня от установленной даты
                double perev = raz / 86400000;
                //Toast.makeText(this,(String.valueOf(perev)), Toast.LENGTH_LONG).show();
                //переводит разницу из миллисекунд в дни
//__________________________________________________________________________________________________
                //создаем массив для отрисовки графика биоритмов
                for (int i1 = 0; i1 < razdney.length; i1++) {
                    razdney[i1] = perev++;
                }
//__________________________________________________________________________________________________
                //объявления переменных для графика и расчеты
                double x, x1, x2, x3, y, y1, y2, y3;

                //отрисовка графиков
                GraphView graph = (GraphView) findViewById(R.id.graph);


                // первый график - биоритм физический

                x = 0;

                series1 = new LineGraphSeries<DataPoint>();
                for (int i = 0; i < 7; i++) {
                    x = x + 1;
                    y = Math.sin(2 * Math.PI * razdney[i] / 23);
                    series1.appendData(new DataPoint(x, y), true, 7);
                }
                graph.addSeries(series1);
                series1.setColor(Color.BLACK);
                series1.setDrawDataPoints(true);
                series1.setDataPointsRadius(10);
                series1.setThickness(8);

                // второй график - биоритм эмоциональный

                x2 = 0;

                series2 = new LineGraphSeries<DataPoint>();
                for (int i = 0; i < 7; i++) {
                    x2 = x2 + 1;
                    y2 = Math.sin(2 * Math.PI * razdney[i] / 28);
                    series2.appendData(new DataPoint(x2, y2), true, 7);
                }
                graph.addSeries(series2);
                series2.setColor(Color.GREEN);
                series2.setDrawDataPoints(true);
                series2.setDataPointsRadius(10);
                series2.setThickness(8);

                // третий график - биоритм интеллектуальный

                x3 = 0;

                series3 = new LineGraphSeries<DataPoint>();
                for (int i = 0; i < 7; i++) {
                    x3 = x3 + 1;
                    y3 = Math.sin(2 * Math.PI * razdney[i] / 33);
                    series3.appendData(new DataPoint(x3, y3), true, 7);
                }
                graph.addSeries(series3);
                series3.setColor(Color.WHITE);
                series3.setDrawDataPoints(true);
                series3.setDataPointsRadius(10);
                series3.setThickness(8);

                //легенда

                series1.setTitle("Sport");
                series2.setTitle("Emotion");
                series3.setTitle("Intel");

                //настройки
                graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().resetStyles();
                graph.getLegendRenderer().setTextColor(Color.WHITE);

                //расположение
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
                //graph.getLegendRenderer().setFixedPosition(10,505);

                //цвет значений
                graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);

                //цвет осей
                graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
                graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);

            }
        }

        OutBTask mt;
        mt = new OutBTask();
        mt.execute();
//__________________________________________________________________________________________________
        //работа с классом asynctask 2

        class OutNTask extends AsyncTask<Void, Void,Double>
        {
            @Override
            protected Double doInBackground(Void... params)// метод для создания отдельного потока, не имеет доступ в UI поток (#этоважно)
            {
                try {
                    FileInputStream fileInputStream = openFileInput("Moods.txt");
                    InputStreamReader reader = new InputStreamReader(fileInputStream);

                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    int charRead;

                    // цикл читает данные из файла,
                    while ((charRead = reader.read(inputBuffer)) != -1) {
                        // конвертируем char в строку
                        String rString = String.copyValueOf(inputBuffer, 0, charRead);
                        q += rString;
                    }
                    reader.close();
                    // создаем всплывающее окно c результатом выволнения чтения из файла
                    //Toast.makeText(getBaseContext(),q, Toast.LENGTH_LONG).show();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                //перевод в double
                vybor = Double.parseDouble(q);
                System.out.println(vybor);
                System.out.println();
                return vybor;
            }

            @Override
            protected void onPostExecute(Double result)
            {
                super.onPostExecute(result);
                //создаем массив для отрисовки графика настроения
                for (int i1 = 0; i1 < nastroy.length; i1++)
                {
                    nastroy[i1] = vybor;

                }
                //объявления переменных для графика и расчеты
                double  x1, y1;

                //отрисовка графиков
                GraphView graph = (GraphView) findViewById(R.id.graph);

                // настроение пользователя
                x1 = 0;
                series = new LineGraphSeries<>();
                for (int i = 0; i < 7; i++)
                {
                    x1 = x1 + 1;
                    y1 = nastroy[i];
                    series.appendData(new DataPoint(x1, y1), true, 7);
                }
                graph.addSeries(series);
                series.setColor(Color.RED);
                series.setDrawDataPoints(true);
                //series.setDrawBackground(true);
                series.setDataPointsRadius(10);
                series.setThickness(8);
                //легенда
                series.setTitle("Y mood");
            }
        }
//__________________________________________________________________________________________________
        //проверка на создание файла
        if (new File("Moods.txt").exists())
        {
            //переход на экран выбора настроения
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(Main2Activity.this, UserActivity.class);
            intent.putExtra(Constants.KEY_ANIM_TYPE, Constants.TransitionType.ExplodeJava);
            startActivity(intent, options.toBundle());
        }
        else
        {
            OutNTask nt;
            nt = new OutNTask();
            nt.execute();
        }
    }


//__________________________________________________________________________________________________


    //анимация
    private void initPage() {
        type = (Constants.TransitionType) getIntent().getSerializableExtra(Constants.KEY_ANIM_TYPE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;
    }

    private void initAnimation() {
        switch (type) {
            case ExplodeJava: {
                Explode enterTransition = new Explode();
                enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                getWindow().setEnterTransition(enterTransition);
                break;
            }
        }
    }
//__________________________________________________________________________________________________

    //переход к информации
    public void userData(View view) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(Main2Activity.this, UserActivity.class);
        intent.putExtra(Constants.KEY_ANIM_TYPE, Constants.TransitionType.ExplodeJava);
        startActivity(intent, options.toBundle());
    }
}
//__________________________________________________________________________________________________
//комменты
/**проверка на создание файла
 try {
 FileInputStream fileInputStream = openFileInput("Birthday.txt");
 InputStreamReader reader = new InputStreamReader(fileInputStream);

 char[] inputBuffer = new char[READ_BLOCK_SIZE];
 int charRead;

 // цикл читает данные из файла,
 while ((charRead = reader.read(inputBuffer)) != -1) {
 // конвертируем char в строку
 String rString = String.copyValueOf(inputBuffer, 0, charRead);
 s += rString;
 }
 reader.close();
 // создаем всплывающее окно c результатом выволнения чтения из файла
 // Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();

 } catch (Exception e) {
 e.printStackTrace();
 }

 //перевод тип date
 Date birthD = new Date();
 DateFormat format = new SimpleDateFormat("MMMM.dd.yyyy", Locale.ENGLISH);

 try {
 birthD = format.parse(s);
 }  catch (ParseException e) {
 e.printStackTrace();
 }*/
//__________________________________________________________________________________________________
/**проверка на создание файла настроения
 if (new File("Moods.txt").exists()) {

 //переход на экран выбора настроения
 ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
 Intent intent = new Intent(Main2Activity.this, UserActivity.class);
 intent.putExtra(Constants.KEY_ANIM_TYPE, Constants.TransitionType.ExplodeJava);
 startActivity(intent, options.toBundle());
 } else {
 try {
 FileInputStream fileInputStream = openFileInput("Moods.txt");
 InputStreamReader reader = new InputStreamReader(fileInputStream);

 char[] inputBuffer = new char[READ_BLOCK_SIZE];
 int charRead;

 // цикл читает данные из файла,
 while ((charRead = reader.read(inputBuffer)) != -1) {
 // конвертируем char в строку
 String rString = String.copyValueOf(inputBuffer, 0, charRead);
 q += rString;
 }
 reader.close();
 // создаем всплывающее окно c результатом выволнения чтения из файла
 //Toast.makeText(getBaseContext(),q, Toast.LENGTH_LONG).show();

 } catch (Exception e) {
 e.printStackTrace();
 }

 }

 //перевод в double
 vybor = Double.parseDouble(q);
 //создаем массив для отрисовки графика настроения
 for (int i1 = 0; i1 < nastroy.length; i1++) {
 nastroy[i1] = vybor;
 }*/
//__________________________________________________________________________________________________
 /**объявления переменных для графика и расчеты
 double x, x1, x2, x3, y, y1, y2, y3;

 //отрисовка графиков
 GraphView graph = (GraphView) findViewById(R.id.graph);


 // первый график - биоритм физический

 x = 0;

 series1 = new LineGraphSeries<DataPoint>();
 for (int i = 0; i < 7; i++) {
 x = x + 1;
 y = Math.sin(2 * Math.PI * razdney[i] / 23);
 series1.appendData(new DataPoint(x, y), true, 7);
 }
 graph.addSeries(series1);
 series1.setColor(Color.BLACK);
 series1.setDrawDataPoints(true);
 series1.setDataPointsRadius(10);
 series1.setThickness(8);

 // второй график - биоритм эмоциональный

 x2 = 0;

 series2 = new LineGraphSeries<DataPoint>();
 for (int i = 0; i < 7; i++) {
 x2 = x2 + 1;
 y2 = Math.sin(2 * Math.PI * razdney[i] / 28);
 series2.appendData(new DataPoint(x2, y2), true, 7);
 }
 graph.addSeries(series2);
 series2.setColor(Color.GREEN);
 series2.setDrawDataPoints(true);
 series2.setDataPointsRadius(10);
 series2.setThickness(8);

 // третий график - биоритм интеллектуальный

 x3 = 0;

 series3 = new LineGraphSeries<DataPoint>();
 for (int i = 0; i < 7; i++) {
 x3 = x3 + 1;
 y3 = Math.sin(2 * Math.PI * razdney[i] / 33);
 series3.appendData(new DataPoint(x3, y3), true, 7);
 }
 graph.addSeries(series3);
 series3.setColor(Color.WHITE);
 series3.setDrawDataPoints(true);
 series3.setDataPointsRadius(10);
 series3.setThickness(8);

 // настроение пользователя
 x1 = 0;
 series = new LineGraphSeries<DataPoint>();
 for (int i = 0; i < 7; i++) {
 x1 = x1 + 1;
 y1 = nastroy[i];
 series.appendData(new DataPoint(x1, y1), true, 7);
 }

 /**
 graph.addSeries(series);
 series.setColor(Color.RED);
 series.setDrawDataPoints(true);
 //series.setDrawBackground(true);
 series.setDataPointsRadius(10);
 series.setThickness(8);

 //легенда
 series.setTitle("Y mood");
 series1.setTitle("Sport");
 series2.setTitle("Emotion");
 series3.setTitle("Intel");

 //настройки
 graph.getLegendRenderer().setVisible(true);
 graph.getLegendRenderer().resetStyles();
 graph.getLegendRenderer().setTextColor(Color.WHITE);

 //расположение
 graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
 //graph.getLegendRenderer().setFixedPosition(10,505);

 //цвет значений
 graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
 graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);

 //цвет осей
 graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
 graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);*/