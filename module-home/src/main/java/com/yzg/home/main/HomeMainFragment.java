package com.yzg.home.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.guannan.chartmodule.utils.LogUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.base.model.MarkettBean;
import com.yzg.base.storage.MmkvHelper;
import com.yzg.base.utils.GsonUtils;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.recyclerview.RecyclerItemDecoration;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.common.utils.DensityUtils;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentMainBinding;
import com.yzg.home.discover.adapter.GDDSItemAdapter;
import com.yzg.home.discover.adapter.HomeMainJLYTAdapter;
import com.yzg.home.discover.bean.SquareCard;
import com.yzg.home.jlyt.HomeJlytActivity;
import com.yzg.home.jlyt.HomeJlytDetailActivity;
import com.yzg.home.jlyt.JlytBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述: 首页
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-09
 */
@Route(path = RouterFragmentPath.Home.PAGER_HOMES)
public class HomeMainFragment
        extends MvvmLazyFragment<HomeFragmentMainBinding, HomeMainViewModel> {
    GDDSItemAdapter gddsAdapter;
    AutoVerticalScrollTextView textView;
//    ArrayList<SquareCard> dataList = new ArrayList<>();
    List<JlytBean> jlytBeans = new ArrayList<>();
    CommonAdapter jlytAdapter;

    public static HomeMainFragment newInstance() {
        HomeMainFragment fragment = new HomeMainFragment();
        return fragment;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        ARouter.getInstance().inject(this);
        // 确定Item的改变不会影响RecyclerView的宽高
        binding.rvCategoryView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getContext(), 2);
        binding.rvCategoryView.setLayoutManager(gridLayoutManager);
        binding.rvCategoryView.addItemDecoration(new RecyclerItemDecoration(0,
                0, DensityUtils.dip2px(getContext(), 13), DensityUtils.dip2px(getContext(), 12)));
        jlytAdapter = new CommonAdapter<JlytBean>(getActivity(), R.layout.home_main_jlyt_item, jlytBeans) {
            @Override
            protected void convert(ViewHolder holder, JlytBean bean, int position) {
                holder.setText(R.id.tv_title, showJlytTitle(bean.getProductType(), bean.getPoint()));
                holder.setText(R.id.tv_zf, bean.getRate() + "%");
                holder.setVisible(R.id.iv_hot, position <= 3 ? true : false);
            }
        };

        binding.rvCategoryView.setAdapter(jlytAdapter);
        jlytAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                Intent intent = new Intent(getContext(), HomeJlytDetailActivity.class);
                intent.putExtra("productId", jlytBeans.get(i).getProductId());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });


        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            loadFinish();
            viewModel.loadMarkets(1,10,"");//加载行情列表
            viewModel.loadJlyt();//加载最近积利银条成交
            binding.refreshLayout.finishRefresh(true);
        });


        binding.rvGdds.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvGdds.setLayoutManager(layoutManager);
        binding.rvGdds.addItemDecoration(new RecyclerItemDecoration(0,
                0, DensityUtils.dip2px(getContext(), 16), 0));
        gddsAdapter = new GDDSItemAdapter(
                R.layout.home_item_category_item_subject_gdds_view);
        gddsAdapter.setOnItemClickListener((adapter1, view, position) -> {
            RxToast.showToast("功能正在开发中，敬请期待!");
//            Intent intent = new Intent();
//            intent.setClass(getContext(), HomeGddsDetailActivity.class);
//            getContext().startActivity(intent);
        });
        binding.rvGdds.setAdapter(gddsAdapter);

        textView = binding.tvScroll;
        textView.setText(strings[0]);
        textView.postDelayed(() -> handler.sendEmptyMessage(199), 1000);

        binding.llGdds.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), HomeGddsListctivity.class);
            getActivity().startActivity(intent);
        });

        binding.tvGdds.setOnClickListener(view -> {
            RxToast.showToast("功能正在开发中，敬请期待!");
//            Intent intent = new Intent();
//            intent.setClass(getContext(), HomeGddsListctivity.class);
//            getActivity().startActivity(intent);
        });
        binding.llFerg.setOnClickListener(view -> {
            ARouter.getInstance()
                    .build(RouterActivityPath.Deal.PAGER_DEAL_BUY)
                    .navigation();

        });
        binding.tvJlyts.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), HomeJlytActivity.class));
        });
        binding.llJlyt.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), HomeJlytActivity.class));
        });
        binding.llSszx.setOnClickListener(view -> {
            LiveEventBus
                    .get("index")
                    .post(3);
        });
        getQuoList();
        viewModel.loadMarkets(1,10,"");//加载行情列表
        viewModel.loadJlyt();//加载最近积利银条成交
        loadFinish();
