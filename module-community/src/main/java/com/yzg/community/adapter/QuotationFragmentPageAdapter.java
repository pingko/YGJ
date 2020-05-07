package com.yzg.community.adapter;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.yzg.community.QuotationChildFragment;
import com.yzg.community.bean.Tabs;

import java.util.ArrayList;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-16
 */
public class QuotationFragmentPageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Tabs> tabs;

    public QuotationFragmentPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void setData(ArrayList<Tabs> data){
        if (data == null){
            return;
        }
        this.tabs = data;
       notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return QuotationChildFragment.newInstance(tabs.get(position).getName());
    }

    @Override
    public int getCount() {
       if (tabs != null && tabs.size() >0){
           return tabs.size();
       }
        return 0;
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        super.restoreState(state, loader);
    }
}
