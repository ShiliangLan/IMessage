package com.lanshiliang.imessage;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listItem;
    private List <MsgInfo.Msg> list;
    private MsgAdapter msgAdapter;

//    private HashMap<String,ArrayList<MsgInfo.Msg>> contentsMap;


    private MsgInfo msgInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listItem = (ListView) findViewById(R.id.list_msg);
        msgInfo = new MsgInfo(this);
        list =msgInfo.getItem();
        msgAdapter = new MsgAdapter(MainActivity.this,R.layout.msg_list_item,list);
        listItem.setAdapter(msgAdapter);
        //注册Listener ，监听数据库变化
        getContentResolver().registerContentObserver(
                Uri.parse("content://sms"), true, new SmsObserver(new Handler()));
        listItem.setOnItemClickListener(this);
//        contentsMap = msgInfo.getContentsMap();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String address = msgInfo.getItem().get(position).getStrAddress();
        Intent intent = new Intent(MainActivity.this,MsgContentActivity.class);
        intent.putExtra("msg",address);
//        System.out.println(address);
        startActivity(intent);
    }


    private  class SmsObserver extends ContentObserver {
        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public SmsObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            msgInfo.getItem(); //更新adapter数据
            msgAdapter.notifyDataSetChanged();
            super.onChange(selfChange);
        }
    }
}
