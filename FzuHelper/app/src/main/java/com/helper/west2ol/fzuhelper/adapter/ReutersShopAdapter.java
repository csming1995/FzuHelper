package com.helper.west2ol.fzuhelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.helper.west2ol.fzuhelper.R;
import com.helper.west2ol.fzuhelper.bean.ShopInfoBean;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Vector;

/**
 * Created by deng on 2016/11/5.
 */

public class ReutersShopAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;
    private List<ShopInfoBean> list;

    public ReutersShopAdapter(Context context , List<ShopInfoBean>list){
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.reuters_shopitem , null);
            viewHolder.shopName = (TextView) convertView.findViewById(R.id.shopName);
            viewHolder.shopCategory = (TextView) convertView.findViewById(R.id.shopCategory);
            viewHolder.shopScore = (TextView) convertView.findViewById(R.id.shopScore);
            viewHolder.commentCount = (TextView) convertView.findViewById(R.id.commentCount);
            setStar(position , convertView , viewHolder);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.shopName.setText((String)list.get(position).getSdShopName());
        viewHolder.shopCategory.setText((String)list.get(position).getSdShopCategory());
        viewHolder.shopScore.setText(String.valueOf((double)list.get(position).getSdShopScore()));
        viewHolder.commentCount.setText(String.valueOf((int)list.get(position).getSdSommentCount()));
        return convertView;
    }

    public void setStar(int position , View convertView , ViewHolder viewHolder){
        int starFullCount = (int)list.get(position).getSdShopScore();
        boolean isStarHalfExist;
        if(list.get(position).getSdShopScore() - (int) list.get(position).getSdShopScore() == 0){
            isStarHalfExist = false;
        }else{
            isStarHalfExist = true;
        }
        viewHolder.star = new Vector<ImageView>();
        ImageView star1 = (ImageView) convertView.findViewById(R.id.star1);
        ImageView star2 = (ImageView) convertView.findViewById(R.id.star2);
        ImageView star3 = (ImageView) convertView.findViewById(R.id.star3);
        ImageView star4 = (ImageView) convertView.findViewById(R.id.star4);
        ImageView star5 = (ImageView) convertView.findViewById(R.id.star5);
        viewHolder.star.add(star1);
        viewHolder.star.add(star2);
        viewHolder.star.add(star3);
        viewHolder.star.add(star4);
        viewHolder.star.add(star5);
        for(int i=0;i<starFullCount;i++){
            viewHolder.star.get(i).setImageResource(R.drawable.star_full);
        }
        if(isStarHalfExist){
            viewHolder.star.get(starFullCount).setImageResource(R.drawable.star_half);
        }else{
            viewHolder.star.get(starFullCount).setImageResource(R.drawable.star_empty);
        }
        for(int i=starFullCount+1;i<5;i++){
            viewHolder.star.get(i).setImageResource(R.drawable.star_empty);
        }
    }

    static class ViewHolder{
        public TextView shopName;
        public TextView shopCategory;
        public TextView shopScore;
        public TextView commentCount;
        public Vector<ImageView> star;
    }
}
