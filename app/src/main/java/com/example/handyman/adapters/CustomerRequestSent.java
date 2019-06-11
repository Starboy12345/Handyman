package com.example.handyman.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handyman.R;
import com.example.handyman.models.RequestHandyMan;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerRequestSent extends FirebaseRecyclerAdapter<RequestHandyMan, CustomerRequestSent.HandyManRequest> {
    private Intent intent;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CustomerRequestSent(@NonNull FirebaseRecyclerOptions<RequestHandyMan> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HandyManRequest holder, int position, @NonNull final RequestHandyMan model) {
        holder.showName(model.getOwnerName());
        holder.showUserPhoto(model.getOwnerImage());
        holder.showResponse(model.getResponse());
        holder.showDate(model.getDate());

        final String getAdapterPosition = getRef(position).getKey();

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle(model.getOwnerName())
                        .setMessage(model.getReason())
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();


            }
        });
    }

    @NonNull
    @Override
    public HandyManRequest onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HandyManRequest((LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_handyman_request_received, viewGroup, false)));
    }


    //an inner class to hold the views to be inflated
    public class HandyManRequest extends RecyclerView.ViewHolder {
        private View view;
        private Button btnView;
        public ConstraintLayout viewForeground;
        RelativeLayout viewBackground;

        HandyManRequest(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            btnView = view.findViewById(R.id.btnView);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }


        void showDate(Long date) {

            TextView txtDate = view.findViewById(R.id.txtRequestDate);
            SimpleDateFormat sfd = new SimpleDateFormat("'Requested on ' dd-MM-yyyy '@' hh:mm aa",
                    Locale.US);

            try {
                txtDate.setText(sfd.format(date));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        //display the user photo
        void showUserPhoto(String urlOfImage) {
            CircleImageView profile = view.findViewById(R.id.imgUserPhotooo);

            Glide.with(view).load(urlOfImage).into(profile);
        }


        //display the Name
        void showName(String s) {
            TextView name = view.findViewById(R.id.txtYou);
            name.setText(s);
        }


        //display the details
        void showResponse(String s) {
            TextView loc = view.findViewById(R.id.txtResultsHandyMan);
            loc.setText(s);
        }


    }

}
