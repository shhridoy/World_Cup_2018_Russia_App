package com.shhridoy.worldcup2018russia.myRecyclerViewData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.shhridoy.worldcup2018russia.R;

import java.util.List;

/**
 * Created by Dream Land on 1/12/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItems> itemsList = null;
    private Context context;

    public MyAdapter(List<ListItems> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ListItems listItem = itemsList.get(position);
        holder.tvDateTime.setText(listItem.getDate());
        holder.tvRound.setText(listItem.getRound());
        holder.tvTeam1.setText(listItem.getTeam1());
        holder.tvTeam2.setText(listItem.getTeam2());
        holder.tvScore.setText(listItem.getScore());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvDateTime, tvRound, tvTeam1, tvTeam2, tvScore;

        ViewHolder(View itemView) {
            super(itemView);
            tvDateTime = itemView.findViewById(R.id.tv_date_time);
            tvRound = itemView.findViewById(R.id.tv_round);
            tvTeam1 = itemView.findViewById(R.id.tv_team1);
            tvTeam2 = itemView.findViewById(R.id.tv_team2);
            tvScore = itemView.findViewById(R.id.tv_score);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Clicked on "+tvTeam1.getText()+ " and "+tvTeam2.getText()+" match.", Toast.LENGTH_SHORT).show();
        }
    }

}
