package fi.jamk.garbagelist;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import android.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.Activity;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> shoppingList = null;
    ArrayAdapter<String> adapter = null;
    ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingList = getShoppingList(getApplicationContext());
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shoppingList);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, final int position, long id) {
                String item = ((TextView) view).getText().toString();
                if (item.trim().equals(shoppingList.get(position).trim())) {
                    removeItem(item, position);
                } else {
                    Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
// POISTETAAN KLIKATTU LISTALTA
    public void removeItem(String item, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove " + item);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                shoppingList.remove(position);
                storeShoppingList(shoppingList, getApplicationContext());
                lv.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // JÄRJESTETÄÄN LISTA
        if (id == R.id.action_sort){
            Collections.sort(shoppingList);
            lv.setAdapter(adapter);
            return true;
        }
        // LISÄTÄÄN LISTAAN
        if (id == R.id.action_add){
            /*
            VANHA
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Add a new Product");
            final EditText input1 = new EditText(this);
            builder1.setView(input1);
            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener(){
               @Override
                public void onClick(DialogInterface dialog, int id){
                   shoppingList.add(input1.getText().toString());
                   Collections.sort(shoppingList);
                   storeShoppingList(shoppingList,getApplicationContext());
                   lv.setAdapter(adapter);
               }
            });
            builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder1.show();
            return true;*/
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.textentry_dialog,null);
            builder.setView(dialogView)
                    .setTitle("Add a new Product")
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {
                            EditText ed1 = (EditText) dialogView.findViewById(R.id.product_text);
                            EditText ed2 = (EditText) dialogView.findViewById(R.id.count_text);
                            EditText ed3 = (EditText) dialogView.findViewById(R.id.price_text);
                            String shoppingitem = ed1.getText().toString() + "            " + ed2.getText().toString() + "            " + ed3.getText().toString();
                            shoppingList.add(shoppingitem);
                            Collections.sort(shoppingList);
                            storeShoppingList(shoppingList, getApplicationContext());
                            lv.setAdapter(adapter);
                        }
                    });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }
        if (id == R.id.action_clear){
            shoppingList.clear();
            lv.setAdapter(adapter);
        }

        return super.onOptionsItemSelected(item);
    }
// SÄILÖTÄÄN PREFERENSSEIHIN OSTOSLISTA
    public static void storeShoppingList( ArrayList<String> inArrayList, Context context)
    {
        Set<String> string = new HashSet<String>(inArrayList);
        SharedPreferences sharedPreferences = context.getSharedPreferences("dbArrayValues", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putStringSet("myArray", string);
        prefEditor.commit();
    }
// HAETAAN PREFERENSSEISTÄ OSTOSLISTA
    public static ArrayList getShoppingList( Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("dbArrayValues",Activity.MODE_PRIVATE);
        Set<String> tempSet = new HashSet<String>();
        tempSet = sharedPreferences.getStringSet("myArray", tempSet);
        return new ArrayList<String>(tempSet);
    }



}
