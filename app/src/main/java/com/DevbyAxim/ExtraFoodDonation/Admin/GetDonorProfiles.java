package com.DevbyAxim.ExtraFoodDonation.Admin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DevbyAxim.ExtraFoodDonation.Models.DonorModel;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.ReceiverActivities.DonorRequestResponseActivity;
import com.DevbyAxim.ExtraFoodDonation.ReceiverActivities.FoodResponseToDonor;
import com.DevbyAxim.ExtraFoodDonation.ReceiverActivities.RequestFoodToDonor;

import java.util.ArrayList;

public class GetDonorProfiles extends RecyclerView.Adapter<GetDonorProfiles.HolderShop> {

    private Context context;
    public ArrayList<DonorModel> donorlist;

    public GetDonorProfiles(Context context, ArrayList<DonorModel> donorlist) {
        this.context = context;
        this.donorlist = donorlist;
    }

    @NonNull
    @Override
    public GetDonorProfiles.HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_shop.xml
        View view = LayoutInflater.from(context).inflate(R.layout.custom_donor_profile, parent, false);


        return new GetDonorProfiles.HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetDonorProfiles.HolderShop holder, int position) {
        //get data
        DonorModel modelShop = donorlist.get(position);
        final String name = modelShop.getUsername();
        final String email = modelShop.getEmail();
        final String phone = modelShop.getPhoneNo();
        final String address = modelShop.getUserCompleteAddress();
        Log.d("name", name);


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
                popup.inflate(R.menu.merchandise_more_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.response:
                                Intent intent = new Intent(context, FoodResponseToDonor.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("phone", phone);
                                intent.putExtra("address", address);
                                context.startActivity(intent);
                                return true;
                            case R.id.foodRequest:
                                intent = new Intent(context, RequestFoodToDonor.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("phone", phone);
                                intent.putExtra("address", address);
                                context.startActivity(intent);
                                return true;
                            case R.id.RequestResponse:
                                intent = new Intent(context, DonorRequestResponseActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("phone", phone);
                                intent.putExtra("address", address);
                                context.startActivity(intent);
                                return  true;
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
        return donorlist.size(); // return number of records
    }

    //view holder
    class HolderShop extends RecyclerView.ViewHolder {

        private TextView username, email, phone;
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
