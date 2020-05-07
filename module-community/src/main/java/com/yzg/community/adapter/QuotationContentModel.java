package com.yzg.community.adapter;

import android.text.TextUtils;

import com.yzg.base.model.BasePagingModel;
import com.yzg.common.contract.BaseCustomViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;

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
public class QuotationContentModel<T> extends BasePagingModel<T> {
    private String typeName = "";

//    private String apiUrl = "";

    private Disposable disposable;

    private Disposable disposable1;

    public QuotationContentModel(String typeName) {
        this.typeName = typeName;
//        this.apiUrl = apiUrl;
    }

    @Override
    protected void load() {
//        if (!TextUtils.isEmpty(apiUrl)) {
//            disposable = EasyHttp.get(apiUrl)
//                    .cacheKey(getClass().getSimpleName())
//                    .execute(new SimpleCallBack<String>() {
//                        @Override
//                        public void onError(ApiException e) {
//                            loadFail(e.getMessage(), isRefresh);
//                        }
//
//                        @Override
//                        public void onSuccess(String s) {
//                            parseData(s);
//                        }
//                    });
//        }

        parseData("");
    }

    private void parseData(String s) {
        ArrayList<BaseCustomViewModel> viewModels = new ArrayList<>();
        viewModels.clear();
//        ThemesContent themesContent =
//                GsonUtils.fromLocalJson(s, ThemesContent.class);
//        if (themesContent != null) {
//            nextPageUrl = themesContent.getNextPageUrl();
//            for (ThemesContent.ItemListBean itemListBean : themesContent
//                    .getItemList()) {
//                ThemesItemViewModel itemViewModel = new ThemesItemViewModel();
//                itemViewModel.coverUrl = itemListBean.getData().getIcon();
//                itemViewModel.title = itemListBean.getData().getTitle();
//                itemViewModel.description =
//                        itemListBean.getData().getDescription();
//                viewModels.add(itemViewModel);
//            }
//        }
        for (int i = 0; i < 4; i++) {
            QuotationItemViewModel itemViewModel = new QuotationItemViewModel();
            itemViewModel.title = "黄金T+D";
            itemViewModel.code = "黄金T+D";
            itemViewModel.subTitle = "Au(T+D)";
            itemViewModel.price = "28" + i + ".0" + i;
            itemViewModel.range = "-0.85%";
            viewModels.add(itemViewModel);
        }


        loadSuccess((T) viewModels, viewModels.size() == 0, isRefresh);
    }

    private void loadMore(String nextPageUrl) {
        disposable1 = EasyHttp.get(nextPageUrl)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        loadFail(e.getMessage(), isRefresh);
                    }

                    @Override
                    public void onSuccess(String s) {
                        parseData(s);
                    }
                });
    }

    public void loadMore() {
        isRefresh = false;
        if (!TextUtils.isEmpty(nextPageUrl)) {
            loadMore(nextPageUrl);
        } else {
            loadSuccess(null, true, isRefresh);
        }
    }

    public void refresh() {
        isRefresh = true;
        load();
    }

    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
        EasyHttp.cancelSubscription(disposable1);
    }
}
