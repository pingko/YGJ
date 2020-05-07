package com.yzg.home.gdds.gddslist;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentRanklistBinding;

import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述: 跟单大师列表
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
@Route(path = RouterFragmentPath.Home.PAGER_GDDSRANKLIST)
public class GddsRankListFragment
        extends MvvmLazyFragment<HomeFragmentRanklistBinding, GddsRankListViewModel>
        implements IGddsRankListView {

    private GddsRankListAdapter adapter;

    public static GddsRankListFragment newInstance() {
        return new GddsRankListFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_ranklist;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        binding.rvTopicView.setHasFixedSize(true);
        binding.rvTopicView
                .setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.refreshLayout
//                .setRefreshHeader(new ClassicsHeader(getContext()));
//        binding.refreshLayout
//                .setRefreshFooter(new ClassicsFooter(getContext()));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.tryRefresh();
        });
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            viewModel.loadMore();
        });
        adapter = new GddsRankListAdapter(R.layout.home_item_gdds_rank_view);
        binding.rvTopicView.setAdapter(adapter);
        setLoadSir(binding.refreshLayout);
        showLoading();
        viewModel.initModel();
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected GddsRankListViewModel getViewModel() {
        return ViewModelProviders.of(this).get(GddsRankListViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

//    private View getFooterView() {
//        return LayoutInflater.from(getContext())
//                .inflate(R.layout.more_item_foote_view,
//                        binding.rvTopicView,
//                        false);
//    }

    @Override
    public void onDataLoaded(List<BaseCustomViewModel> data,
                             boolean isFirstPage) {
        if (data == null) {
            return;
        }
        if (isFirstPage) {
            adapter.setNewData(data);
            showContent();
            binding.refreshLayout.finishRefresh(true);

        } else {
            adapter.addData(data);
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
//        adapter.addFooterView(getFooterView());
        binding.refreshLayout.finishLoadMoreWithNoMoreData();
    }
}
