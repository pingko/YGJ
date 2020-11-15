package com.yzg.user.address;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONArray;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.common.contract.AddressBean;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.user.R;
import com.yzg.user.databinding.UserActivityAddressBinding;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


@Route(path = RouterActivityPath.User.PAGER_Address)
public class UserAddressActivity extends MvvmBaseActivity<UserActivityAddressBinding, UserAddressViewModel> {

    private CommonAdapter jlytAdapter;
    private List<AddressBean> addressList;
    @Autowired(name = "type")
    public int type;//选择提货地址

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
        String addressArray = MmkvHelper.getInstance().getMmkv().decodeString("address");
        Log.e("aa",addressArray);
        if (!TextUtils.isEmpty(addressArray) && addressArray.length() > 2) {
            addressList.clear();
            List<AddressBean> addressList1 = JSONArray.parseArray(addressArray, AddressBean.class);
            addressList.addAll(addressList1);
        }
        jlytAdapter = new CommonAdapter<AddressBean>(this, R.layout.user_add_item, addressList) {
            @Override
            protected void convert(ViewHolder holder, AddressBean bean, int position) {
                TextView normal = holder.getView(R.id.tv_select);
                TextView chooseAdd = holder.getView(R.id.tv_select_set);
                holder.setText(R.id.tv_name, bean.getName()+" "+bean.getPhone());
                holder.setText(R.id.tv_address, bean.getArea()+bean.getAddress());
//                normal.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE);
//                chooseAdd.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                holder.setOnClickListener(R.id.iv_del, view -> {
                    addressList.remove(position);
                    String a = JSONArray.toJSONString(addressList);
                    MmkvHelper.getInstance().getMmkv().encode("address", a);
                    jlytAdapter.notifyDataSetChanged();
                });
            }
        };
        binding.rvJlyt.setLayoutManager(new LinearLayoutManager(this));
//        binding.rvJlyt.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.common_color_text_gray), ConvertUtils.dp2px(15),ConvertUtils.dp2px(15)));
        binding.rvJlyt.setAdapter(jlytAdapter);

//        binding.refreshLayout.setOnRefreshListener(refreshLayout -> binding.refreshLayout.finishRefresh(500));
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(this));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
//            viewModel.tryToRefresh();
            binding.refreshLayout.finishRefresh(500);
        });
        binding.ivBack.setOnClickListener(view -> {
            if (type == 1) {
                LiveEventBus
                        .get("addressIndex")
                        .post(0);
            }
            finish();
        });
        jlytAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                if (type == 1) {
                    LiveEventBus
                            .get("addressIndex")
                            .post(i);
                    finish();
                }
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });

//        viewModel.initModel();
//        TreeMap map = new TreeMap();
//        map.put("loginName",binding.etPhone.getText().toString());
//        map.put("phonenumber",binding.etPhone.getText().toString());
//        map.put("userName",binding.etName.getText().toString());
//        map.put("password",binding.etPwd.getText().toString());
//        viewModel.setRequestParams(map);
//        viewModel.tryToRefresh();

        binding.tvAdd.setOnClickListener(view -> startActivityForResult(new Intent(UserAddressActivity.this, UserAddressAddActivity.class),10001));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 &&resultCode == RESULT_OK){
            String addressArray = MmkvHelper.getInstance().getMmkv().decodeString("address");
            addressList.clear();
            List<AddressBean> addressList1 = JSONArray.parseArray(addressArray, AddressBean.class);
            addressList.addAll(addressList1);
            jlytAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_address;
    }

    @Override
    protected void onRetryBtnClick() {

    }

}
