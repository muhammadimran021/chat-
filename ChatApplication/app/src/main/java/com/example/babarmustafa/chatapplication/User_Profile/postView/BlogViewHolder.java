package com.example.babarmustafa.chatapplication.User_Profile.postView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.babarmustafa.chatapplication.R;
import com.squareup.picasso.Picasso;

/**
 * Created by muhammad imran on 11-Jan-17.
 */

public class BlogViewHolder extends RecyclerView.ViewHolder {
    View view;

    public BlogViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

    public void seTitle(String title) {

        TextView post_title = (TextView) view.findViewById(R.id.User_status);
        post_title.setText(title);
    }

    public void setDescription(String description) {
        TextView description_text = (TextView) view.findViewById(R.id.User_Name);
        description_text.setText(description);
    }

    public void setImage(Context context, String image) {

        ImageView post_image = (ImageView) view.findViewById(R.id.User_post_image);
        Picasso.with(context).load(image).into(post_image);

    }
}
