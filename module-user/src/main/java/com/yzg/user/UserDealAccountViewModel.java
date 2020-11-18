package com.yzg.user;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yzg.base.activity.IBaseView;
import com.yzg.base.http.HttpService;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.base.viewmodel.MvvmBaseViewModel;
import com.yzg.user.bean.UserDealBean;

import java.util.List;
import java.util.TreeMap;


/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-23
 */
public class UserDealAccountViewModel
        extends MvvmBaseViewModel<IBaseView> {


    public MutableLiveData< List<UserDealBean>> successData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void loadData() {
        TreeMap<String, String> map = new TreeMap<>();
        String acctNo = MmkvHelper.getInstance().getMmkv().decodeString("acctNo");
        map.put("acctNo",acctNo);
        OkGo.<String>post(HttpService.EB_DealList)
                .params(map)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            if (jsonObject!=null && jsonObject.containsKey("rows")){
                                List<UserDealBean> beans = JSONObject.parseArray(jsonObject.getString("rows"), UserDealBean.class);
                                successData.setValue(beans);
                            }else {
                                errorLiveData.setValue(response.message());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        errorLiveData.setValue(response.body());
                    }
                });
    }


    @Override
    public void detachUi() {
        super.detachUi();
        OkGo.getInstance().cancelTag(this);

    }
}
