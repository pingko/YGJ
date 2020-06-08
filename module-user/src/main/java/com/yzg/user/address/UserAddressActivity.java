package com.yzg.user.address;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.user.R;
import com.yzg.user.databinding.UserActivityAddressBinding;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Path;


@Route(path = RouterActivityPath.User.PAGER_Address)
public class UserAddressActivity extends MvvmBaseActivity<UserActivityAddressBinding, UserAddressViewModel> implements IAddressView {

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
        for (int i = 0; i < 2; i++) {
            AddressBean bean = new AddressBean();
            bean.setName("王富贵，1516000123" + i);
            bean.setAddress("安徽省 合肥市 北京路 朝阳科技工业园西区15栋007" + i);
            addressList.add(bean);
        }

        jlytAdapter = new CommonAdapter<AddressBean>(this, R.layout.user_add_item, addressList) {
            @Override
            protected void convert(ViewHolder holder, AddressBean bean, int position) {
                TextView normal = holder.getView(R.id.tv_select);
                TextView chooseAdd = holder.getView(R.id.tv_select_set);
                holder.setText(R.id.tv_name, bean.getName());
                holder.setText(R.id.tv_address, bean.getAddress());
                normal.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE);
                chooseAdd.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

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
        binding.ivBack.setOnClickListener(view -> finish());
        jlytAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                if (type == 1) {
                    finish();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });

        viewModel.initModel();
//        TreeMap map = new TreeMap();
//        map.put("loginName",binding.etPhone.getText().toString());
//        map.put("phonenumber",binding.etPhone.getText().toString());
//        map.put("userName",binding.etName.getText().toString());
//        map.put("password",binding.etPwd.getText().toString());
//        viewModel.setRequestParams(map);
//        viewModel.tryToRefresh();

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

    @Override
    public void onDataLoadFinish(BaseCustomViewModel viewModel) {

    }
}
