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
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dream Land on 1/12/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<MatchesListItems> itemsList = null;
    private List<TablesListItems> tablesListItems = null;
    private List<GoalsListItems> goalsListItems = null;
    private Context context;
    private String tag;

    public RecyclerViewAdapter(List<MatchesListItems> itemsList, Context context, String tag) {
        this.itemsList = itemsList;
        this.context = context;
        this.tag = tag;
    }

    public RecyclerViewAdapter(Context context, List<TablesListItems> tablesListItems, String tag) {
        this.tablesListItems = tablesListItems;
        this.context = context;
        this.tag = tag;
    }

    public RecyclerViewAdapter(String tag, List<GoalsListItems> goalsListItems, Context context) {
        this.goalsListItems = goalsListItems;
        this.context = context;
        this.tag = tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (tag) {
            case "Matches":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_list_item, parent, false);
                break;
            case "Tables":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list_item, parent, false);
                break;
            case "Goals":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goals_list_item, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_list_item, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        switch (tag) {
            case "Tables":
                TablesListItems tableItem = tablesListItems.get(position);
                holder.tvGroup.setText(tableItem.getGroup());

                String[] splitTeamNo = tableItem.getTeamNo().split(" ");
                holder.tvTeamNo1.setText(splitTeamNo[0]);
                holder.tvTeamNo2.setText(splitTeamNo[1]);
                holder.tvTeamNo3.setText(splitTeamNo[2]);
                holder.tvTeamNo4.setText(splitTeamNo[3]);

                String[] splitTeamName = tableItem.getTeamName().split(", ");
                holder.tvTeamName1.setText(splitTeamName[0]);
                holder.tvTeamName2.setText(splitTeamName[1]);
                holder.tvTeamName3.setText(splitTeamName[2]);
                holder.tvTeamName4.setText(splitTeamName[3]);

                Picasso.with(context).load(Flags.getFlag(splitTeamName[0])).into(holder.imgTeamFlag1);
                Picasso.with(context).load(Flags.getFlag(splitTeamName[1])).into(holder.imgTeamFlag2);
                Picasso.with(context).load(Flags.getFlag(splitTeamName[2])).into(holder.imgTeamFlag3);
                Picasso.with(context).load(Flags.getFlag(splitTeamName[3])).into(holder.imgTeamFlag4);

                String[] splitTeamStatus = tableItem.getStatus().split(", ");

                String team1Status = splitTeamStatus[0];
                String[] team1Split = team1Status.split(" ");
                holder.tvStatusP1.setText(team1Split[0]);
                holder.tvStatusGrd1.setText(team1Split[1]);
                holder.tvStatusPts1.setText(team1Split[2]);

                String team2Status = splitTeamStatus[1];
                String[] team2Split = team2Status.split(" ");
                holder.tvStatusP2.setText(team2Split[0]);
                holder.tvStatusGrd2.setText(team2Split[1]);
                holder.tvStatusPts2.setText(team2Split[2]);


                String team3Status = splitTeamStatus[2];
                String[] team3Split = team3Status.split(" ");
                holder.tvStatusP3.setText(team3Split[0]);
                holder.tvStatusGrd3.setText(team3Split[1]);
                holder.tvStatusPts3.setText(team3Split[2]);

                String team4Status = splitTeamStatus[3];
                String[] team4Split = team4Status.split(" ");
                holder.tvStatusP4.setText(team4Split[0]);
                holder.tvStatusGrd4.setText(team4Split[1]);
                holder.tvStatusPts4.setText(team4Split[2]);

                /*String[] flagLinks = tableItem.getFlagLink().split(" ");
                if (flagLinks[0] != null) {
                    Picasso.with(context).load(flagLinks[0]).into(holder.imgTeamFlag1);
                }
                if (flagLinks[1] != null) {
                    Picasso.with(context).load(flagLinks[1]).into(holder.imgTeamFlag2);
                }
                if (flagLinks[2] != null) {
                    Picasso.with(context).load(flagLinks[2]).into(holder.imgTeamFlag3);
                }
                if (flagLinks[3] != null) {
                    Picasso.with(context).load(flagLinks[3]).into(holder.imgTeamFlag4);
                }*/

                break;

            case "Goals":
                GoalsListItems goalList = goalsListItems.get(position);
                holder.tvNo.setText((position+1)+".");
                holder.tvName.setText(goalList.getName());
                holder.tvGoal.setText(goalList.getGoal());
                /*if (!goalList.getFlagLink().isEmpty()) {
                    Picasso.with(context).load(goalList.getFlagLink()).into(holder.teamImg);
                } else {
                    Picasso.with(context).load(R.drawable.ic_action_circle).into(holder.teamImg);
                }*/
                // TAG SHOULD BE JUST LIKE THIS (T SPAIN)
                String[] tagSplit = goalList.getTag().split(" ");
                Picasso.with(context).load(Flags.getFlag(tagSplit[1])).into(holder.teamImg);
                break;

            default:
                final MatchesListItems listItem = itemsList.get(position);
                String[] splitDateTime = listItem.getDate().split("/");
                String international = splitDateTime[0];
                String bangladeshi = splitDateTime[1];
                holder.tvDateTime.setText(bangladeshi);
                holder.tvRound.setText(listItem.getRound());

                if (listItem.getTeam1().equals("Saudi Arabia") || listItem.getTeam1().equals("Switzerland") ||
                        listItem.getTeam1().equals("South Korea") || listItem.getTeam1().contains("of")
                        || listItem.getTeam1().contains("Group") || listItem.getTeam1().contains("Winner")) {
                    holder.tvTeam1.setTextSize(14);
                } else {
                    holder.tvTeam1.setTextSize(16);
                }
                if ( listItem.getTeam2().equals("Saudi Arabia") ||
                    listItem.getTeam2().equals("Switzerland") ||
                    listItem.getTeam2().equals("South Korea") || listItem.getTeam1().contains("of")
                        || listItem.getTeam1().contains("Group")){
                    holder.tvTeam2.setTextSize(14);
                } else {
                    holder.tvTeam2.setTextSize(16);
                }
                holder.tvTeam1.setText(listItem.getTeam1());
                holder.tvTeam2.setText(listItem.getTeam2());
                holder.tvScore.setText(listItem.getScore());
                /*
                if (listItem.getFlagTeam1().isEmpty()) {
                    Picasso.with(context).load(R.drawable.ic_action_circle).into(holder.imgvTeam1);
                } else {
                    Picasso.with(context).load(listItem.getFlagTeam1()).into(holder.imgvTeam1);
                }
                if (listItem.getFlagTeam2().isEmpty()) {
                    Picasso.with(context).load(R.drawable.ic_action_circle).into(holder.imgvTeam2);
                } else {
                    Picasso.with(context).load(listItem.getFlagTeam2()).into(holder.imgvTeam2);
                }*/
                Picasso.with(context).load(Flags.getFlag(listItem.getTeam1())).into(holder.imgvTeam1);
                Picasso.with(context).load(Flags.getFlag(listItem.getTeam2())).into(holder.imgvTeam2);
                break;
        }
    }

    @Override
    public int getItemCount() {
        switch (tag) {
            case "Tables":
                return tablesListItems.size();
            case "Goals":
                return goalsListItems.size();
            default:
                return itemsList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Matches View
        TextView tvDateTime, tvRound, tvTeam1, tvTeam2, tvScore;
        CircleImageView imgvTeam1, imgvTeam2;

        // Tables View
        TextView tvGroup, tvTeamNo1, tvTeamNo2, tvTeamNo3, tvTeamNo4;
        TextView tvTeamName1,tvTeamName2, tvTeamName3, tvTeamName4;
        TextView tvStatusPts1, tvStatusPts2, tvStatusPts3, tvStatusPts4;
        TextView tvStatusGrd1, tvStatusGrd2, tvStatusGrd3, tvStatusGrd4;
        TextView tvStatusP1, tvStatusP2, tvStatusP3, tvStatusP4;
        CircleImageView imgTeamFlag1, imgTeamFlag2, imgTeamFlag3, imgTeamFlag4;

        // Goals View
        TextView tvNo, tvName, tvGoal;
        CircleImageView teamImg;

        ViewHolder(View itemView) {
            super(itemView);
            switch (tag) {
                case "Goals":
                    tvNo = itemView.findViewById(R.id.tv_no);
                    tvName = itemView.findViewById(R.id.tv_name);
                    tvGoal = itemView.findViewById(R.id.tv_goal);
                    teamImg = itemView.findViewById(R.id.image_team);
                    break;
                case "Tables":
                    tvGroup = itemView.findViewById(R.id.tv_group);
                    tvTeamNo1 = itemView.findViewById(R.id.tv_teamNo1);
                    tvTeamNo2 = itemView.findViewById(R.id.tv_teamNo2);
                    tvTeamNo3 = itemView.findViewById(R.id.tv_teamNo3);
                    tvTeamNo4 = itemView.findViewById(R.id.tv_teamNo4);
                    tvTeamName1 = itemView.findViewById(R.id.tv_teamName1);
                    tvTeamName2 = itemView.findViewById(R.id.tv_teamName2);
                    tvTeamName3 = itemView.findViewById(R.id.tv_teamName3);
                    tvTeamName4 = itemView.findViewById(R.id.tv_teamName4);
                    tvStatusPts1 = itemView.findViewById(R.id.tv_status_pts1);
                    tvStatusPts2 = itemView.findViewById(R.id.tv_status_pts2);
                    tvStatusPts3 = itemView.findViewById(R.id.tv_status_pts3);
                    tvStatusPts4 = itemView.findViewById(R.id.tv_status_pts4);
                    tvStatusGrd1 = itemView.findViewById(R.id.tv_status_grd1);
                    tvStatusGrd2 = itemView.findViewById(R.id.tv_status_grd2);
                    tvStatusGrd3 = itemView.findViewById(R.id.tv_status_grd3);
                    tvStatusGrd4 = itemView.findViewById(R.id.tv_status_grd4);
                    tvStatusP1 = itemView.findViewById(R.id.tv_status_p1);
                    tvStatusP2 = itemView.findViewById(R.id.tv_status_p2);
                    tvStatusP3 = itemView.findViewById(R.id.tv_status_p3);
                    tvStatusP4 = itemView.findViewById(R.id.tv_status_p4);
                    imgTeamFlag1 = itemView.findViewById(R.id.teamImage1);
                    imgTeamFlag2 = itemView.findViewById(R.id.teamImage2);
                    imgTeamFlag3 = itemView.findViewById(R.id.teamImage3);
                    imgTeamFlag4 = itemView.findViewById(R.id.teamImage4);
                    break;
                default:
                    tvDateTime = itemView.findViewById(R.id.tv_date_time);
                    tvRound = itemView.findViewById(R.id.tv_round);
                    tvTeam1 = itemView.findViewById(R.id.tv_team1);
                    tvTeam2 = itemView.findViewById(R.id.tv_team2);
                    tvScore = itemView.findViewById(R.id.tv_score);
                    imgvTeam1 = itemView.findViewById(R.id.teamImage_1);
                    imgvTeam2 = itemView.findViewById(R.id.teamImage_2);
                    break;
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String t =  null;
            if (tag.equals("Matches")) {
                t = tvTeam1.getText()+ " and "+tvTeam2.getText()+" match.";
            } else if (tag.equals("Tables")) {
                t = tvGroup.getText().toString();
            } else {
                t = tvName.getText().toString();
            }
            Toast.makeText(context, "Clicked on "+t, Toast.LENGTH_SHORT).show();
        }
    }

}
