package com.yzg.more.topic;

import java.util.List;

import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.more.R;
import com.yzg.more.databinding.MoreFragmentTopicBinding;
import com.yzg.more.topic.adapter.TopicAdapter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;

import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 应用模块:
 * <p>
 * 类描述: 话题广场
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class TopicFragment
        extends MvvmLazyFragment<MoreFragmentTopicBinding, TopicFragmentViewModel>
        implements ITopicView {

    private TopicAdapter adapter;

    public static TopicFragment newInstance() {
        return new TopicFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.more_fragment_topic;
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
        adapter = new TopicAdapter(R.layout.more_item_themes_view);
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
    protected TopicFragmentViewModel getViewModel() {
        return ViewModelProviders.of(this).get(TopicFragmentViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    private View getFooterView() {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.more_item_foote_view,
                        binding.rvTopicView,
                        false);
    }

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
        adapter.addFooterView(getFooterView());
        binding.refreshLayout.finishLoadMoreWithNoMoreData();
    }
}
