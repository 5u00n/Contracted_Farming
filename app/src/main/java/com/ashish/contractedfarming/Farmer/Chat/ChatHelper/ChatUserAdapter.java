package com.ashish.contractedfarming.Farmer.Chat.ChatHelper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Models.ChatUsersModel;
import com.ashish.contractedfarming.Models.PlantModel;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.viewHolder> {


    Context context;
    ArrayList<ChatUsersModel> chatUsersModelArrayList;

    private OnItemClickListener mListener;
    public void filterList(ArrayList<ChatUsersModel> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        chatUsersModelArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public ChatUserAdapter(Context context,ArrayList<ChatUsersModel> chatUsersModelArrayList){
        this.chatUsersModelArrayList=chatUsersModelArrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.view_chat_users,parent,false);


        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ChatUsersModel chatUsersModel=chatUsersModelArrayList.get(position);

        holder.id.setText(chatUsersModel.getId());
        holder.name.setText(chatUsersModel.getName());


        holder.status.setText(chatUsersModel.getStatus());
        if(chatUsersModel.getStatus().equals("online")){
            holder.status.setTextColor( Color.parseColor("#86D992"));
        }else {
            holder.status.setTextColor(Color.GRAY);
        }

        holder.user_img.setImageResource(R.drawable.logo);
        if(chatUsersModel.getImage()==null || chatUsersModel.getImage().equals("") || chatUsersModel.getImage().equals(" ")) {
            holder.user_img.setImageResource(R.drawable.logo);
        }else {
            Picasso.get().load(chatUsersModel.getImage()).into(holder.user_img);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClicked(chatUsersModel.getId()); // Pass the ID or relevant data

                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return chatUsersModelArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView id, name, status;
        ImageView user_img;

        CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.view_chats_id);
            name = itemView.findViewById(R.id.view_chats_nameofuser);
            status = itemView.findViewById(R.id.view_chats_statusofuser);

            user_img = itemView.findViewById(R.id.view_chats_imageviewofuser);

            cardView=itemView.findViewById(R.id.view_chats_card);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(String itemId);
    }
}
