package com.lanshiliang.imessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lanshiliang on 2016/1/13.
 */
public class MsgContentAdapter extends ArrayAdapter<MsgInfo.Msg> {
    int resourceId ;

    public MsgContentAdapter(Context context, int resource, List<MsgInfo.Msg> objects) {
        super(context, resource, objects);
        resourceId =resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MsgInfo.Msg  msg=getItem(position);
        View view;
        ViewHolder viewHolder;
        String [] date = msg.getLongDate().split("-| ");
        if (convertView == null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.leftLayout= (LinearLayout) view.findViewById(R.id.left_layout);
            viewHolder.rightLayout= (LinearLayout) view.findViewById(R.id.right_layout);
            viewHolder.leftMsg= (TextView) view.findViewById(R.id.left_msg);
            viewHolder.rightMsg= (TextView) view.findViewById(R.id.right_msg);
            viewHolder.leftDate= (TextView) view.findViewById(R.id.left_date);
            viewHolder.rightDate= (TextView) view.findViewById(R.id.right_date);
            view.setTag(viewHolder);
        }
        else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        if (msg.getIntType() == 1){
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getStrBody());
            viewHolder.leftDate.setText(date[3]);
        } else if(msg.getIntType() == 2){
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(msg.getStrBody());
            viewHolder.rightDate.setText(date[3]);
        }

        return view;
    }


    class ViewHolder {
        LinearLayout leftLayout,rightLayout;
        TextView leftMsg,rightMsg,leftDate,rightDate;

    }
}
