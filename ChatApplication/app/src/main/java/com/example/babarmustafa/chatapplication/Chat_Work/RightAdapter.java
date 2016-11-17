package com.example.babarmustafa.chatapplication.Chat_Work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babarmustafa.chatapplication.R;
import com.example.babarmustafa.chatapplication.User;

import java.util.ArrayList;

/**
 * Created by BabarMustafa on 11/2/2016.
 */

public class RightAdapter extends BaseAdapter {

    private ArrayList<NotificationMessage> dataList;
    private Context context;
    private   User uid;

    public RightAdapter(ArrayList<NotificationMessage> dataList, Context context, User uid) {
        this.dataList = dataList;
        this.context = context;
        this.uid = uid;
    }

    public RightAdapter(ArrayList<NotificationMessage> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public RightAdapter(ArrayList<NotificationMessage> messages, ConversationActivity conversationActivity, String get_if_of_clicked_friend) {


    }

    public RightAdapter(ArrayList<NotificationMessage> messages, ConversationActivity conversationActivity, User friendData) {

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
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        // final View inflater = LayoutInflater.from(MainView.this).inflate(R.layout.for_messages,null);
        View view = inflater.inflate(R.layout.right_view, null);

        TextView for_messages = (TextView) view.findViewById(R.id.mesages_view);

        final NotificationMessage data = dataList.get(position);
        String mssgs = data.getMessage();
       
        //to still the condition after changes
        final NotificationMessage todoChekd = (NotificationMessage) getItem(position);

        for_messages.setText(mssgs);

        return view;
    }
}
