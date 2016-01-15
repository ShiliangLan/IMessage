package com.lanshiliang.imessage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lanshiliang on 2016/1/12.
 */
public class MsgInfo {
    final String SMS_URI_ALL = "content://sms/";

    private Context context;

    private List<Msg> item = new ArrayList<Msg>();

    private HashMap<String,ArrayList<Msg>> contentsMap =new HashMap<String,ArrayList<Msg>>();

    public List<Msg> getItem() {
        return item;
    }

    public MsgInfo(Context context) {
        this.context = context;
    }

    public  ArrayList<Msg> getListMsg(String address) {
        Collections.reverse(contentsMap.get(address));
        return contentsMap.get(address);
    }

    public void getSmsView(){

    }
    public void getSmsInPhone() {
        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = context.getContentResolver().query(uri, null, null, null, "date desc");
            while (cur.moveToNext()){
                int intPerson = cur.getInt(cur.getColumnIndex("person"));
                String strAddress = cur.getString(cur.getColumnIndex("address"));
                String strBody = cur.getString(cur.getColumnIndex("body"));
                long longDate = cur.getLong(cur.getColumnIndex("date"));
                int intType = cur.getInt(cur.getColumnIndex("type"));
                Msg msg =new Msg(strAddress,intPerson,strBody,longDate,intType);
                if(!contentsMap.containsKey(strAddress)){
                    contentsMap.put(strAddress, new ArrayList<Msg>());
                    item.add(msg);
                }
                contentsMap.get(strAddress).add(msg);
            }
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }
    class Msg{
        private String strAddress ;
        private int intPerson ;
        private String strBody ;
        private long longDate ;
        private int intType ;

        public Msg(String strAddress, int intPerson, String strBody, long longDate, int intType) {
            this.strAddress = strAddress;
            this.intPerson = intPerson;
            this.strBody = strBody;
            this.longDate = longDate;
            this.intType = intType;
        }

        public String getStrAddress() {
            return strAddress;
        }

        public int getIntPerson() {
            return intPerson;
        }

        public String getStrBody() {
            return strBody;
        }

        public String getLongDate() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = new Date(longDate);
            String strDate = dateFormat.format(d);

            return strDate;
        }

        public int getIntType() {
            return intType;
        }
    }
}