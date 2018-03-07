package fi.jamk.menuapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    NotificationCompat.Builder notifikaatio;
    private static final int uniqueID = 10234;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notifikaatio = new NotificationCompat.Builder(this);
        notifikaatio.setAutoCancel(true);
    }

    public void onClickNotification (View view){
        // build notification
        notifikaatio.setSmallIcon(R.drawable.ic_launcher);
        notifikaatio.setTicker("Ticktitle");
        notifikaatio.setWhen(System.currentTimeMillis());
        notifikaatio.setContentTitle("Menu app");
        notifikaatio.setContentText("Please come back to the app");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notifikaatio.setContentIntent(pendingIntent);

        // sending notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notifikaatio.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting_action) {
            Toast.makeText(MainActivity.this, "SETTINGS CLICK", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.setting_info) {
            Toast.makeText(MainActivity.this, "INFO CLICK", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
