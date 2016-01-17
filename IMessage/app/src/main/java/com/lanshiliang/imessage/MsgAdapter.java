package com.lanshiliang.imessage;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img_icn);
            viewHolder.name = (TextView) view.findViewById(R.id.msg_name);
            viewHolder.date = (TextView) view.findViewById(R.id.msg_date);
            viewHolder.content = (TextView) view.findViewById(R.id.mag_content);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        if (msg.getName() != ""){
            viewHolder.name.setText(msg.getName());
        }else {
            viewHolder.name.setText(msg.getStrAddress());
        }
        String shortDate []= msg.getLongDate().split("-| ");
        viewHolder.date.setText(shortDate[1]+"-"+shortDate[2]);
        viewHolder.content.setText(msg.getStrBody());
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView  name ,date ,content;
    }

}
