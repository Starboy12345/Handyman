package com.example.handyman;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RequestdetailsAdapter extends RecyclerView.Adapter {

    private List requestdata;
    private Context mContext;

    public RequestdetailsAdapter(List requestdata, Context mContext) {
        this.requestdata = requestdata;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_listofrequest,
                viewGroup, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RequestViewHolder extends RecyclerView.ViewHolder {


        TextView sender;
        TextView messagesent;
        ImageView profilepic;

        RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            sender=itemView.findViewById(R.id.txtsendername);
            messagesent=itemView.findViewById(R.id.txtmessgaesent);
            profilepic=itemView.findViewById(R.id.circleImageViewprofile);

        }
    }

}
