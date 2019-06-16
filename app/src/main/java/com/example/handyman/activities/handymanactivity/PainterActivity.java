package com.example.handyman.activities.handymanactivity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.handyman.R;
import com.example.handyman.adapters.HandyManTypesAdapter;
import com.example.handyman.models.HandyMan;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PainterActivity extends AppCompatActivity {
    HandyManTypesAdapter adapter;
    private DatabaseReference PainterDbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painter);

        PainterDbRef = FirebaseDatabase.getInstance().getReference().child("HandyMen").child("Painter");
        PainterDbRef.keepSynced(true);
        setUpRecycler();


    }

    private void setUpRecycler() {

        final RecyclerView recyclerView = findViewById(R.id.recyclerViewpainter);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());

        //now set the drawable of the item decorator
        try {
            itemDecoration.setDrawable(
                    ContextCompat.getDrawable(PainterActivity.this, R.drawable.recycler_divider)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }




        FirebaseRecyclerOptions<HandyMan> options = new FirebaseRecyclerOptions.Builder<HandyMan>().
                setQuery(PainterDbRef, HandyMan.class).build();

        adapter = new HandyManTypesAdapter(options);


        //add decorator
        recyclerView.addItemDecoration(itemDecoration);
        //attach adapter to recycler view
        recyclerView.setAdapter(adapter);
        //notify data change
        adapter.notifyDataSetChanged();


    }

}
