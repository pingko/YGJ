package com.yzg.home.discover.adapter.provider;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.recyclerview.RecyclerItemDecoration;
import com.yzg.common.utils.DensityUtils;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeItemGddsSyBinding;
import com.yzg.home.discover.adapter.GDDSSubscribeItemAdapter;
import com.yzg.home.discover.bean.SquareCard;
import com.yzg.home.main.HomeGddsDetailActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class GDDSSubscribeProvider extends BaseItemProvider<BaseCustomViewModel> {
    @Override
    public int getItemViewType() {
        return IDisCoverItemType.THEME_GDDS_SUBS_VIEW;
    }

    @Override
    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder,
                                    int viewType) {
        HomeItemGddsSyBinding binding =
                DataBindingUtil.bind(viewHolder.itemView);
        binding.rvCategoryView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvCategoryView.setLayoutManager(layoutManager);
        binding.rvCategoryView.addItemDecoration(new RecyclerItemDecoration(0,
                0, DensityUtils.dip2px(getContext(), 16), 0));
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_item_gdds_sy;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder,
                        @Nullable BaseCustomViewModel baseCustomViewModel) {
        if (baseCustomViewModel == null) {
            return;
        }
        HomeItemGddsSyBinding binding = baseViewHolder.getBinding();
        if (binding != null) {
            ArrayList<SquareCard> dataList = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                dataList.add(new SquareCard());
            }

            GDDSSubscribeItemAdapter adapter = new GDDSSubscribeItemAdapter(
                    R.layout.home_item_gdds_subs_item);
            adapter.setOnItemClickListener((adapter1, view, position) -> {
                Intent intent = new Intent();
                intent.setClass(getContext(), HomeGddsDetailActivity.class);
                context.startActivity(intent);
            });
            binding.rvCategoryView.setAdapter(adapter);
            adapter.setNewData(dataList);
        }
    }
}
