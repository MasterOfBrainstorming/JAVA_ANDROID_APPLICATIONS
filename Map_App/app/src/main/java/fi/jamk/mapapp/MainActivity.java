package fi.jamk.mapapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMap(View view){
        EditText ed1 = (EditText) findViewById(R.id.latitude);
        EditText ed2 = (EditText) findViewById(R.id.longtitude);
        String nm1 = ed1.getText().toString();
        String nm2 = ed2.getText().toString();
        double lat = Double.parseDouble(nm1);
        double lng = Double.parseDouble(nm2);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:"+lat+lng));
        startActivity(intent);


    }
}
