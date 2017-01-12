package com.example.babarmustafa.chatapplication.Chat_Work;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.babarmustafa.chatapplication.R;
import com.example.babarmustafa.chatapplication.User_Profile.postView.BlogViewHolder;
import com.example.babarmustafa.chatapplication.User_Profile.postView.PostObj;
import com.example.babarmustafa.chatapplication.User_Profile.postView.post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Two extends Fragment {
    TextView posttext;
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    FirebaseRecyclerAdapter<PostObj, BlogViewHolder> firebaseRecyclerAdapter;
    public Two() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        posttext = (TextView) view.findViewById(R.id.post);
        mBlogList = (RecyclerView) view.findViewById(R.id.blog_recylView_list);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        posttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postClick();
            }
        });




        return view;
    }


    public void postClick() {

        Intent intent = new Intent(getContext(), post.class);
        startActivity(intent);

    }

    private void FirebaseAdapter() {



    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<PostObj, BlogViewHolder>(

                        PostObj.class,
                        R.layout.card_view,
                        BlogViewHolder.class,
                        mDatabase
                ) {
                    @Override
                    protected void populateViewHolder(BlogViewHolder viewHolder, PostObj model, int position) {

                        viewHolder.seTitle(model.getTitle());
                        viewHolder.setDescription(model.getDesc());
                        viewHolder.setImage(getActivity(), model.getImages());

                    }
                };

        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAdapter();
    }
}


