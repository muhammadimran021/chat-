package com.example.babarmustafa.chatapplication.Chat_Work;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.babarmustafa.chatapplication.R;
import com.example.babarmustafa.chatapplication.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by BabarMustafa on 11/2/2016.
 */

public class MesagesAdapter extends BaseAdapter {

    private ArrayList<NotificationMessage> dataList;
    private Context context;
    private   User uid;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String extention;
    DownloadManager downloadManager;
    String d;

    public MesagesAdapter(ArrayList<NotificationMessage> dataList, Context context, User uid) {
        this.dataList = dataList;
        this.context = context;
        this.uid = uid;
    }

    public MesagesAdapter(ArrayList<NotificationMessage> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public MesagesAdapter(ArrayList<NotificationMessage> messages, ConversationActivity conversationActivity, String get_if_of_clicked_friend) {


    }

    public MesagesAdapter(ArrayList<NotificationMessage> messages, ConversationActivity conversationActivity, User friendData) {

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view;

        user = mAuth.getInstance().getCurrentUser();
        if (dataList.get(position).getUUID().equals(user.getUid())){
            view = inflater.inflate(R.layout.right_view, null);

        }
        else{
            view = inflater.inflate(R.layout.left_view, null);
        }
        // final View inflater = LayoutInflater.from(MainView.this).inflate(R.layout.for_messages,null);
//        View view = inflater.inflate(R.layout.right_view, null);
//
       TextView for_messages = (TextView) view.findViewById(R.id.mesages_view);
        ImageView for_s = (ImageView) view.findViewById(R.id.for_file_view);



        downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);


        final NotificationMessage data = dataList.get(position);

        String mssgs = data.getMessage();

       final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

        Pattern p = Pattern.compile(URL_REGEX);
        final Matcher muy = p.matcher(mssgs);//replace with string to compare
        if(muy.find()) {
//https://firebasestorage.googleapis.com/v0/b/chatapplication-f99c2.appspot.com/o/files%2Fprimary%3A.profig.os?alt=media&token=d38a794e-6c19-4e1f-8aa9-aa2f6a3fa639
//            for_messages.setVisibility(View.INVISIBLE);
           String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(mssgs);
            String finale_extention_of_file = "." +fileExtenstion;
            Toast.makeText(context, ""+finale_extention_of_file, Toast.LENGTH_SHORT).show();
           for_s.setVisibility(View.VISIBLE);
           // Picasso.with(context).load(mssgs).into(for_s);
            if(finale_extention_of_file.equals(".pdf")) {
                Glide.with(context)
                        .load(R.drawable.pdf_for)
                        .into(for_s);
            }
            else if(finale_extention_of_file.equals(".doc") || finale_extention_of_file.equals(".docx") ) {
                Glide.with(context)
                        .load(R.drawable.word_for)
                        .into(for_s);
            }
            else if(finale_extention_of_file.equals(".ppt") || finale_extention_of_file.equals(".pptx") ) {
                Glide.with(context)
                        .load(R.drawable.ppt_for)
                        .into(for_s);
            }
            else if(finale_extention_of_file.equals(".xls") || finale_extention_of_file.equals(".xlsx") ) {
                Glide.with(context)
                        .load(R.drawable.excel_for)
                        .into(for_s);
            }
            else if(finale_extention_of_file.equals(".rar") ) {
                Glide.with(context)
                        .load(R.drawable.rar)
                        .into(for_s);
            }
            else if(finale_extention_of_file.equals(".zip") ) {
                Glide.with(context)
                        .load(R.drawable.fpr_zip)
                        .into(for_s);
            }
            else if(finale_extention_of_file.equals(".txt") ) {
                Glide.with(context)
                        .load(R.drawable.re_text)
                        .into(for_s);
            }
            else if(finale_extention_of_file.equals(".psd") ) {
                Glide.with(context)
                        .load(R.drawable.psd)
                        .into(for_s);
            }
            else if(finale_extention_of_file.equals(".apk") ) {
                Glide.with(context)
                        .load(R.drawable.ap)
                        .into(for_s);
            }
            

            else if(finale_extention_of_file.equalsIgnoreCase(".jpg") ||finale_extention_of_file.equalsIgnoreCase(".png")
                    ||finale_extention_of_file.equalsIgnoreCase(".gif") ||
                    finale_extention_of_file.equalsIgnoreCase(".jpeg")) {
                Glide.with(context)
                        .load(mssgs)
                        .into(for_s);
            }
            else if(finale_extention_of_file.equalsIgnoreCase(".mp3") ||finale_extention_of_file.equalsIgnoreCase(".3ga")
                    ||finale_extention_of_file.equalsIgnoreCase(".amr") || finale_extention_of_file.equalsIgnoreCase(".amr") ||
                    finale_extention_of_file.equalsIgnoreCase(".m4a") || finale_extention_of_file.equalsIgnoreCase(".wav") ||
                    finale_extention_of_file.equalsIgnoreCase(".wma") )  {
                Glide.with(context)
                        .load(R.drawable.au)
                        .into(for_s);
            }
        else{
                Glide.with(context)
                        .load(R.drawable.for_aall)
                        .into(for_s);
            }

//            else {
//                Glide.with(context)
//                        .load(R.drawable.for_aall)
//                        .into(for_s);
//            }
            // d.equals(muy);


        }
        else {
            //Toast.makeText(context, "not a valid url", Toast.LENGTH_SHORT).show();
//            for_messages.setVisibility(View.VISIBLE);
            for_messages.setText(mssgs);
//            for_s.setVisibility(View.GONE);
        }


        for_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = dataList.get(position).getMessage();

                downloadFileNow("pdf", String.valueOf(d), "file");
            }
        });

        //to still the condition after changes
        final NotificationMessage todoChekd = (NotificationMessage) getItem(position);



        return view;
    }
    public void downloadFileNow(String fileType, String d, String fileName) {
        Uri uri = Uri.parse(d);
        DownloadManager.Request request = new DownloadManager.Request(uri);
//                set the notification
        request.setDescription("Downloading " + fileName).setTitle("Demo App");



        extention = String.valueOf(d);
        extention = extention.substring(extention.lastIndexOf(".")).substring(0,4);




        request.setDestinationInExternalPublicDir("/bbbbbb/Files", fileName + extention  );



//                make file visible by and manageable by system's download app
        request.setVisibleInDownloadsUi(true);

//                select which network, etc
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        //after download notification will show complete
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

//                queue the download
        downloadManager.enqueue(request);

    }

}
