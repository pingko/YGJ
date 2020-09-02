package com.yzg.home.main;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.http.HttpService;
import com.yzg.base.model.BaseModel;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.home.discover.bean.SubjectCardBean;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import io.reactivex.disposables.Disposable;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-15
 */
public class HomeMainModel<T> extends BaseModel<T> {
    public static final String DEFAULT_URL =
            "http://baobab.kaiyanapp.com/api/v7/index/tab/discovery?udid=fa53872206ed42e3857755c2756ab683fc22d64a&vc=591&vn=6.2.1&size=720X1280&deviceModel=Che1-CL20&first_channel=eyepetizer_zhihuiyun_market&last_channel=eyepetizer_zhihuiyun_market&system_version_code=19";
    private Disposable disposable;

    @Override
    protected void load() {
//        disposable = EasyHttp.get(DEFAULT_URL)
//              .cacheKey(getClass().getSimpleName())
//              .execute(new SimpleCallBack<String>()
//              {
//                  @Override
//                  public void onError(ApiException e)
//                  {
//                      loadFail(e.getMessage());
//                  }
//
//                  @Override
//                  public void onSuccess(String s)
//                  {
//                      parseJson(s);
//                  }
//              });
        parseJson("");
        loadriceList();
    }

    private void parseJson(String s) {
        List<BaseCustomViewModel> viewModels = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            SubjectCardBean subjectCardBean = new SubjectCardBean();
            viewModels.add(subjectCardBean);
        }

        loadSuccess((T) viewModels);
    }
    public void loadriceList() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("pageNum","1");
        map.put("pageSize","10");
        OkGo.<String>post(HttpService.EB_Quotation_priceList)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
//                            JSONObject jsonObject = JSON.parseObject(response.body());
//                            lastPrice.setValue(jsonObject.getFloatValue("lastPrice") / 1000);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
//                        errorLiveData.setValue(response.body());
                    }
                });
    }

    @Override
    public void cancel() {
        super.cancel();
        OkGo.getInstance().cancelTag(this);
    }
}
