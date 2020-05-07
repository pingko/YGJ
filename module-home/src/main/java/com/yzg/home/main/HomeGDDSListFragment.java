package com.yzg.home.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentGddslistBinding;
import com.yzg.home.discover.adapter.ProviderGDDSSYAdapter;
import com.yzg.home.discover.adapter.ProviderGDDSSubsAdapter;
import com.yzg.home.gdds.HomeGDDSListViewModel;
import com.yzg.home.gdds.IHomeGDDSListView;
import com.yzg.home.gdds.gddslist.HomeGddsRankListctivity;

import java.util.ArrayList;

@Route(path = RouterFragmentPath.Home.PAGER_GDDS)
public class HomeGDDSListFragment
        extends MvvmLazyFragment<HomeFragmentGddslistBinding, HomeGDDSListViewModel>
        implements IHomeGDDSListView {

    private ProviderGDDSSYAdapter syAdapter;
    private ProviderGDDSSubsAdapter subAdapter;


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        binding.ivBack.setOnClickListener(view -> {
            getActivity().finish();
        });
        // 确定Item的改变不会影响RecyclerView的宽高
        binding.rvSyView.setHasFixedSize(true);
        binding.rvSyView
                .setLayoutManager(new LinearLayoutManager(getContext()));
        syAdapter = new ProviderGDDSSYAdapter();
//        adapter.addFooterView(getFooterView());
        binding.rvSyView.setAdapter(syAdapter);
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));

        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.tryToRefresh();
        });

        binding.rvGdds.setHasFixedSize(true);
        binding.rvGdds
                .setLayoutManager(new LinearLayoutManager(getContext()));
        subAdapter = new ProviderGDDSSubsAdapter();
//        adapter.addFooterView(getFooterView());
        binding.rvGdds.setAdapter(subAdapter);

        setLoadSir(binding.refreshLayout);
        showLoading();
        viewModel.initModel();

//        subAdapter.setOnItemClickListener((adapter, view, position) -> {
//            Intent intent = new Intent();
//            intent.setClass(getContext(), HomeGddsDetailActivity.class);
//            getActivity().startActivity(intent);
//        });
//
//        syAdapter.setOnItemClickListener((adapter, view, position) -> {
//            Intent intent = new Intent();
//            intent.setClass(getContext(), HomeGddsDetailActivity.class);
//            getActivity().startActivity(intent);
//        });
        binding.tvSyMore.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), HomeGddsRankListctivity.class);
            getActivity().startActivity(intent);
        });
        binding.tvSubMore.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), HomeGddsRankListctivity.class);
            getActivity().startActivity(intent);
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private View getFooterView() {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.home_item_foote_view,
                        binding.rvGdds,
                        false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_gddslist;
    }

    @Override
    public int getBindingVariable() {
        return com.yzg.home.BR.HomeVM;
    }

    @Override
    protected HomeGDDSListViewModel getViewModel() {
        return ViewModelProviders.of(this).get(HomeGDDSListViewModel.class);
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
        syAdapter.setNewData(viewModels);
        subAdapter.setNewData(viewModels);
        showContent();
    }
}
