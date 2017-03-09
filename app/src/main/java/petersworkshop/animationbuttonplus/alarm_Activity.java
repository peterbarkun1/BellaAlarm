package petersworkshop.animationbuttonplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class alarm_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_);
    }

    public void onClickAlarm(View view) {
        Toast.makeText(alarm_Activity.this, "Complete!", Toast.LENGTH_SHORT).show();
    }
}