package com.example.babarmustafa.chatapplication.Chat_Work;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babarmustafa.chatapplication.MainActivity;
import com.example.babarmustafa.chatapplication.R;
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


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class Chat_Main_View extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabAdapter adapter;
    private ArrayList<Fragment> mFragmentArrayList;
    private One mTab1;
    private Two mTab2;
    private Members mTab3;
    private static int Gallery_Request = 1;
    private static  int CAMERA_REQUEST =1;
    Intent intent_of_gallery,intent_of_camera;
    private Uri mImageUri = null;
    private StorageReference mStoarge;
    private DatabaseReference databaseReference;
    CircularImageView iv;
    Button profile;
    ProgressDialog progres;
    LinearLayout linear;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    String name;
    private  TextView show_name;
    private String photoUrl = null;
//    CircularImageView circularImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__main__view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //starting service
        Intent t = new Intent(Chat_Main_View.this,PushService.class);
        startService(t);
        mStoarge = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User Info");



        mAuth = FirebaseAuth.getInstance();
        final String login_user = mAuth.getCurrentUser().getUid();

        //forlogout

        FirebaseDatabase.getInstance().getReference().child("User Info").child(login_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Map<String, String> map = (Map)dataSnapshot.getValue();

                photoUrl =map.get("Profile_image");
               name = map.get("Name");
                Toast.makeText(Chat_Main_View.this, ""+name, Toast.LENGTH_SHORT).show();

                if(!TextUtils.isEmpty(photoUrl)) {

                    Picasso.with(Chat_Main_View.this).load(photoUrl).into(iv);
                }
                 if (!TextUtils.isEmpty(name)){
                    show_name.setText(name);

                }



                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        progres = new ProgressDialog(this);

        //fragments work
        mTab1 = new One();
        mTab2 = new Two();
        mTab3 = new Members();

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mFragmentArrayList = new ArrayList<>();

          /*
        * add Fragment to ArrayList
        */
        mFragmentArrayList.add(mTab1);
        mFragmentArrayList.add(mTab2);
        mFragmentArrayList.add(mTab3);

        mTabLayout.addTab(mTabLayout.newTab().setText("TAB 1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("TAB 2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Members"));

        adapter = new TabAdapter(getSupportFragmentManager(), mFragmentArrayList);

        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mViewPager.setOffscreenPageLimit(0);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               mViewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        new One();
                        break;
                    case 1:
                        new Two();
                        break;
                    case 3:
                        new Members();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();





        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View hView =  navigationView.getHeaderView(0);
        show_name = (TextView) hView.findViewById(R.id.username_view);






        profile = (Button) hView.findViewById(R.id.im);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Chat_Main_View.this, "test tost", Toast.LENGTH_SHORT).show();
                progres.setMessage("uploading...");
                progres.show();

                StorageReference filepath = mStoarge.child("Images").child(mImageUri.getLastPathSegment());

                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        DatabaseReference database_reference = databaseReference.child(mAuth.getCurrentUser().getUid());

                        database_reference.child("Profile_image").setValue(downloadUrl.toString());

                        Toast.makeText(Chat_Main_View.this, "Upload image succesfully", Toast.LENGTH_SHORT).show();


                        progres.dismiss();
                    }
                });
            }
        });

        //


        //profile picture work
iv=(CircularImageView)hView.findViewById(R.id.cirular_view);
iv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(Chat_Main_View.this, "clicked", Toast.LENGTH_SHORT).show();
    AlertDialog.Builder builder = new AlertDialog.Builder(Chat_Main_View.this);
        builder.setTitle("Profile pic Menu.....");
        builder.setMessage("Choose your image from the folloeing");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getBaseContext().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 111);
                }
            }
        });

        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent_of_gallery = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent_of_gallery, Gallery_Request);
            }
        });
        builder.create().show();

    }
});




    }


    @Override
    protected void onStart() {
        super.onStart();
//        Picasso.with(Chat_Main_View.this).load(String.valueOf(user_profile_image)).into(iv);
//        mAuth.addAuthStateListener(mAuthListener);
    }


    //crop work
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Request && resultCode == RESULT_OK) {
            Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri = result.getUri();


                iv.setImageURI(mImageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }


        }
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            //saves the pic locally
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            StorageReference imagesRef = mStoarge.child("caa.jpg");

            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(Chat_Main_View.this, "Failed to upload camera", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(Chat_Main_View.this, "upload camera", Toast.LENGTH_SHORT).show();
                    DatabaseReference database_reference = databaseReference.child(mAuth.getCurrentUser().getUid());

                    database_reference.child("Profile_image").setValue(downrl.toString());
                   // for_message.setText(downrl.toString());
                    iv.setImageURI(downrl);
                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat__main__view, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logOut();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        }



        else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
