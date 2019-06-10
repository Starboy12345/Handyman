package com.example.handyman;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.handyman.adapters.HandyManAdapter;
import com.example.handyman.models.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityMechanicList extends AppCompatActivity {

    HandyManAdapter adapter;
    private DatabaseReference mechanicDbRef;
    private static final String TAG = "ActivityMechanicList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic);

        mechanicDbRef = FirebaseDatabase.getInstance().getReference().child("HandyMen").child("Mechanic");
        setUpRecycler();
    }



    private void setUpRecycler() {
        Log.d(TAG, "setUpRecycler: completed");
        final RecyclerView recyclerView = findViewById(R.id.recyclerViewMechanic);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());

        //now set the drawable of the item decorator
        try {
            itemDecoration.setDrawable(
                    ContextCompat.getDrawable(ActivityMechanicList.this, R.drawable.recycler_divider)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }




        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>().
                setQuery(mechanicDbRef, User.class).build();

        adapter = new HandyManAdapter(options);


        //add decorator
        recyclerView.addItemDecoration(itemDecoration);
        //attach adapter to recycler view
        recyclerView.setAdapter(adapter);
        //notify data change
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
