package com.helper.west2ol.fzuhelper.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.helper.west2ol.fzuhelper.R;
import com.helper.west2ol.fzuhelper.adapter.ReutersShopAdapter;
import com.helper.west2ol.fzuhelper.bean.ShopInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deng on 2016/11/3.
 */

public class ReutersBusinessFragment extends Fragment {

    private Context context;
    private ReutersShopAdapter reutersShopAdapter;
    private ListView listView;
    private List<ShopInfoBean> shopInfoBeanList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_reuters_street_business , container , false);
        context = getContext();
        shopInfoBeanList = new ArrayList<>();
        setData();
        listView = (ListView) view.findViewById(R.id.lv_street_business);
        reutersShopAdapter = new ReutersShopAdapter(context , shopInfoBeanList);
        listView.setAdapter(reutersShopAdapter);
        return view;
    }

    void setData(){
        for(int i=0;i<20;i++){
            ShopInfoBean shopInfoBean = new ShopInfoBean();
            shopInfoBean.setSdShopName("问鱼");
            shopInfoBean.setSdShopCategory("餐厅");
            shopInfoBean.setSdShopScore(4.2);
            shopInfoBean.setSdSommentCount(666);
            shopInfoBeanList.add(shopInfoBean);
        }
    }
}
