package com.yzg.more.message;

import java.util.List;

import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.recyclerview.RecyclerItemDecoration;
import com.yzg.common.utils.DensityUtils;
import com.yzg.more.R;
import com.yzg.more.databinding.MoreFragmentMessageBinding;
import com.yzg.more.message.adapter.MessageAdapter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;

import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class MessageFragment extends
        MvvmLazyFragment<MoreFragmentMessageBinding, MessageFragmentViewModel>
        implements IMessageView {

    private MessageAdapter adapter;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.more_fragment_message;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        int margin = DensityUtils.dp2px(getContext(), 10);
        binding.rvMessageView.setHasFixedSize(true);
        binding.rvMessageView.addItemDecoration(
                new RecyclerItemDecoration(margin, 0, margin, margin));
        binding.rvMessageView
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
        adapter = new MessageAdapter();
        binding.rvMessageView.setAdapter(adapter);
        setLoadSir(binding.refreshLayout);
        showLoading();
        viewModel.initModel();

    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected MessageFragmentViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MessageFragmentViewModel.class);
    }

    private View getFooterView() {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.more_item_foote_view,
                        binding.rvMessageView,
                        false);
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
        adapter.addFooterView(getFooterView());
        binding.refreshLayout.finishLoadMoreWithNoMoreData();
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
}
