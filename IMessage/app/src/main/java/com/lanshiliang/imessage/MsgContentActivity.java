package com.lanshiliang.imessage;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by lanshiliang on 2016/1/13.
 */
public class MsgContentActivity extends Activity {

    private ArrayList<MsgInfo.Msg> msgList ;
    private MsgInfo msgInfo;
    private ListView contentListView ;
    private TextView mnmName,mnmAddress;
    private MsgContentAdapter msgContentAdapter;
    private LinearLayout contentName;
    private Button send;
    private EditText input;
    private IntentFilter sendFilter;
    private SendStatusReceiver sendStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_content_main);
        contentListView = (ListView) findViewById(R.id.content_list_view);
        mnmName = (TextView) findViewById(R.id.mnm_name);
        mnmAddress = (TextView) findViewById(R.id.mnm_address);
        input = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgInfo = new MsgInfo(this);
        msgInfo.getSmsInPhone();
        readContacts();
        String address = getIntent().getStringExtra("msg"); //获取手机号码
        msgList = msgInfo.getContentsMap().get(address);
        Collections.reverse(msgList);//按时间顺序给短信排序
        msgContentAdapter = new MsgContentAdapter(this,R.layout.msg_item,msgList);
        mnmAddress.setText(address);
        contentName = (LinearLayout) findViewById(R.id.content_name);
        if(contacts.containsKey(address)){
            mnmName.setText(contacts.get(address));
        }
        else {
            contentName.setVisibility(View.GONE);
            mnmAddress.setTextSize(25);
        }
        contentListView.setAdapter(msgContentAdapter);
        contentListView.setSelection(msgList.size());
        sendFilter = new IntentFilter();
        sendFilter.addAction("SEND_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver, sendFilter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                Intent sendIntent = new Intent("SEND_SMS_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast(MsgContentActivity.this,0,sendIntent,0);
                smsManager.sendTextMessage(msgList.get(0).getStrAddress(),
                        null,input.getText().toString(),pi,null);
                msgInfo.getSmsInPhone();
                msgContentAdapter.notifyDataSetChanged();
            }
        });
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sendStatusReceiver);
    }

    private Map<String,String> contacts =new HashMap<String,String>();
    public  void readContacts(){
        Cursor cursor=null;
        try{
            cursor=this.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null);
            while(cursor.moveToNext()){

                String displayName=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contacts.put(number,displayName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null) cursor.close();
        }
    }

    private class SendStatusReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode() == RESULT_OK){
                Toast.makeText(context,"Send succeeded ",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context,"Send failed ",Toast.LENGTH_LONG).show();

            }
        }
    }
}
