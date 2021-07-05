package com.yzg.user.address;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONArray;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.common.contract.AddressBean;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.user.R;
import com.yzg.user.databinding.UserActivityAddressAddBinding;

import java.util.ArrayList;
import java.util.List;


@Route(path = RouterActivityPath.User.PAGER_Address_add)
public class UserAddressAddActivity extends MvvmBaseActivity<UserActivityAddressAddBinding, UserAddressViewModel> {

    private List<AddressBean> addressList;
    @Autowired(name = "type")
    public int type;//选择提货地址

    private String addressArray;
    JDCityPicker cityPicker;
    private JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();
    public JDCityConfig.ShowType mWheelType = JDCityConfig.ShowType.PRO_CITY;

    @Override
    protected UserAddressViewModel getViewModel() {
        return ViewModelProviders.of(this).get(UserAddressViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        initData();
    }

    private void initData() {
        addressList = new ArrayList<>();
        addressArray = MmkvHelper.getInstance().getMmkv().decodeString("address");
        if (!TextUtils.isEmpty(addressArray) && addressArray.length() > 2) {
            List<AddressBean> addressList1 = JSONArray.parseArray(addressArray, AddressBean.class);
            addressList.addAll(addressList1);
        }

        binding.ivBack.setOnClickListener(view -> finish());
        binding.tvAdd.setOnClickListener(view -> {
            AddressBean bean = new AddressBean();
            if (TextUtils.isEmpty(binding.etName.getText().toString().trim())){
                RxToast.normal("请输入姓名");
                return;
            }
            if (TextUtils.isEmpty(binding.etPhone.getText().toString().trim())){
                RxToast.normal("请输入电话");
                return;
            }
            if (TextUtils.isEmpty(binding.tvArea.getText().toString().trim())){
                RxToast.normal("请输入地区");
                return;
            }
            if (TextUtils.isEmpty(binding.etDetail.getText().toString().trim())){
                RxToast.normal("请输入详细地址");
                return;
            }
            bean.setName(binding.etName.getText().toString());
            bean.setPhone(binding.etPhone.getText().toString());
            String area = binding.tvArea.getText().toString();
            bean.setArea(area);
            String[] p = area.split(" ");
            bean.setProvince(p[0]);
            bean.setCity(p[1]);
            bean.setDistrict(p[2]);
            bean.setAddress(binding.etDetail.getText().toString());
            addressList.add(bean);
            String a = JSONArray.toJSONString(addressList);
            MmkvHelper.getInstance().getMmkv().encode("address", a);
            setResult(RESULT_OK);
            finish();
        });

        binding.tvArea.setOnClickListener(view -> {
//            isSHowKeyboard(this,binding.etDetail);

            hintKeyBoard();
            cityPicker.showCityPicker();
        });

        initPicker();

    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 判断软键盘是否弹出
     */
    public static boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    private void initPicker() {

        mWheelType = JDCityConfig.ShowType.PRO_CITY_DIS;
//        setWheelType(mWheelType);
        jdCityConfig.setShowType(mWheelType);

        cityPicker = new JDCityPicker();
        //初始化数据
        cityPicker.init(this);
        //设置JD选择器样式位只显示省份和城市两级
        cityPicker.setConfig(jdCityConfig);
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                String proData = null;
                if (province != null) {
                    proData = "name:  " + province.getName() + "   id:  " + province.getId();
                }

                String cituData = null;
                if (city != null) {
                    cituData = "name:  " + city.getName() + "   id:  " + city.getId();
                }


                String districtData = null;
                if (district != null) {
                    districtData = "name:  " + district.getName() + "   id:  " + district.getId();
                }

                binding.tvArea.setText(province.getName() + " " + city.getName() + " " + district.getName());
//
//                if (mWheelType == JDCityConfig.ShowType.PRO_CITY_DIS) {
//                    binding.tvArea.setText("城市选择结果：\n" + proData + "\n"
//                            + cituData + "\n"
//                            + districtData);
//                } else {
//                    binding.tvArea.setText("城市选择结果：\n" + proData + "\n"
//                            + cituData + "\n"
//                    );
//                }
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_address_add;
    }

    @Override
    protected void onRetryBtnClick() {

    }


}
