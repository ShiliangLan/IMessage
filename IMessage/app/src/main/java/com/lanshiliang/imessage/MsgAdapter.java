package com.lanshiliang.imessage;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lanshiliang on 2016/1/12.
 */
public class MsgAdapter extends ArrayAdapter<MsgInfo.Msg> {

    private int resourceId;

    public MsgAdapter(Context context, int resource, List<MsgInfo.Msg> objects) {

        super(context, resource, objects);
        resourceId = resource;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MsgInfo.Msg  msg=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView img = (ImageView) view.findViewById(R.id.img_icn);
        TextView name = (TextView) view.findViewById(R.id.msg_name);
        TextView date = (TextView) view.findViewById(R.id.msg_date);
        TextView content = (TextView) view.findViewById(R.id.mag_content);
        img.setImageResource(R.mipmap.ic_launcher);
        if (msg.getName() != ""){
            name.setText(msg.getName());
        }else {
            name.setText(msg.getStrAddress());
        }
        String shortDate []= msg.getLongDate().split("-| ");
        date.setText(shortDate[1]+"-"+shortDate[2]);
        content.setText(msg.getStrBody());
        return view;
    }

}
