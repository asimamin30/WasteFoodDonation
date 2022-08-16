package com.DevbyAxim.ExtraFoodDonation.Admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.DevbyAxim.ExtraFoodDonation.Models.ReceiverModel;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GetReceiverProfiles extends RecyclerView.Adapter<GetReceiverProfiles.HolderShop> {

    private Context context;
    public ArrayList<ReceiverModel> receiverlist;
    String name;

    public GetReceiverProfiles(Context context, ArrayList<ReceiverModel> receiverlist) {
        this.context = context;
        this.receiverlist = receiverlist;

    }

    @NonNull
    @Override
    public GetReceiverProfiles.HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_shop.xml
        View view = LayoutInflater.from(context).inflate(R.layout.custom_receiver_profile,parent,false);


        return new GetReceiverProfiles.HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetReceiverProfiles.HolderShop holder, int position) {
        //get data
        ReceiverModel modelShop = receiverlist.get(position);
          name = modelShop.getUsername().toString();
         String email = modelShop.getEmail();
         String phone = modelShop.getPhoneNo();
         String address = modelShop.getUserCompleteAddress();
        Log.d("name",name);


        //set data
        holder.username.setText(name);
        holder.email.setText(email);
        holder.phone.setText(phone);



        //handle click listener, show shop details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.itemView);
                //inflating menu from xml resource
                popup.inflate(R.menu.delete_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials").child(name);
                                reference.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        ref.removeValue();
                                    }
                                });
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();



            }
        });
    }

    @Override
    public int getItemCount() {
        return receiverlist.size(); // return number of records
    }

    //view holder
    class HolderShop extends RecyclerView.ViewHolder{

        private TextView username,email,phone;
        private ImageView shopIv;
        public HolderShop(@NonNull View itemView) {
            super(itemView);

            //init ui views
            username = itemView.findViewById(R.id.receiver_username);
            email = itemView.findViewById(R.id.receiver_email);
            phone = itemView.findViewById(R.id.receiver_phoneNo);
        }
    }

}
