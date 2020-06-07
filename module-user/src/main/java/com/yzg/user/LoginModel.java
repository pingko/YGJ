package com.yzg.user;

import android.text.TextUtils;
import android.util.Log;

import com.yzg.base.model.BasePagingModel;

import io.reactivex.disposables.Disposable;

/**
 * 应用模块: 首页
 * <p>
 * 类描述: 首页 业务逻辑 处理中心
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-10
 */
public class LoginModel<T> extends BasePagingModel<T> {

    private Disposable disposable;
    private Disposable disposable1;

    @Override
    protected void load() {
//        disposable = EasyHttp.get("/api/v5/index/tab/allRec")
//                .cacheKey(getClass().getSimpleName())
//                .execute(new SimpleCallBack<String>() {
//                    @Override
//                    public void onError(ApiException e) {
//                        loadFail(e.getMessage(), isRefresh);
//                    }
//
//                    @Override
//                    public void onSuccess(String s) {
//                        parseJson(s);
//                    }
//                });
    }

    public void loadMore(String url) {
//        disposable1 = EasyHttp.get(url)
//                .cacheMode(CacheMode.NO_CACHE)
//                .execute(new SimpleCallBack<String>() {
//                    @Override
//                    public void onError(ApiException e) {
//                        loadFail(e.getMessage(), isRefresh);
//                    }
//
//                    @Override
//                    public void onSuccess(String s) {
//                        parseJson(s);
//                        Log.d("NominateModel", s);
//                    }
//                });
    }

    public void parseJson(String s) {

    }

    @Override
    public void cancel() {
        super.cancel();
//        EasyHttp.cancelSubscription(disposable);
//        EasyHttp.cancelSubscription(disposable1);
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


}
