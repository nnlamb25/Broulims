package com.broulims.broulims;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class search_test extends AppCompatActivity {
    //push this



    //String[] items;
    //ArrayList<String> listItems;
    ArrayAdapter<Map<String,String>> adapter;
    //ListView listView;
    EditText editText;


    ListView products;
    DatabaseReference databaseReference;
    List<Map<String, String>> productList = new ArrayList<>();
    //SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_test);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //listView = (ListView)findViewById(R.id.listview);

        products  = (ListView) findViewById(R.id.listview);
        editText = (EditText)findViewById(R.id.txtitem);
        /*simpleAdapter = new SimpleAdapter(this, productList,
                android.R.layout.simple_list_item_2,
                new String[] {"Name", "Price"},
                new int[] {android.R.id.text1, android.R.id.text2});*/
        initList();
        products.setAdapter(adapter);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    //Price wasn't working, will fix it later
                    Map<String, String> item = new HashMap<>(2);
                    item.put("Name", ds.child("ItemDescription").getValue().toString());
                    item.put("Price", "Price: " + ds.child("BasePrice").getValue().toString() + " Aisle: " + ds.child("Aisle").getValue().toString());

                    productList.add(item);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toException());
            }
        };

        databaseReference.addValueEventListener(listener);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    initList();
                }
                else {
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void searchItem(String textToSearch) {
        //for(String item:items)

        for(Map<String,String> myMap:productList) {
            /*if(!item.contains(textToSearch)) {
                productList.remove(item);
            }*/
            for(String key: myMap.keySet())
            {
                if(!key.contains(textToSearch)) {
                    productList.remove(myMap);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void initList() {
        //items = new String[] {"Canada", "China", "Japan", "USA"};
        //listItems = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.txtitem,productList);
        /*simpleAdapter = new SimpleAdapter(this, R.layout.list_item,,
                android.R.layout.simple_list_item_2,
                new String[] {"Name", "Price"},
                new int[] {android.R.id.text1, android.R.id.text2});*/
        products.setAdapter(adapter);

    }
}
