package com.lanshiliang.imessage;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listItem;

    private MsgInfo msgInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listItem = (ListView) findViewById(R.id.list_msg);
        System.out.println("1");
        msgInfo = new MsgInfo(this);
        msgInfo.getSmsInPhone();

        System.out.println(msgInfo.getItem());
        MsgAdapter msgAdapter = new MsgAdapter(MainActivity.this,R.layout.msg_list_item,msgInfo.getItem());
        System.out.println("3");
        listItem.setAdapter(msgAdapter);
        System.out.println("4");

    }
}
