package com.example.babarmustafa.chatapplication.Chat_Work;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.babarmustafa.chatapplication.R;
import com.example.babarmustafa.chatapplication.User;
import com.example.babarmustafa.chatapplication.User_Profile.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ConversationActivity extends Activity {

    EditText for_message;
    ListView conversation;
    Button to_send;
    ImageButton back_to_main;


    DatabaseReference database;
    private FirebaseAuth mAuth;


    ArrayList<String> list;

    User f_data;
    String UUID;
    String get_f_id_on_clicked;
    String get_f_pic;
    String get_f_name;
    String get_f_email;
    String get_f_gender;
    CircularImageView for_user_image_on_toolbar;
    TextView for_user_name_selected_for_chat;
    private String conversationPushRef;
    private Conver conversationData;
    private boolean isConversationOld = false;

    public HashMap<String, String> hashObj = new HashMap<>();
    public HashMap<String, String> hashObj2 = new HashMap<>();
    NotificationMessage notificationMessage;

    RightAdapter listadapter;
    private ArrayList<NotificationMessage> messages;
    private FirebaseUser user;
    private ImageView pullerLayout;
    public static View groupMemberFragment;
    public static float groupMemberFragmentTop;
    public static View upperView;
    GestureDetector gestureDetector;
    AnimationForPullerBottomListener animationForPullerBottomListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);


        for_message = (EditText) findViewById(R.id.editMessage);
        to_send = (Button) findViewById(R.id.button_Send);
        back_to_main = (ImageButton) findViewById(R.id.throw_back);
        conversation = (ListView) findViewById(R.id.messages_conversation);
        for_user_image_on_toolbar = (CircularImageView) findViewById(R.id.user_pic);
        for_user_name_selected_for_chat = (TextView) findViewById(R.id.selected);
        pullerLayout = (ImageButton) findViewById(R.id.puller);
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        pullerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("AnimationExp", "in HomeFragment pullerLayout's onTouch()");
                return gestureDetector.onTouchEvent(event);
            }
        });


        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        list = new ArrayList<>();
        messages = new ArrayList<>();
        listadapter = new RightAdapter(messages, ConversationActivity.this);
        conversation.setAdapter(listadapter);
        user = mAuth.getInstance().getCurrentUser();
        UUID = mAuth.getCurrentUser().getUid();


        get_f_id_on_clicked = getIntent().getStringExtra("friend_uid");
        get_f_pic = getIntent().getStringExtra("friend_image");
        get_f_name = getIntent().getStringExtra("friend_name");
        get_f_email = getIntent().getStringExtra("friend_email");
        get_f_gender = getIntent().getStringExtra("friend_gender");


        Picasso.with(ConversationActivity.this).load(get_f_pic).into(for_user_image_on_toolbar);
        for_user_name_selected_for_chat.setText(get_f_name);
        checkConversationNewOROLD();
        ClickOnName();
        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//        getSupportFragmentManager()
//                .beginTransaction().add(R.id.frame_layout,new Two()).commit();
//        Intent u = new Intent(ConversationActivity.this,Chat_Main_View.class);
//        startActivity(u);
            }
        });

    }

    private void checkConversationNewOROLD() {
        database.child("user_conv").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Conver data = d.getValue(Conver.class);
                        if (data.getUserid().equals(get_f_id_on_clicked)) {

                            conversationData = data;
                            isConversationOld = true;
                            conversationPushRef = data.getConversationId();
                        }
                    }

                } catch (Exception ec) {
                    ec.printStackTrace();
                } finally {
                    getConvoDataOrCreateNew();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getConvoDataOrCreateNew() {
        if (isConversationOld) {
            getConversationData();
        } else {
            createNewConversation();
        }
    }

    private void createNewConversation() {
        DatabaseReference pushRef = database.child("conversation").push();
        conversationPushRef = pushRef.getKey();
        final Conver tempRefObj = new Conver(conversationPushRef, get_f_id_on_clicked);


        database.child("user_conv").child(UUID).push().setValue(tempRefObj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    tempRefObj.setUserid(get_f_id_on_clicked);
                    database.child("user_conv").child(get_f_id_on_clicked).push().setValue(tempRefObj, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                getConversationData();
                            }
                        }
                    });
                }
            }
        });
    }


    private void getConversationData() {
        database.child("conversation").child(conversationPushRef).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    NotificationMessage message = d.getValue(NotificationMessage.class);
                    messages.add(message);
                    listadapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        setButtonClick();
    }

    private void setButtonClick() {
        to_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                if (for_message.getText().length() > 1) {
                    NotificationMessage m = new NotificationMessage();
                    m.setMessage(for_message.getText().toString());
                    m.setTime(mydate);
                    m.setUUID(user.getUid());
                    database.child("conversation").child(conversationPushRef).push().setValue(m);

                    for_message.setText("");
                }
            }
        });
    }

    public void ClickOnName() {
        for_user_name_selected_for_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), UserProfile.class);
                i.putExtra("username", get_f_name);
                i.putExtra("userimage", get_f_pic);
                i.putExtra("useremail", get_f_email);
                i.putExtra("usergender", get_f_gender);
                startActivity(i);
            }
        });
    }

    private void addGroupMembersFragment(int member_fragment_container) {

//        SecondFragment groupMembersFragment = new SecondFragment();
//        animationForPullerBottomListener = groupMembersFragment;
//
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(member_fragment_container, groupMembersFragment)
//                .addToBackStack(null)
//                .commit();

    }


    public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            Log.d("AnimationExp", "in HomeFragment onFling(), e1.getY(): " + e1.getY() + ", e2.getY(): " + e2.getY());

            if (e1.getY() < e2.getY()) {

                moveDown();
            }
            return true;
        }
    }

    private void moveDown() {

        if (groupMemberFragmentTop == 0)
            groupMemberFragmentTop = -groupMemberFragment.getHeight();


        ObjectAnimator animationForUpperView = ObjectAnimator.ofFloat(upperView, "y", -groupMemberFragmentTop);
        animationForUpperView.setDuration(400);

        ObjectAnimator slideDownAnimation = ObjectAnimator.ofFloat(groupMemberFragment, "y", groupMemberFragmentTop, 0);
        slideDownAnimation.setDuration(400);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

//                    Log.d("hardwarelayer", "in onAnimationStart isHardwareAccelerated: " + groupMemberFragment.isHardwareAccelerated());
//                    groupMemberFragment.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("AnimationExp", "in onAnimationEnd, name: " + animation.toString());
                // mListener.changeMenuItemsForHomeFragmentToGroupMembersFragment();
                animationForPullerBottomListener.animatePullerBottom();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("AnimationExp", "in HomeFragment onAnimationRepeat(): ");

            }
        });
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.play(slideDownAnimation).with(animationForUpperView);
        animatorSet.start();
    }

    public static interface AnimationForPullerBottomListener {
        public void animatePullerBottom();
    }


}


