package fi.jamk.downloadingimages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView image= (ImageView)findViewById(R.id.img);
        Picasso.with(this).load("http://cdn.newsapi.com.au/image/v1/9fdbf585d17c95f7a31ccacdb6466af9").resize(600, 600).centerCrop().into(image);
    }
    public void onClick(View v){
        ImageView image= (ImageView)findViewById(R.id.img);
        EditText edtx = (EditText)findViewById(R.id.edittext);
        String text = edtx.getText().toString();
        if (text != ""){
            Picasso.with(this).load(text).resize(600, 600).centerCrop().into(image);
            ((EditText) findViewById(R.id.edittext)).getText().clear();
        }
        else{Toast.makeText(getApplicationContext(), "Please input URL correctly!",Toast.LENGTH_SHORT).show();}
    }

}
