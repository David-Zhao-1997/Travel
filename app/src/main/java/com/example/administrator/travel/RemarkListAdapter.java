package com.example.administrator.travel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/19.
 */

public class RemarkListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<RemarkItem> remarkList;
    private RemarkItem remarkItem;
    private int pos = -1;

    public RemarkListAdapter(Context context, ArrayList<RemarkItem> remarkList)
    {
        this.context = context;
        this.remarkList = remarkList;
    }

    @Override
    public int getCount()
    {
        return remarkList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return i;
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        ViewHolder viewHolder = null;
        if (view == null)
        {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.remark_list, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image);
            viewHolder.remark = (TextView) view.findViewById(R.id.remarkText);
            viewHolder.userName = (TextView) view.findViewById(R.id.userName);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        remarkItem = remarkList.get(position);
        viewHolder.remark.setText(remarkItem.text);
        viewHolder.userName.setText(remarkItem.userName);//得到Resources对象
        Resources r = MainActivity.mainActivity.getResources();
//以数据流的方式读取资源
        Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.mainActivity.getResources(), R.mipmap.ic_launcher);
        if (bitmap != null)
        {
            viewHolder.imageView.setImageBitmap(bitmap);
        }
        viewHolder.imageView.setTag(null);
        return view;
    }

    public class ViewHolder
    {
        public ImageView imageView;
        public TextView userName;
        public TextView remark;
    }

}
