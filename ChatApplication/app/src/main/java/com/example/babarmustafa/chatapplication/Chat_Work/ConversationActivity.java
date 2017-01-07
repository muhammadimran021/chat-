package com.example.babarmustafa.chatapplication.Chat_Work;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.babarmustafa.chatapplication.R;
import com.example.babarmustafa.chatapplication.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ConversationActivity extends Activity {

    EditText for_message;
    ListView conversation;
    Button to_send;

    CircularImageView for_user_image_on_toolbar;
    TextView for_user_name_selected_for_chat;


    DatabaseReference database;
    private FirebaseAuth mAuth;


    ArrayList<String> list ;

    User f_data;
    String UUID;
    String get_f_id_on_clicked;
    String get_f_pic;
    String get_f_name;
    String get_f_email;
    String get_f_gender;
    Intent intent_of_gallery;
    private static int Gallery_Request = 1;
    private boolean chooseFlag;
    private boolean flag = true;
    private String conversationPushRef;
    private Conver conversationData;
    private boolean isConversationOld = false;
    private static final int SAVE_REQUEST_CODE = 1;

    public HashMap<String, String> hashObj = new HashMap<>();
    public HashMap<String, String> hashObj2 = new HashMap<>();
    NotificationMessage notificationMessage;

    MesagesAdapter listadapter;
    private ArrayList<NotificationMessage> messages;
    private FirebaseUser user;
    private StorageReference mStoarge;
    private Uri mImageUri = null;
    private Uri ImageUri = null;

   Uri downloadUrl;
    Uri downloadl;

    ImageButton for_file_sharing;
    ImageButton for_image_sharing;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);



        for_message = (EditText) findViewById(R.id.editMessage);
        to_send = (Button) findViewById(R.id.button_Send);
       // back_to_main = (ImageButton) findViewById(R.id.throw_back);
        conversation = (ListView) findViewById(R.id.messages_conversation);
        for_user_image_on_toolbar = (CircularImageView) findViewById(R.id.user_pic);
        for_user_name_selected_for_chat = (TextView) findViewById(R.id.selected);
        for_file_sharing = (ImageButton) findViewById(R.id.for_files);
        for_image_sharing = (ImageButton) findViewById(R.id.for_images);


        mStoarge = FirebaseStorage.getInstance().getReference();


        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        list=new ArrayList<>();
        messages = new ArrayList<>();
        listadapter = new MesagesAdapter(messages,ConversationActivity.this);
        conversation.setAdapter(listadapter);
        //for hiding gridlines
        conversation.setDivider(null);
        conversation.setDividerHeight(0);
        user = mAuth.getInstance().getCurrentUser();
        UUID =  mAuth.getCurrentUser().getUid();



        get_f_id_on_clicked = getIntent().getStringExtra("friend_uid");
        get_f_pic = getIntent().getStringExtra("friend_image");
        get_f_name = getIntent().getStringExtra("friend_name");
        get_f_email = getIntent().getStringExtra("friend_email");
        get_f_gender = getIntent().getStringExtra("friend_gender");


        Picasso.with(ConversationActivity.this).load(get_f_pic).into(for_user_image_on_toolbar);
        for_user_name_selected_for_chat.setText(get_f_name);
        checkConversationNewOROLD();


for_file_sharing.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/*");
        startActivityForResult(intent, SAVE_REQUEST_CODE);
    }
});
        for_image_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                intent_of_gallery = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent_of_gallery, Gallery_Request);
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
        final Conver tempRefObj = new Conver(conversationPushRef ,get_f_id_on_clicked);


        database.child("user_conv").child(UUID).push().setValue(tempRefObj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    tempRefObj.setUserid(mAuth.getCurrentUser().getUid());
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
                String eee =for_message.getText().toString();
                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                if (for_message.getText().length() > 1) {
                    NotificationMessage m = new NotificationMessage();
                    m.setMessage(eee);
                    m.setTime(mydate);
                    m.setUUID(user.getUid());
                    database.child("conversation").child(conversationPushRef).push().setValue(m);

                    for_message.setText("");
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        // TODO Auto-generated method stub
        //if (resultCode == ConversationActivity.this.RESULT_OK && requestCode == Gallery_Request){}

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "no file selected", Toast.LENGTH_SHORT).show();
            return;
        } else if (requestCode == SAVE_REQUEST_CODE  && resultCode == RESULT_OK) {
            String FilePath = data.getData().getPath();

            mImageUri = data.getData();
            StorageReference filepath = mStoarge.child("files").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    downloadUrl = taskSnapshot.getDownloadUrl();

                    Toast.makeText(ConversationActivity.this, "Upload File succesfully", Toast.LENGTH_SHORT).show();
                    for_message.setText(downloadUrl.toString());


                }

            });

            filepath.putFile(mImageUri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ConversationActivity.this, "failed to upload", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (requestCode == Gallery_Request && resultCode == RESULT_OK) {
            Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
            StorageReference filath = mStoarge.child("Imes").child(ImageUri.getLastPathSegment());
            filath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    downloadl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(ConversationActivity.this, "" + downloadl, Toast.LENGTH_SHORT).show();


//
                    //  DatabaseReference database_reference = databaseReference.push();
//
//                        database_reference.child("images").setValue(downloadUrl.toString());
                    Toast.makeText(ConversationActivity.this, "Upload image succesfully", Toast.LENGTH_SHORT).show();

                }
            });

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                ImageUri = result.getUri();
//                       for_s.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }


        }


    }


}
