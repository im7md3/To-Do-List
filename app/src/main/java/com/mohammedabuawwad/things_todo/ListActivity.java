package com.mohammedabuawwad.things_todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private EditText editText_listName;
    private List<Item> itemList;
    private Button saveList;
    private Button createList;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);

        createList = findViewById(R.id.createList);
        createList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ListActivity.this);
                View view = getLayoutInflater().inflate(R.layout.popup, null);
                editText_listName = view.findViewById(R.id.editText_listName);
                saveList = view.findViewById(R.id.saveList);
                builder.setView(view);
                dialog = builder.create();
                dialog.show();
                saveList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText_listName.getText().toString().isEmpty()) {
                            Item item = new Item();
                            item.setNameOfList(editText_listName.getText().toString());
                            itemList.add(item);
                            recyclerViewAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Snackbar.make(v, "Field is Empty!", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}