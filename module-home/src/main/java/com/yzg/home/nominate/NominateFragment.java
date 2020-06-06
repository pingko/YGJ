package com.yzg.home.nominate;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentNominateBinding;
import com.yzg.home.nominate.adapter.ProviderNominateAdapter;

import java.util.ArrayList;

/**
 * 应用模块: 首页
 * <p>
 * 类描述: 首页-推荐
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-09
 */
public class NominateFragment
        extends MvvmLazyFragment<HomeFragmentNominateBinding, NominateViewModel>
        implements INominateView {
    private ProviderNominateAdapter adapter;

    public static NominateFragment newInstance() {
        NominateFragment fragment = new NominateFragment();
        return fragment;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();

    }

    private void initView() {

        // 确定Item的改变不会影响RecyclerView的宽高
        binding.rvNominateView.setHasFixedSize(true);
        binding.rvNominateView
                .setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProviderNominateAdapter();
//        adapter.addHeaderView(getHeaderView());
        binding.rvNominateView.setAdapter(adapter);
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));
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

    /**
     * 配置头部banner
     */
//    private View getHeaderView()
//    {
//        ViewDataBinding binding = DataBindingUtil.inflate(getLayoutInflater(),
//            R.layout.home_item_banner_view,
//            binding.rvNominateView,
//            false);
//        View view = binding.getRoot();
//        return view;
//    }
    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_nominate;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected NominateViewModel getViewModel() {
        return ViewModelProviders.of(this).get(NominateViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onLoadMoreFailure(String message) {
        binding.refreshLayout.finishLoadMore(false);
    }

    @Override
    public void onLoadMoreEmpty() {
        binding.refreshLayout.finishLoadMoreWithNoMoreData();
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
}
