package com.lanshiliang.imessage;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;



/**
 * Created by lanshiliang on 2016/1/13.
 */
public class MsgContentActivity extends Activity {

    private String address;
    private List<MsgInfo.Msg> msgList ;
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
        address = getIntent().getStringExtra("msg"); //获取手机号码
        msgList = msgInfo.getListMsg(address);
//        Collections.reverse(msgList);//按时间顺序给短信排序
        msgContentAdapter = new MsgContentAdapter(this,R.layout.msg_item,msgList);
        mnmAddress.setText(address);
        contentName = (LinearLayout) findViewById(R.id.content_name);
        if(msgList.get(0).getName() != ""){ //如果号码不在联系人中
            mnmName.setText(msgList.get(0).getName());
        }
        else {
            contentName.setVisibility(View.GONE);
            mnmAddress.setTextSize(25);
        }
        contentListView.setAdapter(msgContentAdapter);
        contentListView.setSelection(msgList.size());
        //注册Listener ，监听数据库变化
        getContentResolver().registerContentObserver(
                Uri.parse("content://sms"), true, new SmsObserver(new Handler()));
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
                        null, input.getText().toString(), pi, null);
                Toast.makeText(MsgContentActivity.this,"发送中……",Toast.LENGTH_LONG).show();

            }
        });
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sendStatusReceiver);
    }


    private class SendStatusReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode() == RESULT_OK){
                Toast.makeText(context,"发送成功",Toast.LENGTH_LONG).show();
                input.setText("");
            }
            else {
                Toast.makeText(context,"发送失败",Toast.LENGTH_LONG).show();
            }
        }
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
            msgInfo.getListMsg(address); //更新adapter数据
            msgContentAdapter.notifyDataSetChanged();
            contentListView.setSelection(msgList.size());
            super.onChange(selfChange);
        }
    }
}
