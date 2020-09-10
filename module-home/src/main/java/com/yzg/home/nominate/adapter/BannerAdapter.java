package com.yzg.home.nominate.adapter;

import java.util.ArrayList;

import androidx.databinding.BindingAdapter;

import com.yzg.base.utils.GsonUtils;
import com.yzg.home.R;
import com.yzg.home.nominate.adapter.provider.LocalBannerProvider;
import com.yzg.home.nominate.adapter.provider.NetBannerProvider;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.PageStyle;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-13
 */
public class BannerAdapter {
    @BindingAdapter("initBannerView")
    public static void initBannerView(BannerViewPager bannerViewPager,
                                      ArrayList<String> list) {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add(
                "http://img.kaiyanapp.com/b5b00c67dfc759a02c8b457e104b3ec6.png?imageMogr2/quality/60/format/jpg");
        list1.add(
                "http://img.kaiyanapp.com/b5b00c67dfc759a02c8b457e104b3ec6.png?imageMogr2/quality/60/format/jpg");
        list1.add(
                "http://img.kaiyanapp.com/1eaf8827688ea3b910b7b6b6cb192a5f.png?imageMogr2/quality/60/format/jpg");
        list1.add(
                "http://img.kaiyanapp.com/1eaf8827688ea3b910b7b6b6cb192a5f.png?imageMogr2/quality/60/format/jpg");

        bannerViewPager.setHolderCreator(NetBannerProvider::new)
                .setPageStyle(PageStyle.MULTI_PAGE)
                .create(list1);
    }
    @BindingAdapter("initBannerView1")
    public static void initBannerView1(BannerViewPager bannerViewPager,
                                      ArrayList<Integer> list) {
        ArrayList<Integer> list1 = new ArrayList<>();
       if (GsonUtils.isShowTrue()) {
           list1.add(R.drawable.banner_01);
           list1.add(R.drawable.banner_02);
           list1.add(R.drawable.banner_03);
           list1.add(R.drawable.banner_04);
       }else {
           list1.add(R.drawable.banner00);
           list1.add(R.drawable.banner00);
       }
        bannerViewPager.setHolderCreator(LocalBannerProvider::new)
                .setPageStyle(PageStyle.MULTI_PAGE)
                .create(list1);
    }
}
