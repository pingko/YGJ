package com.yzg.home.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.tamsiree.rxkit.view.RxToast;
import com.yzg.base.fragment.MvvmLazyFragment;
import com.yzg.common.contract.BaseCustomViewModel;
import com.yzg.common.recyclerview.RecyclerItemDecoration;
import com.yzg.common.router.RouterFragmentPath;
import com.yzg.common.utils.DensityUtils;
import com.yzg.home.R;
import com.yzg.home.databinding.HomeFragmentMainBinding;
import com.yzg.home.discover.adapter.GDDSItemAdapter;
import com.yzg.home.discover.adapter.HomeMainJLYTAdapter;
import com.yzg.home.discover.bean.SquareCard;
import com.yzg.home.jlyt.HomeJlytActivity;

import java.util.ArrayList;

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
        extends MvvmLazyFragment<HomeFragmentMainBinding, HomeMainViewModel>
        implements IHomeMainView {
    private HomeMainJLYTAdapter adapter;
//    private ProviderGDDSAdapter gddsAdapter;
    GDDSItemAdapter gddsAdapter;
    AutoVerticalScrollTextView textView;
    ArrayList<SquareCard> dataList = new ArrayList<>();

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

        adapter = new HomeMainJLYTAdapter(R.layout.home_item_category_item_subject_card_view);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), HomeGddsDetailActivity.class);
            getActivity().startActivity(intent);
        });
//        adapter.setNewData(dataList);

        binding.rvCategoryView.setAdapter(adapter);


        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.tryToRefresh();
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
            Intent intent = new Intent();
            intent.setClass(getContext(), HomeGddsDetailActivity.class);
            getContext().startActivity(intent);
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
            Intent intent = new Intent();
            intent.setClass(getContext(), HomeGddsListctivity.class);
            getActivity().startActivity(intent);
        });
        binding.llFerg.setOnClickListener(view -> {
            RxToast.normal("开发中");
        });
        binding.llJlyt.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), HomeJlytActivity.class));
        });
        binding.llSszx.setOnClickListener(view -> {
            LiveEventBus
                    .get("index")
                    .post(3);
        });
        setLoadSir(binding.refreshLayout);
        showLoading();
        viewModel.initModel();
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

    private String[] strings = {"135*****001  刘先生 产品名称  25克 300元", "135*****001  刘先生 产品名称  25克 300元"};
    public int number = 0;

//    private View getFooterView() {
//        return LayoutInflater.from(getContext())
//                .inflate(R.layout.home_item_foote_view,
//                        binding.rvDiscoverView,
//                        false);
//    }

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

    @Override
    public void onDataLoadFinish(ArrayList<BaseCustomViewModel> viewModels,
                                 boolean isEmpty) {
        if (isEmpty) {
            binding.refreshLayout.finishRefresh(false);
        } else {
            binding.refreshLayout.finishRefresh(true);
        }
        dataList.clear();
        for (int i = 0; i < 4; i++) {
            dataList.add(new SquareCard());
        }
        adapter.setNewData(dataList);
        gddsAdapter.setNewData(dataList);
        showContent();
    }
}
