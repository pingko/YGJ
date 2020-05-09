package com.yzg.home.jlyt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.header.ClassicsHeader;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeActivityJlytBinding;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HomeJlytActivity extends MvvmBaseActivity<HomeActivityJlytBinding,HomeJlytViewModel> implements IJLYTView {

    private CommonAdapter jlytAdapter;
    private List<JlytBean> jlytBeanList;

    @Override
    protected HomeJlytViewModel getViewModel() {
        return ViewModelProviders.of(this).get(HomeJlytViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        jlytBeanList = new ArrayList<>();
        for (int i=0;i<4;i++){
            JlytBean bean = new JlytBean();
            bean.setName("白银"+(i+1)+"号(12个月)");
            bean.setRate("4.35%");
            jlytBeanList.add(bean);
        }

        jlytAdapter = new CommonAdapter<JlytBean>(this, R.layout.home_jlyt_item, jlytBeanList) {
            @Override
            protected void convert(ViewHolder holder, JlytBean bean, int position) {
                holder.setText(R.id.tv_name,bean.getName());
                holder.setText(R.id.tv_rate,bean.getRate());
            }
        };
        binding.rvJlyt.setLayoutManager(new LinearLayoutManager(this));
//        binding.rvJlyt.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.common_color_text_gray), ConvertUtils.dp2px(15),ConvertUtils.dp2px(15)));
        binding.rvJlyt.setAdapter(jlytAdapter);

//        binding.refreshLayout.setOnRefreshListener(refreshLayout -> binding.refreshLayout.finishRefresh(500));
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(this));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
//            viewModel.tryToRefresh();
            binding.refreshLayout.finishRefresh(500);
        });
        binding.ivBack.setOnClickListener(view -> finish());
        jlytAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                Intent intent =new Intent(HomeJlytActivity.this,HomeJlytDetailActivity.class);
                intent.putExtra("JlytBean",jlytBeanList.get(i));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });

    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity_jlyt;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onDataLoadFinish(BaseCustomViewModel viewModel) {

    }
}
