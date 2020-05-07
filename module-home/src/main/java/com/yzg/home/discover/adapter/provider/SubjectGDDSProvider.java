package com.yzg.home.discover.adapter.provider;

import android.content.Intent;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.recyclerview.RecyclerItemDecoration;
import com.yzg.common.utils.DensityUtils;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeItemSubjectGddsViewBinding;
import com.yzg.home.discover.adapter.GDDSItemAdapter;
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
public class SubjectGDDSProvider extends BaseItemProvider<BaseCustomViewModel> {
    @Override
    public int getItemViewType() {
        return IDisCoverItemType.THEME_GDDS_VIEW;
    }

    @Override
    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder,
                                    int viewType) {
        HomeItemSubjectGddsViewBinding binding =
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
        return R.layout.home_item_subject_gdds_view;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder,
                        @Nullable BaseCustomViewModel baseCustomViewModel) {
        if (baseCustomViewModel == null) {
            return;
        }
        HomeItemSubjectGddsViewBinding binding = baseViewHolder.getBinding();
        if (binding != null) {
//            SubjectCardBean cardBean = (SubjectCardBean) baseCustomViewModel;
//            binding.tvTitle.setText(cardBean.getData().getHeader().getTitle());
//            binding.tvActionTitle
//                    .setText(cardBean.getData().getHeader().getRightText());
            ArrayList<SquareCard> dataList = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                dataList.add(new SquareCard());
            }
            GDDSItemAdapter adapter = new GDDSItemAdapter(
                    R.layout.home_item_category_item_subject_gdds_view);
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
