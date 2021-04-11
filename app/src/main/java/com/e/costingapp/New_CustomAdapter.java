package com.e.costingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class New_CustomAdapter extends RecyclerView.Adapter<New_CustomAdapter.MyViewHolder1> {
    private Context context;
    private ArrayList proj_id, proj_name, proj_start, proj_end, proj_location, proj_cost;

    New_CustomAdapter(Context context,ArrayList proj_id, ArrayList proj_name, ArrayList proj_start, ArrayList proj_end, ArrayList proj_location, ArrayList proj_cost){
        this.context= context;
        this.proj_id= proj_id;
        this.proj_name= proj_name;
        this.proj_start= proj_start;
        this.proj_end= proj_end;
        this.proj_location= proj_location;
        this.proj_cost= proj_cost;

    }

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.project_row, parent, false);
        return new MyViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, final int position) {
        holder.proj_id_txt.setText(String.valueOf(proj_id.get(position)));
        holder.proj_name_txt.setText(String.valueOf(proj_name.get(position)));
        holder.proj_start_txt.setText(String.valueOf(proj_start.get(position)));
        holder.proj_end_txt.setText(String.valueOf(proj_end.get(position)));
        holder.proj_cost_txt.setText(String.valueOf(proj_cost.get(position)));
        holder.pjLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ViewProjectActivity.class);

                intent.putExtra("id", String.valueOf(proj_id.get(position)));
                intent.putExtra("name", String.valueOf(proj_name.get(position)));
                intent.putExtra("start_d", String.valueOf(proj_start.get(position)));
                intent.putExtra("end_d", String.valueOf(proj_end.get(position)));
                intent.putExtra("loci", String.valueOf(proj_location.get(position)));
                intent.putExtra("cost", String.valueOf(proj_cost.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return proj_id.size();
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        TextView proj_id_txt, proj_name_txt, proj_start_txt, proj_end_txt, proj_location_txt, proj_cost_txt;
        LinearLayout pjLayout;
        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            proj_id_txt = itemView.findViewById(R.id.tv_id);
            proj_name_txt = itemView.findViewById(R.id.tv_name);
            proj_start_txt = itemView.findViewById(R.id.tv_date_1);
            proj_end_txt = itemView.findViewById(R.id.tv_date_2);
//            proj_location_txt = itemView.findViewById(R.id.tv_name);
            proj_cost_txt = itemView.findViewById(R.id.estimate_cost);
            pjLayout = itemView.findViewById(R.id.pjLayout);
        }
    }
}
