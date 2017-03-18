package com.example.administrator.travel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.travel.offlinemap.MainIntoActivity;

import java.util.ArrayList;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.BaseViewHolder>
{
    private ArrayList<String> dataList = new ArrayList<>();
    private Resources res;
    private static int i = 0;

    public void replaceAll(ArrayList<String> list)
    {
        dataList.clear();
        if (list != null && list.size() > 0)
        {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public DemoAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.one, parent, false));
    }

    @Override
    public void onBindViewHolder(DemoAdapter.BaseViewHolder holder, int position)
    {
        holder.setData(dataList.get(position));
    }


    @Override
    public int getItemCount()
    {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder
    {

        public BaseViewHolder(View itemView)
        {
            super(itemView);
        }

        void setData(Object data)
        {
        }
    }

    private class OneViewHolder extends BaseViewHolder
    {
        private ImageView ivImage;
        private ImageButton ok;
        private ImageButton mark;

        public OneViewHolder(View view)
        {
            super(view);
            ivImage = (ImageView) view.findViewById(R.id.ivImage);
            ok = (ImageButton) view.findViewById(R.id.ok);
            mark = (ImageButton) view.findViewById(R.id.mark);
            Context context = MainActivity.mainActivity.getBaseContext();
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;
//            int width = context.getWindowManager().getDefaultDisplay().getWidth();
//            int width = 1440;//TODO 修改
            ViewGroup.LayoutParams params = ivImage.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = width / 2;
            params.height = (int) (200 + Math.random() * 400);
            ivImage.setLayoutParams(params);
            res = itemView.getContext().getResources();
        }

        @Override
        void setData(Object data)
        {
            if (data != null)
            {
                String text = (String) data;
                final String final_text = text.split("/")[text.split("/").length - 1];
                /*
                加载点赞信息
                 */
                while (MainActivity.mainActivity.LikeMap == null)
                {
                }
                System.out.println("get:" + MainActivity.mainActivity.LikeMap.get(final_text));
                if (MainActivity.mainActivity.LikeMap.get(final_text).flag)
                {
                    System.out.println("设置图片");
                    ok.setImageDrawable(MainActivity.mainActivity.getResources().getDrawable(R.drawable.ok_act));
                }

                /*
                加载点赞信息
                 */
//                System.out.println(text);
                Glide.with(itemView.getContext()).load(text).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher).crossFade().into(ivImage);
                System.out.println(text);
//                ivText.setOnClickListener(new clikListener(text));
                //TODO 这里需要重新设置监听器
                ok.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        SharedPreferences sharedPreferences1 = MainActivity.mainActivity.getSharedPreferences(
                                "user_config", Context.MODE_PRIVATE);
                        String username = sharedPreferences1.getString("username", null);
//                        MainActivity.mainActivity.setContentView(R.layout.activity_remark);
//                        Toast.makeText(MainActivity.mainActivity, username + ":点赞:" + final_text, Toast.LENGTH_SHORT).show();
                        if (MainActivity.mainActivity.LikeMap.get(final_text).flag)
                        {
//                            System.out.println("取消赞")；
                            ok.setImageDrawable(MainActivity.mainActivity.getResources().getDrawable(R.drawable.ok_idle));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("obj", new LikeMessage(final_text,MainActivity.mainActivity.getUserName()));
                            Intent intent = new Intent(MainActivity.mainActivity, MyService.class);
                            intent.putExtras(bundle);
                            intent.putExtra("MSG",1);
                            MainActivity.mainActivity.startService(intent);

                        }
                        else
                        {
//                            System.out.println("点赞");
                            ok.setImageDrawable(MainActivity.mainActivity.getResources().getDrawable(R.drawable.ok_act));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("obj", new DislikeMessage(final_text,MainActivity.mainActivity.getUserName()));
                            Intent intent = new Intent(MainActivity.mainActivity, MyService.class);
                            intent.putExtras(bundle);
                            intent.putExtra("MSG",1);
                            MainActivity.mainActivity.startService(intent);
                        }
                    }
                });
                mark.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        SharedPreferences sharedPreferences1 = MainActivity.mainActivity.getSharedPreferences(
                                "user_config", Context.MODE_PRIVATE);
                        String username = sharedPreferences1.getString("username", null);
//                        MainActivity.mainActivity.setContentView(R.layout.activity_remark);
                        Toast.makeText(MainActivity.mainActivity, username + ":评论:" + final_text, Toast.LENGTH_SHORT).show();
                    }
                });
                //TODO 这里需要重新设置监听器
                ivImage.setOnClickListener(new clikListener(text));
                Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
                //异步获得bitmap图片颜色值
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener()
                {
                    @Override
                    public void onGenerated(Palette palette)
                    {
                        Palette.Swatch vibrant = palette.getVibrantSwatch();//有活力
                        Palette.Swatch c = palette.getDarkVibrantSwatch();//有活力 暗色
                        Palette.Swatch d = palette.getLightVibrantSwatch();//有活力 亮色
                        Palette.Swatch f = palette.getMutedSwatch();//柔和
                        Palette.Swatch a = palette.getDarkMutedSwatch();//柔和 暗色
                        Palette.Swatch b = palette.getLightMutedSwatch();//柔和 亮色

                        if (vibrant != null)
                        {
                            int color1 = vibrant.getBodyTextColor();//内容颜色
                            int color2 = vibrant.getTitleTextColor();//标题颜色
                            int color3 = vibrant.getRgb();//rgb颜色

                            ivImage.setBackgroundColor(
                                    vibrant.getRgb());

                        }
                    }
                });
            }
        }
    }

    public class clikListener implements View.OnClickListener
    {
        String url_Autumn = "";
        String url_Summer = "";
        String url_Spring = "";
        String url_Winter = "";

        public clikListener(String text)
        {
            url_Winter = text;
            url_Autumn = text.replace("win", "aut");
            url_Summer = text.replace("win", "sum");
            url_Spring = text.replace("win", "spr");

        }

        @Override
        public void onClick(View view)
        {
            Intent mintent = new Intent(MainActivity.mainActivity, MainIntoActivity.class);
            mintent.putExtra("Summer", url_Summer);
            mintent.putExtra("Autumn", url_Autumn);
            mintent.putExtra("Spring", url_Spring);
            mintent.putExtra("Winter", url_Winter);
            MainActivity.mainActivity.startActivity(mintent);
        }
    }
}
