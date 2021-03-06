package com.rupesh.baji.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.baji.R;
import com.rupesh.baji.activities.Bottom_nav;
import com.rupesh.baji.helper.ChallengeDialog;
import com.rupesh.baji.model.Challenge;
import com.rupesh.baji.model.Result;
import com.rupesh.baji.model.User;
import com.rupesh.baji.url.Url;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CurrentChallengeAdapter extends RecyclerView.Adapter<CurrentChallengeAdapter.CurrentChallengeViewHolder> {

    Context mContext;
    List<Challenge> challengeList;
    private static User userme;
    private static final String TAG = "ChallengeAdapter";

    public CurrentChallengeAdapter(Context mContext, List<Challenge> challengeList) {
        this.mContext = mContext;
        this.challengeList = challengeList;
    }

    @NonNull
    @Override
    public CurrentChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_accepted_challenge, parent, false);
        return new CurrentChallengeViewHolder(view, mContext, challengeList);
    }

    public void Mode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentChallengeAdapter.CurrentChallengeViewHolder holder, int position) {
        Challenge challenge = challengeList.get(position);
        Mode();
        userme = challenge.getChBy();
        String imagePathPost = Url.imagePath + challenge.getChImage();
        Picasso.get().load(imagePathPost).into(holder.imgAcceptedchImage);

        holder.tvChallenger.setText(userme.getUname());
        holder.tvGameName.setText(challenge.getChGame());
        holder.tvBP.setText(challenge.getChAmt());

        holder.btnPostResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChallengeDialog();
            }

            private void openChallengeDialog() {
                Bundle args = new Bundle();
                User user;
                user = challenge.getChAcceptedby();
                User user1;
                user1 = challenge.getChBy();
                args.putString("chID", challenge.get_id());
                args.putString("acceptedBY", user.get_id());
                args.putString("challenger", user1.get_id());
                args.putString("currentUsr", Bottom_nav.user.get_id());

                ChallengeDialog challengeDialog = new ChallengeDialog();
                challengeDialog.setArguments(args);
                challengeDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "challenge dialog");
            }
        });

    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    public class CurrentChallengeViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAcceptedchImage;
        TextView tvChallenger, tvGameName, tvBP;
        Button btnPostResult, btn_accepted_Challenge_verify;
        Context context;
        List<Challenge> list;

        public CurrentChallengeViewHolder(@NonNull View itemView, Context mContext, List<Challenge> challengeList) {
            super(itemView);

            imgAcceptedchImage = itemView.findViewById(R.id.img_accepted_game_image);

            tvChallenger = itemView.findViewById(R.id.tv_accepted_challengerName);
            tvGameName = itemView.findViewById(R.id.tv_accepted_GameName);
            tvBP = itemView.findViewById(R.id.tv_accepted_BP);

            btnPostResult = itemView.findViewById(R.id.btn_accepted_Challenge_result);

            this.context = mContext;
            this.list = challengeList;

        }
    }
}
