package com.yzg.home.daily;

import java.util.ArrayList;

import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.home.R;
import com.yzg.home.daily.adapter.ProviderDailyAdapter;
import com.yzg.home.databinding.HomeFragmentDailyBinding;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 应用模块:
 * <p>
 * 类描述: 日报
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-09
 */
public class DailyFragment extends
        MvvmLazyFragment<HomeFragmentDailyBinding, DailyViewModel> implements IDailyView {

    private ProviderDailyAdapter adapter;

    public static DailyFragment newInstance() {
        DailyFragment fragment = new DailyFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_daily;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        // 确定Item的改变不会影响RecyclerView的宽高
        binding.rvDailyView.setHasFixedSize(true);
        binding.rvDailyView
                .setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProviderDailyAdapter();
        binding.rvDailyView.setAdapter(adapter);
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));
        binding.refreshLayout.setEnableLoadMore(true);
        binding.refreshLayout
                .setRefreshFooter(new ClassicsFooter(getContext()));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.tryToRefresh();
        });
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            viewModel.loadMore();
        });
        setLoadSir(binding.refreshLayout);
        showLoading();
        viewModel.initModel();

    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected DailyViewModel getViewModel() {
        return ViewModelProviders.of(this).get(DailyViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onDataLoadFinish(ArrayList<BaseCustomViewModel> viewModels,
                                 boolean isFirstPage) {
        if (isFirstPage) {

            adapter.setNewData(viewModels);
            showContent();
            binding.refreshLayout.finishRefresh(true);
        } else {

            adapter.addData(viewModels);
            showContent();
            binding.refreshLayout.finishLoadMore(true);
        }
    }

    @Override
    public void onLoadMoreFailure(String message) {
        binding.refreshLayout.finishLoadMore(false);
    }

    @Override
    public void onLoadMoreEmpty() {
        binding.refreshLayout.finishLoadMoreWithNoMoreData();
    }
}
