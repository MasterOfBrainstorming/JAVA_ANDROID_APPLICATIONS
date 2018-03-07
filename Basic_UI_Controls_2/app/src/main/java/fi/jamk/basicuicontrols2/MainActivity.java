package fi.jamk.basicuicontrols2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, new String[]
                {"Pasi","Juha","Kari","Jouni","Esa","Hannu"});
        AutoCompleteTextView actv = (AutoCompleteTextView)
                findViewById(R.id.login_name);
        actv.setAdapter(aa);

    }

}
