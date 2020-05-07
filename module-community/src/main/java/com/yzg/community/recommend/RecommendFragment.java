package com.yzg.community.recommend;

import java.util.ArrayList;

import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.recyclerview.RecyclerItemDecoration;
import com.yzg.common.utils.DensityUtils;
import com.yzg.community.R;
import com.yzg.community.databinding.CommunityFragmentRecommendBinding;
import com.yzg.community.recommend.adapter.ProviderRecommendAdapter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 应用模块:
 * <p>
 * 类描述: 社区-推荐
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-16
 */
public class RecommendFragment
    extends MvvmLazyFragment<CommunityFragmentRecommendBinding, RecommendViewModel>
    implements IRecommendView
{

    private ProviderRecommendAdapter adapter;

    public static RecommendFragment newInstance()
    {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }
    
    @Override
    public int getLayoutId()
    {
        return R.layout.community_fragment_recommend;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        adapter = new ProviderRecommendAdapter();
        binding.rvDailyView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        binding.rvDailyView.setLayoutManager(layoutManager);
        binding.rvDailyView.addItemDecoration(new RecyclerItemDecoration(0,0, DensityUtils.dp2px(getContext(),5),DensityUtils.dp2px(getContext(),15)));
        binding.rvDailyView.setAdapter(adapter);
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));
        binding.refreshLayout
                .setRefreshFooter(new ClassicsFooter(getContext()));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.tryRefresh();
        });
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            viewModel.loadMore();
        });
        setLoadSir(binding.refreshLayout);
        showLoading();
        viewModel.initModel();
    }

    @Override
    public int getBindingVariable()
    {
        return 0;
    }
    
    @Override
    protected RecommendViewModel getViewModel()
    {
        return ViewModelProviders.of(this).get(RecommendViewModel.class);
    }
    
    @Override
    protected void onRetryBtnClick()
    {
        
    }
    
    @Override
    public void onLoadMoreFailure(String message)
    {
        binding.refreshLayout.finishLoadMore(false);
    }
    
    @Override
    public void onLoadMoreEmpty()
    {
        binding.refreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void onDataLoadFinish(ArrayList<BaseCustomViewModel> viewModels, boolean isFirstPage) {
        if (isFirstPage){
            adapter.setNewData(viewModels);
            showContent();
            binding.refreshLayout.finishRefresh(true);

        }else {
            adapter.addData(viewModels);
            showContent();
            binding.refreshLayout.finishLoadMore(true);

        }

    }
}
