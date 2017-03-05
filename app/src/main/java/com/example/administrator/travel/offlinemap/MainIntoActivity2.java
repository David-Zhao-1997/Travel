package com.example.administrator.travel.offlinemap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.administrator.travel.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainIntoActivity2 extends AppCompatActivity
{
    private ImageView mSpring;
    private ImageView mSummer;
    private ImageView mAutumn;
    private ImageView mWinter;
    String url = "http://www.davidzhao.cn/pic/badaguan-win.png";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_into2);
     String url_Summer= getIntent().getExtras().getString("Summer");

        ImageView mSummer = (ImageView) findViewById(R.id.spot_summer);

        loadImage(url_Summer, mSummer);



    }

    public void loadImage(String url, ImageView view)
    {
        NetService netService = new NetService(url);
        netService.start();
        Bitmap bitmap;
        while ((bitmap = netService.getBitmap()) == null)
        {
//            try
//            {
//                Thread.sleep(50);
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
        }
        view.setImageBitmap(bitmap);
    }

    class NetService extends Thread
    {
        String url;
        Bitmap bitmap = null;


        public Bitmap getBitmap()
        {
            return bitmap;
        }

        NetService(String url)
        {
            this.url = url;
        }

        @Override
        public void run()
        {
            URL myFileURL;
            try
            {
                myFileURL = new URL(url);
                //获得连接
                HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
                //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
                conn.setConnectTimeout(0);
                //连接设置获得数据流
                conn.setDoInput(true);
                //不使用缓存
                conn.setUseCaches(true);
                //这句可有可无，没有影响
                conn.connect();
                //得到数据流
                InputStream is = conn.getInputStream();
                //解析得到图片
                bitmap = BitmapFactory.decodeStream(is);
                //关闭数据流
                is.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getHttpBitmap(String url)
    {
        URL myFileURL;
        Bitmap bitmap = null;
        try
        {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return bitmap;

    }
}
