package com.yzg.community.recommend;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.guannan.chartmodule.chart.KMasterChartView;
import com.guannan.chartmodule.chart.KSubChartView;
import com.guannan.chartmodule.chart.MarketFigureChart;
import com.guannan.chartmodule.data.ExtremeValue;
import com.guannan.chartmodule.data.KLineToDrawItem;
import com.guannan.chartmodule.data.SubChartData;
import com.guannan.chartmodule.helper.ChartDataSourceHelper;
import com.guannan.chartmodule.helper.TechParamType;
import com.guannan.chartmodule.inter.IChartDataCountListener;
import com.guannan.chartmodule.inter.IPressChangeListener;
import com.guannan.simulateddata.LocalUtils;
import com.guannan.simulateddata.parser.KLineParser;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.model.MarkettBean;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.community.R;
import com.yzg.community.databinding.CommunityActivityQuotationBinding;

import java.util.List;


@Route(path = RouterActivityPath.Quotation.Quotation_main)
public class QuotationActivity extends MvvmBaseActivity<CommunityActivityQuotationBinding, QuotationViewModel> implements IChartDataCountListener<List<KLineToDrawItem>>, IPressChangeListener {

    @Autowired(name = "MarkettBean")
    public MarkettBean bean;//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        Log.e("QuotationActivity","MarkettBean: "+((bean==null)?true:false));
        if (bean!=null){
            binding.tvTitle.setText(bean.getVarietynm());
            binding.tvPrice.setText(bean.getLastPrice() + "");
            binding.tvChangeNumber.setText(bean.getChangePrice() + "   " + bean.getChangeMargin());
//                binding.tvChangeNumber.setTextColor(bean.getChangePrice() > 0 ? getResources().getColor(R.color.community_red) : getResources().getColor(R.color.community_green));
//                binding.tvPrice.setTextColor(bean.getChangePrice() > 0 ? getResources().getColor(R.color.community_red) : getResources().getColor(R.color.community_green));
            binding.tvChangeNumber.setTextColor(getResources().getColor(R.color.community_green));
            binding.tvPrice.setTextColor(getResources().getColor(R.color.community_green));
            binding.tvJj.setText(bean.getYesyPrice() + "");
            binding.tvZd.setText(bean.getLowPrice() + "");
            binding.tvJk.setText(bean.getOpenPrice() + "");
            binding.tvZs.setText(bean.getLastPrice() + "");
            binding.tvZg.setText(bean.getHighPrice() + "");
        }else {
            initData();
        }
        binding.ivBack.setOnClickListener(view -> {
            finish();
        });
        initK();
    }


    private void initData() {
        viewModel.loadData();
        viewModel.successData.observe(this, bean -> {
            if (bean != null) {
                binding.tvTitle.setText(bean.getVarietynm());
                binding.tvPrice.setText(bean.getLastPrice() + "");
                binding.tvChangeNumber.setText(bean.getChangePrice() + "   " + bean.getChangeMargin());
//                binding.tvChangeNumber.setTextColor(bean.getChangePrice() > 0 ? getResources().getColor(R.color.community_red) : getResources().getColor(R.color.community_green));
//                binding.tvPrice.setTextColor(bean.getChangePrice() > 0 ? getResources().getColor(R.color.community_red) : getResources().getColor(R.color.community_green));
                binding.tvChangeNumber.setTextColor(getResources().getColor(R.color.community_green));
                binding.tvPrice.setTextColor(getResources().getColor(R.color.community_green));
                binding.tvJj.setText(bean.getYesyPrice() + "");
                binding.tvZd.setText(bean.getLowPrice() + "");
                binding.tvJk.setText(bean.getOpenPrice() + "");
                binding.tvZs.setText(bean.getLastPrice() + "");
                binding.tvZg.setText(bean.getHighPrice() + "");

            }
        });
    }

    @Override
    protected QuotationViewModel getViewModel() {
        return ViewModelProviders.of(this).get(QuotationViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.community_activity_quotation;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    private ChartDataSourceHelper mHelper;
    private KMasterChartView mKLineChartView;
    private KSubChartView mVolumeView;
    private MarketFigureChart mMarketFigureChart;
    private ProgressBar mProgressBar;

    private int MAX_COLUMNS = 160;
    private int MIN_COLUMNS = 20;
    private KSubChartView mMacdView;

    private void initK() {
        mProgressBar = findViewById(R.id.progress_circular);

        // 行情图容器
        mMarketFigureChart = findViewById(R.id.chart_container);

        // 行情图主图（蜡烛线）
        mKLineChartView = new KMasterChartView(this);
        mMarketFigureChart.addChildChart(mKLineChartView, 200);

        // 行情图附图（成交量）
        mVolumeView = new KSubChartView(this);
        mMarketFigureChart.addChildChart(mVolumeView, 100);

        // MACD
        mMacdView = new KSubChartView(this);
        mMarketFigureChart.addChildChart(mMacdView, 100);

        // 容器的手势监听
        mMarketFigureChart.setPressChangeListener(this);
        initialData("pingan.json");

    }

    private void initialData(String s) {
        mProgressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData(s);
            }
        }, 500);
    }



    /**
     * 解析行情图数据
     */
    public void initData(String json) {
        // 士兰微k线数据
        String kJson = LocalUtils.getFromAssets(this, json);

        KLineParser parser = new KLineParser(kJson);
        parser.parseKlineData();

        if (mHelper == null) {
            mHelper = new ChartDataSourceHelper(this);
        }
        mProgressBar.setVisibility(View.GONE);
        mHelper.initKDrawData(parser.klineList, mKLineChartView, mVolumeView, mMacdView);
    }

    /**
     * 对主图和附图进行数据填充
     */
    @Override
    public void onReady(List<KLineToDrawItem> data, ExtremeValue extremeValue,
                        SubChartData subChartData) {
        mKLineChartView.initData(data, extremeValue,subChartData);
        mVolumeView.initData(data, extremeValue, TechParamType.VOLUME,subChartData);
        mMacdView.initData(data, extremeValue, TechParamType.MACD,subChartData);
    }

    /**
     * 主图的横向滑动
     */
    @Override
    public void onChartTranslate(MotionEvent me, float dX) {
        if (mHelper != null) {
            mHelper.initKMoveDrawData(dX, ChartDataSourceHelper.SourceType.MOVE);
        }
    }

    /**
     * 主图的手势fling
     */
    @Override
    public void onChartFling(float distanceX) {
        if (mHelper != null) {
            mHelper.initKMoveDrawData(distanceX, ChartDataSourceHelper.SourceType.FLING);
        }
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        ChartDataSourceHelper.K_D_COLUMNS = (int) (ChartDataSourceHelper.K_D_COLUMNS / scaleX);
        ChartDataSourceHelper.K_D_COLUMNS =
                Math.max(MIN_COLUMNS, Math.min(MAX_COLUMNS, ChartDataSourceHelper.K_D_COLUMNS));
        if (mHelper != null) {
            mHelper.initKMoveDrawData(0, ChartDataSourceHelper.SourceType.SCALE);
        }
    }
}
