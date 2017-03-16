package petersworkshop.animationbuttonplus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Main2Activity extends AppCompatActivity {

    LineGraphSeries<DataPoint>series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        double x,y,x1,y1,pi;
        //отрисовка графиков
        x=0;
        pi=3.14;
        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i<7; i++)
        {
            x=x+1;
            y=Math.sin(2*pi*x/23);
            series.appendData(new DataPoint(x,y),true,7);
        }
        graph.addSeries(series);
        series.setColor(Color.WHITE);
        series.setDrawDataPoints(true);
        series.setDrawBackground(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

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

}