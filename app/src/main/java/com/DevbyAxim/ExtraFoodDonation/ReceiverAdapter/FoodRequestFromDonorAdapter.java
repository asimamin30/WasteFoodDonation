package com.DevbyAxim.ExtraFoodDonation.ReceiverAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DevbyAxim.ExtraFoodDonation.Models.FoodRequestModel;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.ReceiverActivities.RequestResponseToDonor;

import java.util.ArrayList;

public class FoodRequestFromDonorAdapter extends RecyclerView.Adapter<FoodRequestFromDonorAdapter.HolderShop> {

    private Context context;
    public ArrayList<FoodRequestModel> foodreqlist;

    public FoodRequestFromDonorAdapter(Context context, ArrayList<FoodRequestModel> foodreqlist) {
        this.context = context;
        this.foodreqlist = foodreqlist;
    }

    @NonNull
    @Override
    public FoodRequestFromDonorAdapter.HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_shop.xml
        View view = LayoutInflater.from(context).inflate(R.layout.custom_food_req_from_donor,parent,false);


        return new FoodRequestFromDonorAdapter.HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodRequestFromDonorAdapter.HolderShop holder, int position) {
        //get data
        FoodRequestModel modelShop = foodreqlist.get(position);
        final String DonateFoodRequestId=modelShop.getDonateFoodRequestId();
        final String DonateFoodRequestTime=modelShop.getDonateFoodRequestTime();
        final String uname = modelShop.getUserName();
        final String description = modelShop.getRequestDescription();
        final String res = modelShop.getReponse();
        final String ut = modelShop.getUserType();
        if (ut.equals("Receiver")){
            holder.Resp.setVisibility(View.VISIBLE);
            holder.Res.setVisibility(View.VISIBLE);
        }else{
            //handle click listener, show shop details
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RequestResponseToDonor.class);
                    intent.putExtra("DonateFoodRequestId",DonateFoodRequestId);
                    intent.putExtra("DonateFoodRequestTime",DonateFoodRequestTime);
                    intent.putExtra("name",uname);
                    intent.putExtra("des",description);
                    intent.putExtra("res",res);
                    intent.putExtra("ut",ut);

                    context.startActivity(intent);
                }
            });
        }




        //set data
        holder.username.setText(uname);
        holder.desc.setText(description);




    }

    @Override
    public int getItemCount() {
        return foodreqlist.size(); // return number of records
    }

    //view holder
    class HolderShop extends RecyclerView.ViewHolder{

        private TextView username,desc,Resp,Res;
        private ImageView shopIv;
        public HolderShop(@NonNull View itemView) {
            super(itemView);

            //init ui views
            username = itemView.findViewById(R.id.Donor_username);
            desc = itemView.findViewById(R.id.Descr);
            Resp = itemView.findViewById(R.id.Resp);
            Res = itemView.findViewById(R.id.Res);
        }
    }

}
