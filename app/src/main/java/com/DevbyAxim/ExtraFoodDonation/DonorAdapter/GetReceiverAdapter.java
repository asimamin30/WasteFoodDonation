package com.DevbyAxim.ExtraFoodDonation.DonorAdapter;

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

import com.DevbyAxim.ExtraFoodDonation.DonorActivities.CheckResponse;
import com.DevbyAxim.ExtraFoodDonation.DonorActivities.ReceiverRequestResponseActivity;
import com.DevbyAxim.ExtraFoodDonation.DonorActivities.RequestDonateFood;
import com.DevbyAxim.ExtraFoodDonation.Models.ReceiverModel;
import com.DevbyAxim.ExtraFoodDonation.R;

import java.util.ArrayList;

public class GetReceiverAdapter extends RecyclerView.Adapter<GetReceiverAdapter.HolderShop> {

    private Context context;
    public ArrayList<ReceiverModel> receiverlist;

    public GetReceiverAdapter(Context context, ArrayList<ReceiverModel> receiverlist) {
        this.context = context;
        this.receiverlist = receiverlist;
    }

    @NonNull
    @Override
    public HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_shop.xml
        View view = LayoutInflater.from(context).inflate(R.layout.custom_receiver,parent,false);


        return new HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderShop holder, int position) {
        //get data
        ReceiverModel modelShop = receiverlist.get(position);
        final String name = modelShop.getUsername();
        final String email = modelShop.getEmail();
        final String phone = modelShop.getPhoneNo();
        final String address = modelShop.getUserCompleteAddress();
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
                popup.inflate(R.menu.merchandise_more_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.response:
                                Intent intent = new Intent(context, CheckResponse.class);
                                context.startActivity(intent);
                                return true;
                            case R.id.foodRequest:
                                 intent = new Intent(context, RequestDonateFood.class);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                intent.putExtra("phone",phone);
                                intent.putExtra("address",address);
                                context.startActivity(intent);
                                return true;
                            case R.id.RequestResponse:
                                intent = new Intent(context, ReceiverRequestResponseActivity.class);
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
