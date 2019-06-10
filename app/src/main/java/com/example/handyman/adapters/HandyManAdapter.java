package com.example.handyman.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handyman.R;
import com.example.handyman.RequestHandyManActivity;
import com.example.handyman.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class HandyManAdapter extends FirebaseRecyclerAdapter<User, HandyManAdapter.MechanicViewHolder> {
    private Intent intent;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HandyManAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MechanicViewHolder holder, int position, @NonNull final User model) {
        holder.showName(model.getFullName());
        holder.showOccupation(model.getOccupation());
        holder.showUserPhoto(model.getImage());
        holder.showUserPhoto(model.getMobileNumber());
        holder.showNumber(model.getAbout());

        final String getAdapterPosition = getRef(position).getKey();

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent = new Intent(v.getContext(), RequestHandyManActivity.class);
                intent.putExtra("position", getAdapterPosition);
                intent.putExtra("name", model.getFullName());
                intent.putExtra("image", model.getImage());
                intent.putExtra("occupation", model.getOccupation());
                intent.putExtra("about", model.getAbout());

                v.getContext().startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


            }
        });
    }

    @NonNull
    @Override
    public MechanicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MechanicViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_view_all_handy_men, viewGroup, false));
    }

    //an inner class to hold the views to be inflated
    public class MechanicViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private Button btnView;


        MechanicViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            btnView = view.findViewById(R.id.btnView);
        }

/*
        void showDate(Long date) {

            TextView txtDate = view.findViewById(R.id.txtShowLeaveDate);
//            SimpleDateFormat sfd = new SimpleDateFormat("'Sent on ' dd-MM-yyyy '@' hh:mm aa",
//                    Locale.US);

            try {
                txtDate.setText(GetDateTime.getFormattedDate(new Date(date)));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
*/

        //display the user photo
        void showUserPhoto(String urlOfImage) {
            CircleImageView checkInPhoto = view.findViewById(R.id.imgViewHandyMan);

            Glide.with(view).load(urlOfImage).into(checkInPhoto);
        }


        //display the Name
        void showName(String s) {
            TextView name = view.findViewById(R.id.txtNameOfHandyMan);
            name.setText(s);
        }

        //display the occupation
        void showOccupation(String s) {
            TextView occ = view.findViewById(R.id.txtOccupationOfHandyMan);
            occ.setText(s);
        }


        //display the location
        void showLocation(String s) {
            TextView loc = view.findViewById(R.id.txtLocationOfHandyMan);
            loc.setText(s);
        }

        //display the number
        void showNumber(String s) {
            TextView name = view.findViewById(R.id.txtHandyManAbout);
            name.setText(s);
        }


    }

}
