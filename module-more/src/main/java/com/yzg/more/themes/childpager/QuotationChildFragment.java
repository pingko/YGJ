package com.yzg.more.themes.childpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.more.R;
import com.yzg.more.databinding.MoreFragmentThemesContentBinding;
import com.yzg.more.themes.childpager.adapter.QuoContentAdapter;
import com.yzg.more.themes.childpager.adapter.ThemesContentAdapter;

import java.util.ArrayList;

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
        MvvmLazyFragment<MoreFragmentThemesContentBinding, ThemesContentViewModel>
        implements IThemeContentView {

    private QuoContentAdapter adapter;

    private String typeName = "";

//    private String apiUrl = "";

    @Override
    public int getLayoutId() {
        return R.layout.more_fragment_themes_content;
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
        adapter = new QuoContentAdapter(R.layout.quo_item_view);
        binding.rvThemeView.setAdapter(adapter);
        setLoadSir(binding.refreshLayout);
        showLoading();
        viewModel.initModel(typeName);

    }

    private View getFooterView() {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.more_item_foote_view,
                        binding.rvThemeView,
                        false);
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
    protected ThemesContentViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ThemesContentViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void onDataLoaded(ArrayList<BaseCustomViewModel> viewModels) {
        if (viewModels == null) {
            return;
        }
//        if (isFirstPage) {
//            adapter.setNewData(viewModels);
//            showContent();
//            binding.refreshLayout.finishRefresh(true);
//        } else {
//
//        }
        adapter.addData(viewModels);
        showContent();
        binding.refreshLayout.finishLoadMore(true);

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
