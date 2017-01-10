package com.example.babarmustafa.chatapplication.Chat_Work;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.babarmustafa.chatapplication.MainActivity;
import com.example.babarmustafa.chatapplication.R;
import com.example.babarmustafa.chatapplication.Signup_Adapter;
import com.example.babarmustafa.chatapplication.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Members extends Fragment {
    private ListView emailList;
    private ArrayList<User> messages;
    private Signup_Adapter listAdapter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private User uSer;
    ArrayList<String> list;
    User data;
    DatabaseReference database;
    String pId;
    private Context context;
    String push1;
    String push2;
    String push3;
    public HashMap<String, String> hashObj = new HashMap<>();
    public HashMap<String, String> hashObj2 = new HashMap<>();
    String friend_uid_on_clicked;
    String friend_pic;
    String friend_name;
    String friend_email;
    String friend_gender;

    Conver e;


    //toDO myTodo;


    public Members() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_members, container, false);


        list = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
if(mAuth.getCurrentUser().getUid() != null) {
    final String login_user = mAuth.getCurrentUser().getUid();
}
        database = FirebaseDatabase.getInstance().getReference();


        emailList = (ListView) view.findViewById(R.id.list_view);
        messages = new ArrayList<>();
        listAdapter = new Signup_Adapter(messages, getActivity());
        emailList.setAdapter(listAdapter);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    // User is signed in


                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Intent call = new Intent(getActivity(), MainActivity.class);
                    call.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(call);
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        //for cne to one
//        push2 = database.getRef().push().getKey();
//        push3 = database.getRef().push().getKey();
//        conversationId = database.getRef().push().getKey();
//        Toast.makeText(getActivity(), "" + conversationId, Toast.LENGTH_SHORT).show();
       // conversationId = database.getRef().push().getKey();

        emailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent conwindow = new Intent(getActivity(), ConversationActivity.class);
//


                friend_uid_on_clicked = list.get(position);
                friend_pic = messages.get(position).getProfile_image();
                friend_name = messages.get(position).getName();
                friend_gender = messages.get(position).getGEnder();
                friend_email =  messages.get(position).getEmail();


                conwindow.putExtra("friend_uid", friend_uid_on_clicked);
                conwindow.putExtra("friend_image", friend_pic);
                conwindow.putExtra("friend_name", friend_name);
                conwindow.putExtra("friend_email", friend_email);
                conwindow.putExtra("friend_gender", friend_gender);


                conwindow.putExtra("imp", friend_uid_on_clicked);
                startActivity(conwindow);
            }
        });


        emailList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                pId = list.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View vieww = LayoutInflater.from(getActivity()).inflate(R.layout.for_messages, null);
                final EditText mytext = (EditText) vieww.findViewById(R.id.formessages);

                builder.setTitle("Notifications window");
                builder.setMessage("Write the Meeage...?");
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String UUID = mAuth.getCurrentUser().getUid();
                        Log.d("v", "" + UUID);


                        // EditText mytext = (EditText) view.findViewById(R.id.formessages);

                        Log.d("v", "" + mytext.getText().toString());

                        NotificationMessage notificationMessage = new NotificationMessage(mytext.getText().toString(), database.child("Notifications").child(UUID).push().getKey().toString(), UUID);

/*
                        HashMap<String, String> result = new HashMap<>();
                        result.put("uid", UUID);
                        result.put("pushId", database.child("Notifications").child(UUID).push().getKey());
                        result.put("message", mytext.getText().toString());*/

                        database.child("Notifications").child(pId).child(notificationMessage.getPushId()).setValue(notificationMessage);

                    }
                });


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setView(vieww);
                builder.create().show();
                return true;
            }
        });


        FirebaseDatabase.getInstance().getReference().child("User Info").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever Data at this location is updated.
                data = dataSnapshot.getValue(User.class);
                list.add(data.getUID());
                // Log.v("DATA", "" + data.getId() + data.getName() + data.getCity());
                User email = new User(data.getUID(), data.getName(), data.getEmail(),  data.getPassword(),data.getGEnder(), data.getProfile_image());
                messages.add(email);
                listAdapter.notifyDataSetChanged();
//

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);


    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


//
}
