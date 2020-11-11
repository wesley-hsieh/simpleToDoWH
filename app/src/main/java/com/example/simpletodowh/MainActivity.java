package com.example.simpletodowh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;

    Button addBtn;
    EditText editItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.addBtn);
        editItem = findViewById(R.id.editItem);
        rvItems = findViewById(R.id.rvItems);

        //editItem.setText("Changing text from mainactivity.java");

        loadItems();
        /*
        items.add("Buy Milk");
        items.add("Do Android todo project");
        items.add("Cuddle with dog");
         */

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //delete the item here because we have the interface
                items.remove(position);
                //notify adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        itemsAdapter =  new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = editItem.getText().toString();
                //add item to model
                items.add(todoItem);
                //notify adapter that there has been a change
                itemsAdapter.notifyItemInserted(items.size()-1);
                editItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    //read load the items in the data.txt file
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity.java", "Error reading items", e);
            items = new ArrayList<>();
        }
    };

    //write any/all changes to the list onto the data.txt
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity.java", "Error writing items", e);
        }
    }
}