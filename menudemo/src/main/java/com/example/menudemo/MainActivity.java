package com.example.menudemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateDatabase();


//        SQLiteDatabase db = MenuDao.getInstance(this);
//        Cursor c = db.query("dish_tb",null,null,null,null,null,null);
//        while(c.moveToNext()) {
//            int id = c.getInt(0);
//            String name = c.getString(1);
//            String type = c.getString(2);
//            Log.e("TAG",name);
//        }
    }

    public void generateDatabase(){
        try {
            InputStream in = getAssets().open("menu.db");
            byte[] b = new byte[1024*512];
            int len = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            File f = new File(Environment.getExternalStorageDirectory() + "/imooc_menu.db");
            if(!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f);
            while ((len = in.read(b))>0){
                baos.write(b,0,len);
            }
            fos.write(baos.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
