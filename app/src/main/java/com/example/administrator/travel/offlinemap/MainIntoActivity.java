package com.example.administrator.travel.offlinemap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.example.administrator.travel.NaviActivity;
import com.example.administrator.travel.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainIntoActivity extends AppCompatActivity
{
    private ImageView mSpring;
    private ImageView mSummer;
    private ImageView mAutumn;
    private ImageView mWinter;
    private Button mButton;
    private TextView desctiption;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_into);
        String url_Summer = getIntent().getExtras().getString("Summer");
        String url_Autumn = getIntent().getExtras().getString("Autumn");
        String url_Spring = getIntent().getExtras().getString("Spring");
        String url_Winter = getIntent().getExtras().getString("Winter");
//        String url = "http://www.davidzhao.cn/pic/badaguan-win.png";
        String url = url_Winter;
        mButton = (Button) findViewById(R.id.share_life_btn);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent mIntent = new Intent(MainIntoActivity.this, NaviActivity.class);
                startActivity(mIntent);
            }
        });
        desctiption = (TextView) findViewById(R.id.description);
//        desctiption.getSettings().setJavaScriptEnabled(true);
//        desctiption.loadUrl("http://www.baidu.com");
//        setContentView(desctiption);
        ImageView mSummer = (ImageView) findViewById(R.id.spot_summer);
        ImageView mAutumn = (ImageView) findViewById(R.id.spot_autumn);
        ImageView mSpring = (ImageView) findViewById(R.id.spot_spring);
        ImageView mWinter = (ImageView) findViewById(R.id.spot_winter);
        loadImage(url_Summer, mSummer);
        loadImage(url_Autumn, mAutumn);
        loadImage(url_Spring, mSpring);
        loadImage(url_Winter, mWinter);


        String url_des = url.replace("-win.png", ".txt");
        DesFetch fetch = new DesFetch(url_des);
        fetch.start();
        String des = null;
        while ((des = fetch.getDes()) == null)
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
        desctiption.setText(des);
//        desctiption.setText("hehe");
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

    class DesFetch extends Thread
    {
        String url;
        String des = null;

        DesFetch(String url)
        {
            this.url = url;
        }

        public String getDes()
        {
            return des;
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
                BufferedReader bufr = new BufferedReader(new InputStreamReader(is));
                StringBuffer stringBuffer = new StringBuffer();
                String s = null;
                while ((s = bufr.readLine()) != null)
                {
                    stringBuffer.append(s);
                }
                des = stringBuffer.toString();
                System.out.println(des);
                //关闭数据流
                is.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
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
