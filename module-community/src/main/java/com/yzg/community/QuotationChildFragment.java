package com.yzg.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.community.adapter.IQuotationContentView;
import com.yzg.community.adapter.QuoContentAdapter;
import com.yzg.community.adapter.QuotationContentViewModel;
import com.yzg.community.databinding.CommunityFragmentThemesContentBinding;
import com.yzg.community.recommend.QuotationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述: 主题内容 二级fragment
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class QuotationChildFragment extends
        MvvmLazyFragment<CommunityFragmentThemesContentBinding, QuotationContentViewModel>
        implements IQuotationContentView {

    private QuoContentAdapter adapter;

    private String typeName = "";

//    private String apiUrl = "";

    @Override
    public int getLayoutId() {
        return R.layout.community_fragment_themes_content;
    }

    public static QuotationChildFragment newInstance(String name) {
        QuotationChildFragment fragment = new QuotationChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        binding.rvThemeView.setHasFixedSize(true);
        binding.rvThemeView
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
        adapter = new QuoContentAdapter(R.layout.community_quo_item_view);
        adapter.addHeaderView(getHeadView());
        binding.rvThemeView.setAdapter(adapter);
        setLoadSir(binding.refreshLayout);
        showLoading();
        viewModel.initModel(typeName);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(getActivity(), QuotationActivity.class));
        });

    }


    @Override
    protected void initParameters() {
        if (getArguments() != null) {
            typeName = getArguments().getString("name");
//            apiUrl = getArguments().getString("url");
        }
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected QuotationContentViewModel getViewModel() {
        return ViewModelProviders.of(this).get(QuotationContentViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    private View getHeadView() {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.quotation_item_footer_view,
                        binding.rvThemeView,
                        false);
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

    @Override
    public void onDataLoaded(List<BaseCustomViewModel> data, boolean isFirstPage) {
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
