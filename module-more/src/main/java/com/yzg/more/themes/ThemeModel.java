package com.yzg.more.themes;

import com.yzg.base.model.BaseModel;
import com.yzg.base.utils.GsonUtils;
import com.yzg.more.themes.bean.TabInfo;

import io.reactivex.disposables.Disposable;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-22
 */
public class ThemeModel<T> extends BaseModel<T> {

    private Disposable disposable;

    @Override
    protected void load() {
//        disposable = EasyHttp.get("/api/v7/tag/tabList")
//                  .cacheKey(getClass().getSimpleName())
//                  .execute(new SimpleCallBack<String>() {
//                      @Override
//                      public void onError(ApiException e) {
//                          loadFail(e.getMessage());
//                      }
//
//                      @Override
//                      public void onSuccess(String s) {
//                         parseData(s);
//                      }
//                  });
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    private void parseData(String s) {
        TabInfo tabInfo = GsonUtils.fromLocalJson(s, TabInfo.class);
        if (tabInfo != null){
            loadSuccess((T) tabInfo.getTabInfo().getTabList());
        }
    }
}
