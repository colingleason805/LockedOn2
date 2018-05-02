package com.example.colingleason.lockedon2;

import android.app.ListActivity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BlacklistActivity extends ListActivity {

    TextView content;
    static ArrayList<String> blacklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);


        content = (TextView)findViewById(R.id.output);

        //listView = (ListView) findViewById(R.id.list);
        //String[] values = new String[] { "Android Example ListActivity", "Adapter implementation", "Simple List View With ListActivity",
                //"ListActivity Android", "Android Example", "ListActivity Source Code", "ListView ListActivity Array Adapter", "Android Example ListActivity" };

        //get a list of every app installed on the phone
        final PackageManager pm = getPackageManager();
        String[] values = new String[2000];

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (int i = 0;  i< packages.size();i++){
               values[i] = packages.get(i).packageName;
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);


        // Assign adapter to List
        setListAdapter(adapter);

        blacklist = new ArrayList<String>();
        blacklist.add("fill");
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        // ListView Clicked item index
        int itemPosition     = position;

        // ListView Clicked item value
        String  itemValue    = (String) l.getItemAtPosition(position);

        content.setText("Click : \n  Position :"+itemPosition+"  \n  ListItem : " +itemValue);

        Iterator<String> it = blacklist.iterator();

        for(int i = 0; i< blacklist.size();i++){
            while(it.hasNext()){
                String theItem = it.next();
                //Toast.makeText(this, theItem, Toast.LENGTH_SHORT).show();
                if(theItem.equals(itemValue))
                {
                    blacklist.remove(i);
                    Toast.makeText(this, "Item is removed from the blacklist",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    blacklist.add(itemValue);
                    Toast.makeText(this, "Item is added to the blacklist", Toast.LENGTH_LONG).show();

                }
            }
        }
    }


}
