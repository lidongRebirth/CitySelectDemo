package com.myfittinglife.app.mycityselectdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.myfittinglife.app.mycityselectdemo.adapter.MyAdapter;
import com.myfittinglife.app.mycityselectdemo.bean.ItemBean;
import com.myfittinglife.app.mycityselectdemo.util.CityComparator;
import com.myfittinglife.app.mycityselectdemo.util.HanziToPinYin;
import com.myfittinglife.app.mycityselectdemo.view.SideBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
*  @Author      LD
*  @Time        2018/12/18
*  @Describe    自己的城市选择控件
*  @Modify
*/
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<ItemBean> itemList;    //所有的item子项，可能是城市、可能是字母
    private List<String> cityList;      //所有的城市名

    private static final String TAG = "ceshi";
    private MyAdapter myAdapter;
    private ListView listView;
    private SideBar sideBar;
    private TextView dialog_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view_cities);
        dialog_text = (TextView) findViewById(R.id.tv_text_dialog);

        sideBar = findViewById(R.id.side_bar);
        sideBar.setTextDialog(dialog_text);

        // 设置拼音列表的滑动事件，
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(int position) {
                String city_label = Constants.CITY_TYPE[position];
                for (int i = 0; i < cityList.size(); i++) {
                    if (itemList.get(i).getItemName().equals(city_label)) {
                        listView.setSelection(i);
                        dialog_text.setVisibility(View.VISIBLE);
                        break;
                    }
                    if(i == cityList.size() -1){
                        dialog_text.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        initData();


    }

    /**
     * 初始化数据，将所有城市进行排序，且加上字母和他们一起  最终要的在这里
     */
    private void initData(){

        //获取所有的城市
        String[] cityArray = getResources().getStringArray(R.array.mycityarray);
        cityList = Arrays.asList(cityArray);

        itemList = new ArrayList<>();
        //*----------------------------------------------------------------------------------------
        Collections.sort(cityList,new CityComparator());                           //将所有城市进行排序
        
        for(int i = 0;i<cityList.size();i++){
            Log.i(TAG, cityList.get(i)+"\n");
        }

        String currentLetter = HanziToPinYin.toPinYin(cityList.get(0)) + "";    //获取到的是首字母
        Log.i(TAG, "initData: 字母为："+currentLetter);
        ItemBean itemBean = new ItemBean();
        itemBean.setItemName(currentLetter);
        itemBean.setItemType("label");
        itemList.add(itemBean);

        for(int i =0;i<cityList.size();i++){

            String city = cityList.get(i);
            String letter=null;                          //字母
            if(city.contains("重庆")){
                letter = HanziToPinYin.toPinYin("崇庆") + "";

            }else {
                letter = HanziToPinYin.toPinYin(cityList.get(i)) + "";
            }

            if(letter.equals(currentLetter)){           //在A字母下，属于当前字母
                itemBean = new ItemBean();
                itemBean.setItemName(city);             //把汉字放进去
                itemBean.setItemType(letter);           //把拼音放进去
                itemList.add(itemBean);
            }else {                                     //不属于当前字母
                //添加标签
                itemBean = new ItemBean();
                itemBean.setItemName(letter);           //把首字母进去
                itemBean.setItemType("label");          //把label标签放进去
                currentLetter = letter;
                itemList.add(itemBean);

                //添加城市
                itemBean = new ItemBean();
                itemBean.setItemName(city);             //把汉字放进去
                itemBean.setItemType(letter);           //把拼音放进去
                itemList.add(itemBean);
            }
        }
        myAdapter = new MyAdapter(itemList,this);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(view.getContext(),itemList.get(position).getItemName(),Toast.LENGTH_SHORT).show();
    }
}
