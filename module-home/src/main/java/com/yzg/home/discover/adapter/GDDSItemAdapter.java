package com.yzg.home.discover.adapter;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.tamsiree.rxkit.RxImageTool;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeItemCategoryItemSubjectCardViewBinding;
import com.yzg.home.databinding.HomeItemCategoryItemSubjectGddsViewBinding;
import com.yzg.home.discover.bean.SquareCard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 应用模块:
 * <p>
 * 类描述: 专题adapter
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class GDDSItemAdapter
        extends BaseQuickAdapter<SquareCard, BaseViewHolder> {

    public GDDSItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder,
                                           int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder,
                           @Nullable SquareCard squareCard) {
        if (squareCard == null) {
            return;
        }

        HomeItemCategoryItemSubjectGddsViewBinding binding =
                baseViewHolder.getBinding();
//        Glide.with(binding.ivImage.getContext())
//                .load(R.drawable.)
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into(binding.ivImage);
        if (binding != null) {
            binding.setViewModel(squareCard);
            binding.executePendingBindings();
        }
    }
}
