package com.DevbyAxim.ExtraFoodDonation.ReceiverAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DevbyAxim.ExtraFoodDonation.Models.RcFoodReqModel;
import com.DevbyAxim.ExtraFoodDonation.R;

import java.util.ArrayList;

public class RequestResponseFromDonorAdapter extends RecyclerView.Adapter<RequestResponseFromDonorAdapter.HolderShop> {

    private Context context;
    public ArrayList<RcFoodReqModel> rcFoodReqModels;

    public RequestResponseFromDonorAdapter(Context context, ArrayList<RcFoodReqModel> rcFoodReqModels) {
        this.context = context;
        this.rcFoodReqModels = rcFoodReqModels;
    }

    @NonNull
    @Override
    public RequestResponseFromDonorAdapter.HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_shop.xml
        View view = LayoutInflater.from(context).inflate(R.layout.custom_requset_from_donor,parent,false);


        return new RequestResponseFromDonorAdapter.HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestResponseFromDonorAdapter.HolderShop holder, int position) {
        //get data
        RcFoodReqModel modelShop = rcFoodReqModels.get(position);
        final String DonateFoodRequestId=modelShop.getDonateFoodRequestId();
        final String DonateFoodRequestTime=modelShop.getDonateFoodRequestTime();
        final String uname = modelShop.getUserName();
        final String description = modelShop.getRequestDescription();
        final String res = modelShop.getResponse();
        final String ut = modelShop.getUserType();

        Log.d("res",res);

        //set data
        holder.username.setText(uname);
        holder.desc.setText(description);

        if (res.equals("null")){
            holder.Res.setText("No Response");
        }else{
            holder.Res.setText(res);
        }





    }

    @Override
    public int getItemCount() {
        return rcFoodReqModels.size(); // return number of records
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

