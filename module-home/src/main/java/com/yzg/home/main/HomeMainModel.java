package com.yzg.home.main;

import com.yzg.base.model.BaseModel;
import com.yzg.base.utils.GsonUtils;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.home.discover.bean.BannerBean;
import com.yzg.home.discover.bean.BriefCard;
import com.yzg.home.discover.bean.CategoryCardBean;
import com.yzg.home.discover.bean.SubjectCardBean;
import com.yzg.home.discover.bean.TextCardbean;
import com.yzg.home.discover.bean.TopBannerBean;
import com.yzg.home.discover.bean.viewmodel.BriefCardViewModel;
import com.yzg.home.discover.bean.viewmodel.ContentBannerViewModel;
import com.yzg.home.discover.bean.viewmodel.TopBannerViewModel;
import com.yzg.home.nominate.bean.VideoSmallCardBean;
import com.yzg.home.nominate.bean.viewmodel.TitleViewModel;
import com.yzg.home.nominate.bean.viewmodel.VideoCardViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    }

    private void parseJson(String s) {
        List<BaseCustomViewModel> viewModels = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            SubjectCardBean subjectCardBean = new SubjectCardBean();
            viewModels.add(subjectCardBean);
        }

        loadSuccess((T) viewModels);
    }


    @Override
    public void cancel() {
        super.cancel();
        EasyHttp.cancelSubscription(disposable);
    }
}
