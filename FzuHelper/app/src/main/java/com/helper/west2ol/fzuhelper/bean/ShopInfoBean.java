package com.helper.west2ol.fzuhelper.bean;

/**
 * Created by deng on 2016/11/13.
 */

/*
            1.店铺名称（shopName）
            2.店铺种类（shopCategory）
            3.店铺评分（shopScore）
            4.评论条数（commentCount）
*/

public class ShopInfoBean {
    private String sdShopName;
    private String sdShopCategory;
    private double sdShopScore;
    private int sdSommentCount;

    public String getSdShopCategory() {
        return sdShopCategory;
    }

    public void setSdShopCategory(String sdShopCategory) {
        this.sdShopCategory = sdShopCategory;
    }

    public String getSdShopName() {
        return sdShopName;
    }

    public void setSdShopName(String sdShopName) {
        this.sdShopName = sdShopName;
    }

    public double getSdShopScore() {
        return sdShopScore;
    }

    public void setSdShopScore(double sdShopScore) {
        this.sdShopScore = sdShopScore;
    }

    public int getSdSommentCount() {
        return sdSommentCount;
    }

    public void setSdSommentCount(int sdSommentCount) {
        this.sdSommentCount = sdSommentCount;
    }

}
