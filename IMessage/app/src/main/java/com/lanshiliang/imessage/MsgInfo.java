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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lanshiliang on 2016/1/12.
 */
public class MsgInfo {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d ;

    final String SMS_URI_ALL = "content://sms/";

    private Context context;

    private List<Msg> item =new ArrayList<>() ;

    private Set<String> addSet =new HashSet<>();
    private HashMap<String,ArrayList<Msg>> contentsMap =new HashMap<String,ArrayList<Msg>>();

    public List<Msg> getItem() {

        return item;
    }

    public MsgInfo(Context context) {
        this.context = context;
        readContacts();
    }

    public  ArrayList<Msg> getListMsg(String address) {
        Collections.reverse(contentsMap.get(address));
        return contentsMap.get(address);
    }

    private Map<String,String> contacts =new HashMap<String,String>();
    public  void readContacts(){
        Cursor cursor=null;
        try{
            cursor=context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null);
            while(cursor.moveToNext()){

                String displayName=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                contactsList.add(displayName+"\n"+number);
                contacts.put(number,displayName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null) cursor.close();
        }
    }
    public void getContentAdapter() {
        contentsMap.clear();
        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = context.getContentResolver().query(uri, null, null, null, "date desc");
            while (cur.moveToNext()){
                String strAddress = cur.getString(cur.getColumnIndex("address"));
                String strBody = cur.getString(cur.getColumnIndex("body"));
                long longDate = cur.getLong(cur.getColumnIndex("date"));
                int intType = cur.getInt(cur.getColumnIndex("type"));
                String name = "";
                if( contacts.containsKey(strAddress)){
                    name =contacts.get(strAddress);
                }
                Msg msg =new Msg(strAddress,strBody,longDate,intType,name);
                if(!contentsMap.containsKey(strAddress)){
                    contentsMap.put(strAddress, new ArrayList<Msg>());
                }
                contentsMap.get(strAddress).add(msg);
            }
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }
    public void getMainAdapter() {
        addSet.clear();
        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = context.getContentResolver().query(uri, null, null, null, "date desc");
            while (cur.moveToNext()){
                int intPerson = cur.getInt(cur.getColumnIndex("person"));
                String strAddress = cur.getString(cur.getColumnIndex("address"));
                String strBody = cur.getString(cur.getColumnIndex("body"));
                long longDate = cur.getLong(cur.getColumnIndex("date"));
                int intType = cur.getInt(cur.getColumnIndex("type"));
                String name = "";

                if( contacts.containsKey(strAddress)){
                    name =contacts.get(strAddress);
                }
                Msg msg =new Msg(strAddress,strBody,longDate,intType,name);
                if(!addSet.contains(strAddress)){
                    addSet.add(strAddress);
                    item.add(msg);
                }
            }
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }
    class Msg{
        private String strAddress ;
        private String name ;
        private String strBody ;
        private long longDate ;
        private int intType ;

        public Msg(String strAddress, String strBody, long longDate, int intType,String name) {
            this.strAddress = strAddress;
            this.name = name;
            this.strBody = strBody;
            this.longDate = longDate;
            this.intType = intType;
        }

        public String getName() {
            return name;
        }

        public String getStrAddress() {
            return strAddress;
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