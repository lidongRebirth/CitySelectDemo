package com.myfittinglife.app.mycityselectdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myfittinglife.app.mycityselectdemo.R;
import com.myfittinglife.app.mycityselectdemo.bean.ItemBean;

import java.util.List;

/**
 * @Author      LD
 * @Time        2018/12/19 15:15
 * @Describe    列表适配器
 * @Modify
 */
public class MyAdapter extends BaseAdapter {
    private List<ItemBean> itemBeanList;
    private Context context;

    public MyAdapter(List<ItemBean> itemBeanList, Context context) {
        this.itemBeanList = itemBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemBeanList.size();
    }

    @Override
    public int getViewTypeCount() {
        return ItemBean.getTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return itemBeanList.get(position).getType();    //0为显示大写字母，1为显示城市
    }

    @Override
    public Object getItem(int position) {
        return itemBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //判断是否可以点击，即判断是否是省市名拼音
    @Override
    public boolean isEnabled(int position) {

        if (getItemViewType(position) == ItemBean.TYPE_LABEL){
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 如果是label 即拼音 就为label布局
        if(getItemViewType(position) == ItemBean.TYPE_LABEL){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_list_label, parent, false);
        }else{
            //如果是城市，则为城市名称布局
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_list_city, parent, false);
        }
        //显示列表项文字
        TextView tv = (TextView) convertView.findViewById(R.id.tv_item_city);
        tv.setText(itemBeanList.get(position).getItemName());
        return convertView;
    }
}
