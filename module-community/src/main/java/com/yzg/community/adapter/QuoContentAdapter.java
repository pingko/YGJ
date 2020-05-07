package com.yzg.community.adapter;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.community.databinding.CommunityQuoItemViewBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class QuoContentAdapter extends BaseQuickAdapter<BaseCustomViewModel, BaseViewHolder> {

    public QuoContentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, @Nullable BaseCustomViewModel baseCustomViewModel) {
        if (baseCustomViewModel == null){
            return;
        }
        CommunityQuoItemViewBinding binding = baseViewHolder.getBinding();
        if (binding != null){
            binding.setViewModel((QuotationItemViewModel) baseCustomViewModel);
            binding.executePendingBindings();
        }
    }
}