//        if (!GsonUtils.isShowTrue()) {
//            binding.llFerg.setVisibility(View.GONE);
//            binding.llJlyt.setVisibility(View.GONE);
//            binding.tvGdds.setText("经验大师");
//            binding.tvJlyts.setText("积利经验");
//        }

//        viewModel.reLogin.observe(this, s -> {
//            MmkvHelper.getInstance().getMmkv().clearAll();
//
//            ARouter.getInstance()
//                    .build(RouterActivityPath.User.PAGER_LOGIN)
//                    .navigation();
//
//            getActivity().finish();
//        });

    }

    public String showJlytTitle(String type, int point) {
        if ("1".equals(type)) {
            type = "1";
        } else if ("2".equals(type)) {
            type = "3";
        } else if ("3".equals(type)) {
            type = "6";
        } else if ("4".equals(type)) {
            type = "12";
        }
        return type + "个月," + point + "克起";
    }

    private CommonAdapter marketAdapter;
    private List<MarkettBean> quotationBeans = new ArrayList<>();

    private void getQuoList() {
        binding.rvMarket.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvMarket.setLayoutManager(layoutManager);
//        binding.rvMarket.addItemDecoration(new RecyclerItemDecoration(0,
//                0, DensityUtils.dip2px(getContext(), 16), 0));
        marketAdapter = new CommonAdapter<MarkettBean>(getActivity(), R.layout.home_martket_item_view, quotationBeans) {
            @Override
            protected void convert(ViewHolder holder, MarkettBean bean, int position) {

                holder.setText(R.id.tv_name, bean.getVarietynm() + "");
                holder.setText(R.id.tv_td_price, bean.getLastPrice() + "");
                holder.setText(R.id.tv_td_change, bean.getChangePrice() + "  " + bean.getChangeMargin() + "");
//                if (!GsonUtils.isShowTrue()) {
//                    holder.setText(R.id.tv_name, "青铜" + position + "大师");
//                }
            }
        };
        marketAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
//                if (GsonUtils.isShowTrue())
                    ARouter.getInstance()
                            .build(RouterActivityPath.Quotation.Quotation_main_chart)
                            .withSerializable("MarkettBean", quotationBeans.get(i))
                            .navigation();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        binding.rvMarket.setAdapter(marketAdapter);
        viewModel.successData.observe(this, markettBeans -> {
            quotationBeans.clear();
            quotationBeans.addAll(markettBeans);
            marketAdapter.notifyDataSetChanged();
        });

        viewModel.jlytBeansData.observe(this, jlytBeans1 -> {
            Logger.e("积利银条");
            jlytBeans.clear();
            if (jlytBeans1.size() <= 4) {
                jlytBeans.addAll(jlytBeans1);
            } else {
                for (int i = 0; i < jlytBeans1.size(); i++) {
                    if (i == 4)
                        break;
                    jlytBeans.add(jlytBeans1.get(i));
                }
            }
            jlytAdapter.notifyDataSetChanged();
        });
    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 199) {
                textView.next();
                number++;
                textView.setText(strings[number % strings.length]);
                handler.sendEmptyMessageDelayed(199, 2000);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
    }

    private String[] strings = {"135****0001  刘先生 产品名称  25克 300元", "135****0001  刘先生 产品名称  25克 300元"};
    private String[] strings1 = {"135****0001  刘先生 产品名称  25豆 300元", "135****0001  刘先生 产品名称  25豆 300元"};
    public int number = 0;


    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_main;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected HomeMainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(HomeMainViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    /**
     * 模拟假数据  激励银条  跟单大师
     */
    public void loadFinish() {
////        if (GsonUtils.isShowTrue()) {
//            dataList.clear();
//            for (int i = 0; i < 4; i++) {
//                dataList.add(new SquareCard("1个月,100豆起", "", "成长经验"));
//            }
//            gddsAdapter.setNewData(dataList);
//            showContent();
//        }
    }
}
