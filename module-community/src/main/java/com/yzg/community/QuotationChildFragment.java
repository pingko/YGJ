package com.yzg.community;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.base.model.MarkettBean;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.community.adapter.QuotationContentViewModel;
import com.yzg.community.databinding.CommunityFragmentThemesContentBinding;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述: 主题内容 二级fragment
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23MvvmBaseActivity<HomeActivityJlytDetailBinding, HomeJlytDetailViewModel> {
 */
public class QuotationChildFragment extends
        MvvmLazyFragment<CommunityFragmentThemesContentBinding, QuotationContentViewModel> {

    private String typeName = "";

    private int num = 1;
    private int size = 10;
    private List<MarkettBean> quotationBeans = new ArrayList<>();

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    //    @Override
//    protected void onFragmentFirstVisible() {
//        super.onFragmentFirstVisible();
//        initView();
//    }

    private CommonAdapter marketAdapter;

    private void initView() {
        if (!isAdded())
            return;
        if (binding.rvThemeView==null)
            return;
        binding.rvThemeView.setHasFixedSize(true);
        binding.rvThemeView
                .setLayoutManager(new LinearLayoutManager(getContext()));
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));
        binding.refreshLayout
                .setRefreshFooter(new ClassicsFooter(getContext()));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            num=1;
            viewModel.loadData(num, size, "");
            binding.refreshLayout.finishRefresh();
        });
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            viewModel.loadData(num++, size, "");
            binding.refreshLayout.finishLoadMore(true);
        });

        marketAdapter = new CommonAdapter<MarkettBean>(getActivity(), R.layout.community_quo_item_view, quotationBeans) {
            @Override
            protected void convert(ViewHolder holder, MarkettBean bean, int position) {
                holder.setText(R.id.tv_title, bean.getVarietynm() + "");
                holder.setText(R.id.tv_subtitle, bean.getVariety());
                holder.setText(R.id.tv_range, bean.getChangeMargin() + "");
                holder.setText(R.id.tv_price, bean.getChangePrice() + "");

            }
        };
        binding.rvThemeView.setAdapter(marketAdapter);
        viewModel.loadData(num, size, "");
//        viewModel.loadData(typeName);

        marketAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                ARouter.getInstance()
                        .build(RouterActivityPath.Quotation.Quotation_main)
                        .withSerializable("MarkettBean", quotationBeans.get(i))
                        .navigation();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });

        viewModel.successData.observe(this, markettBeans -> {
            quotationBeans.clear();
            quotationBeans.addAll(markettBeans);
            marketAdapter.notifyDataSetChanged();
        });

    }


    @Override
    protected void initParameters() {
        if (getArguments() != null) {
            typeName = getArguments().getString("name");
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


}
