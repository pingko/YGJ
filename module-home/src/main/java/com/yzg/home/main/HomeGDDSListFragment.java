package com.yzg.home.main;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.recyclerview.RecyclerItemDecoration;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.common.utils.DensityUtils;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentGddslistBinding;
import com.yzg.home.gdds.HomeGDDSListViewModel;
import com.yzg.home.gdds.gddslist.GddsBean;
import com.yzg.home.gdds.gddslist.HomeGddsRankListctivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterFragmentPath.Home.PAGER_GDDS)
public class HomeGDDSListFragment
        extends MvvmLazyFragment<HomeFragmentGddslistBinding, HomeGDDSListViewModel> {

    private CommonAdapter syAdapter;
    private CommonAdapter subAdapter;
    private List<GddsBean> gddsSYBeans = new ArrayList<>();
    private List<GddsBean> gddsSubBeans = new ArrayList<>();


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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvSyView.setLayoutManager(layoutManager);
        binding.rvSyView.addItemDecoration(new RecyclerItemDecoration(0,
                0, DensityUtils.dip2px(getContext(), 16), 0));
        syAdapter = new CommonAdapter<GddsBean>(getContext(), R.layout.home_item_gdds_sy_item, gddsSYBeans) {
            @Override
            protected void convert(ViewHolder holder, GddsBean bean, int position) {
            }
        };
        binding.rvSyView.setAdapter(syAdapter);
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));

        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
        });

        binding.rvGdds.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvGdds.setLayoutManager(layoutManager1);
        binding.rvGdds.addItemDecoration(new RecyclerItemDecoration(0,
                0, DensityUtils.dip2px(getContext(), 16), 0));


        subAdapter = new CommonAdapter<GddsBean>(getContext(), R.layout.home_item_gdds_subs_item, gddsSubBeans) {
            @Override
            protected void convert(ViewHolder holder, GddsBean bean, int position) {
            }
        };
//        adapter.addFooterView(getFooterView());
        binding.rvGdds.setAdapter(subAdapter);


        subAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                Intent intent = new Intent();
                intent.setClass(getContext(), HomeGddsDetailActivity.class);
                getActivity().startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        syAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                Intent intent = new Intent();
                intent.setClass(getContext(), HomeGddsDetailActivity.class);
                getActivity().startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
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
        viewModel.getList("", "", "");
        viewModel.successData.observe(this, aBoolean -> {
            for (int i = 0; i < 9; i++) {
                gddsSubBeans.add(new GddsBean());
                gddsSYBeans.add(new GddsBean());
            }
            syAdapter.notifyDataSetChanged();
            subAdapter.notifyDataSetChanged();
        });

        viewModel.gddsBeanMutableLiveData.observe(this, new Observer<List<GddsBean>>() {
            @Override
            public void onChanged(List<GddsBean> gddsBeans) {
                gddsSubBeans.addAll(gddsBeans);
                gddsSYBeans.addAll(gddsBeans);
            syAdapter.notifyDataSetChanged();
            subAdapter.notifyDataSetChanged();
            }
        });
    }
//    GDDSSubscribeItemAdapter adapter = new GDDSSubscribeItemAdapter(
//            R.layout.home_item_gdds_subs_item);
//            adapter.setOnItemClickListener((adapter1, view, position) -> {
//        Intent intent = new Intent();
//        intent.setClass(getContext(), HomeGddsDetailActivity.class);
//        context.startActivity(intent);
//    });

//    GDDSSYItemAdapter adapter = new GDDSSYItemAdapter(
//            R.layout.home_item_gdds_sy_item);
//            binding.rvCategoryView.setAdapter(adapter);
//            adapter.setOnItemClickListener((adapter1, view, position) -> {
//        Intent intent = new Intent();
//        intent.setClass(getContext(), HomeGddsDetailActivity.class);
//        context.startActivity(intent);
//    });

    @Override
    public void onDestroy() {
        super.onDestroy();
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
}
