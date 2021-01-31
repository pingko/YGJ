package com.yzg.home.gdds.gddslist;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentRanklistBinding;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
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
public class GddsRankListFragment extends MvvmLazyFragment<HomeFragmentRanklistBinding, GddsRankListViewModel> {

    private CommonAdapter adapter;

    private List<GddsBean> gddsBeans = new ArrayList<>();

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
        binding.rvTopicView
                .setLayoutManager(new LinearLayoutManager(getContext()));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            binding.refreshLayout.finishRefresh(500);
        });
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            binding.refreshLayout.finishLoadMore(500);
        });

        adapter = new CommonAdapter<GddsBean>(getContext(), R.layout.home_item_gdds_rank_view, gddsBeans) {
            @Override
            protected void convert(ViewHolder holder, GddsBean bean, int position) {
            }
        };

        binding.rvTopicView.setAdapter(adapter);
        viewModel.getList("", "", "");
        viewModel.successData.observe(this, aBoolean -> {
            for (int i = 0; i < 9; i++) {
                gddsBeans.add(new GddsBean());
            }
            adapter.notifyDataSetChanged();
        });

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

}
