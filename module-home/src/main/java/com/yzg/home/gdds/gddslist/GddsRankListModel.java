package com.yzg.home.gdds.gddslist;

import android.text.TextUtils;

import com.yzg.base.model.BasePagingModel;
import com.yzg.common.contract.BaseCustomViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class GddsRankListModel<T> extends BasePagingModel<T> {

    private Disposable disposable;

    private Disposable disposable1;

    @Override
    protected void load() {
//        disposable = EasyHttp.get("/api/v7/topic/list")
//            .cacheKey(getClass().getSimpleName())
//            .execute(new SimpleCallBack<String>()
//            {
//                @Override
//                public void onError(ApiException e)
//                {
//                    loadFail(e.getMessage(), isRefresh);
//                }
//
//                @Override
//                public void onSuccess(String s)
//                {
//                    parseData(s);
//                }
//            });

        parseData("");
    }

    private void loadMore(String nextPageUrl) {
//        disposable1 = EasyHttp.get(nextPageUrl)
//            .cacheMode(CacheMode.NO_CACHE)
//            .execute(new SimpleCallBack<String>()
//            {
//                @Override
//                public void onError(ApiException e)
//                {
//                    loadFail(e.getMessage(), isRefresh);
//                }
//
//                @Override
//                public void onSuccess(String s)
//                {
//                    parseData(s);
//                }
//            });

        parseData("");
    }

    private void parseData(String s) {
//        TopicBean topicBean = GsonUtils.fromLocalJson(s,TopicBean.class);
        List<BaseCustomViewModel> viewModels = new ArrayList<>();
//        if (topicBean != null){
//            nextPageUrl = topicBean.getNextPageUrl();
//            for (TopicBean.ItemListBean itemData : topicBean.getItemList()){
//                ThemesItemViewModel viewModel = new ThemesItemViewModel();
//                viewModel.coverUrl = itemData.getData().getImageUrl();
//                viewModel.title = itemData.getData().getTitle();
//                viewModel.description = itemData.getData().getViewCount()+" 人浏览 / "+itemData.getData().getJoinCount()+"人参与";
//                viewModels.add(viewModel);
//            }
//        }

        for (int i = 0; i < 10; i++) {
            GddsItemViewModel viewModel = new GddsItemViewModel();
            viewModel.name = "诸葛亮";
            viewModel.fans = "粉丝数1100";
            viewModels.add(viewModel);
        }

        loadSuccess((T) viewModels, viewModels.size() == 0, isRefresh);
    }

    public void refresh() {
        isRefresh = true;
        load();
    }

    public void loadMore() {
        isRefresh = false;
        if (!TextUtils.isEmpty(nextPageUrl)) {
            loadMore(nextPageUrl);
        } else {
            loadSuccess(null, true, isRefresh);
        }
    }

    @Override
    public void cancel() {
        super.cancel();
//        EasyHttp.cancelSubscription(disposable);
//        EasyHttp.cancelSubscription(disposable1);
    }
}
