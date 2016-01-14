package com.lanshiliang.imessage;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listItem;

//    private HashMap<String,ArrayList<MsgInfo.Msg>> contentsMap;


    private MsgInfo msgInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listItem = (ListView) findViewById(R.id.list_msg);
        msgInfo = new MsgInfo(this);
        msgInfo.getSmsInPhone();
        MsgAdapter msgAdapter = new MsgAdapter(MainActivity.this,R.layout.msg_list_item,msgInfo.getItem());
        listItem.setAdapter(msgAdapter);
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
}
