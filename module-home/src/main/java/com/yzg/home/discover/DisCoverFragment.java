package com.yzg.home.discover;

import java.util.ArrayList;

import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentFindMoreBinding;
import com.yzg.home.discover.adapter.ProviderDisCoverAdapter;
import com.scwang.smart.refresh.header.ClassicsHeader;

import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 应用模块:
 * <p>
 * 类描述: 发现
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-09
 */
public class DisCoverFragment
        extends MvvmLazyFragment<HomeFragmentFindMoreBinding, DisCoverViewModel>
        implements IDisCoverView {

    private ProviderDisCoverAdapter adapter;

    public static DisCoverFragment newInstance() {
        DisCoverFragment fragment = new DisCoverFragment();
        return fragment;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        // 确定Item的改变不会影响RecyclerView的宽高
        binding.rvDiscoverView.setHasFixedSize(true);
        binding.rvDiscoverView
                .setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProviderDisCoverAdapter();
        adapter.addFooterView(getFooterView());
        binding.rvDiscoverView.setAdapter(adapter);
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.tryToRefresh();
        });
        setLoadSir(binding.refreshLayout);
        showLoading();
        viewModel.initModel();
    }

    private View getFooterView() {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.home_item_foote_view,
                        binding.rvDiscoverView,
                        false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_find_more;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected DisCoverViewModel getViewModel() {
        return ViewModelProviders.of(this).get(DisCoverViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onDataLoadFinish(ArrayList<BaseCustomViewModel> viewModels,
                                 boolean isEmpty) {
        if (isEmpty) {
            binding.refreshLayout.finishRefresh(false);
        } else {
            binding.refreshLayout.finishRefresh(true);
        }
        adapter.setNewData(viewModels);
        showContent();
    }
}
