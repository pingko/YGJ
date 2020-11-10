package com.yzg.community.recommend;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.vinsonguo.klinelib.chart.TimeLineView;
import com.vinsonguo.klinelib.model.HisData;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.model.MarkettBean;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.community.R;
import com.yzg.community.databinding.CommunityActivityQuotationBinding;
import com.yzg.community.klineview.Util;

import java.util.List;


/**
 * 行情图页面
 */
@Route(path = RouterActivityPath.Quotation.Quotation_main)
public class QuotationActivity extends MvvmBaseActivity<CommunityActivityQuotationBinding, QuotationViewModel> {
//public class QuotationActivity extends MvvmBaseActivity<CommunityActivityQuotationBinding, QuotationViewModel> implements IChartDataCountListener<List<KLineToDrawItem>>, IPressChangeListener {

    @Autowired(name = "MarkettBean")
    public MarkettBean bean;//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        Log.e("QuotationActivity", "MarkettBean: " + ((bean == null) ? true : false));
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
        } else {
            initData();
        }
        binding.ivBack.setOnClickListener(view -> {
            finish();
        });

        viewModel.laodMoreData(1, 2000, bean.getVariety());

        viewModel.marketBeans.observe(this, new Observer<List<MarkettBean>>() {
            @Override
            public void onChanged(List<MarkettBean> markettBeans) {
//                initialData(markettBeans);
            }
        });
        initView();
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

//    private ChartDataSourceHelper mHelper;
//    private KMasterChartView mKLineChartView;
//    private KSubChartView mVolumeView;
//    private MarketFigureChart mMarketFigureChart;
//    private ProgressBar mProgressBar;
//
//    private int MAX_COLUMNS = 160;
//    private int MIN_COLUMNS = 20;
//    private KSubChartView mMacdView;

//    private void initK() {
//        mProgressBar = findViewById(R.id.progress_circular);
//
//        // 行情图容器
//        mMarketFigureChart = findViewById(R.id.chart_container);
//
//        // 行情图主图（蜡烛线）
//        mKLineChartView = new KMasterChartView(this);
//        mMarketFigureChart.addChildChart(mKLineChartView, 200);
//
//        // 行情图附图（成交量）
//        mVolumeView = new KSubChartView(this);
//        mMarketFigureChart.addChildChart(mVolumeView, 100);
//
//        // MACD
//        mMacdView = new KSubChartView(this);
//        mMarketFigureChart.addChildChart(mMacdView, 100);
//
//        // 容器的手势监听
//        mMarketFigureChart.setPressChangeListener(this);
////        initialData("pingan.json");
//
//
//
//    }

//    private void initialData(List<MarkettBean> markettBeans) {
//        String s = null;
//        mProgressBar.setVisibility(View.VISIBLE);
//        new Handler().postDelayed(() -> initData(markettBeans, s), 500);
//    }


//    /**
//     * 解析行情图数据
//     */
//    public void initData(List<MarkettBean> markettBeans, String json) {
//        // 士兰微k线数据
////        String kJson = LocalUtils.getFromAssets(this, json);
//
//        String kJson = "";
//        List<KLineItem> lineItems = new ArrayList<>();
////        for (MarkettBean markettBean:markettBeans){
//
////        for (int i = markettBeans.size() - 1; i >= 0; i--) {
//        for (int i = 0; i <markettBeans.size() - 1; i++) {
//            MarkettBean markettBean = markettBeans.get(i);
//            if (i > 0 && markettBeans.get(i - 1).getUptime().equals(markettBean.getUptime())) {
//                continue;
//            }
//            KLineItem lineItem = new KLineItem();
//            lineItem.preClose = markettBean.getYesyPrice();
//            lineItem.day = markettBean.getUptime();
//            lineItem.high = (float) markettBean.getHighPrice();
//            lineItem.low = (float) markettBean.getLowPrice();
//            lineItem.open = markettBean.getOpenPrice();
//            lineItem.volume = markettBean.getVolume();
//            lineItem.close = (float) markettBean.getLastPrice();
//            lineItems.add(lineItem);
//        }
//        kJson = JSON.toJSONString(lineItems);
//        Log.e("ssssss", kJson);
//        KLineParser parser = new KLineParser(kJson);
//        parser.parseKlineData();
//
//        if (mHelper == null) {
//            mHelper = new ChartDataSourceHelper(this);
//        }
//        mProgressBar.setVisibility(View.GONE);
//        mHelper.initKDrawData(parser.klineList, mKLineChartView, mVolumeView, mMacdView);
//    }
//
//    /**
//     * 对主图和附图进行数据填充
//     */
//    @Override
//    public void onReady(List<KLineToDrawItem> data, ExtremeValue extremeValue,
//                        SubChartData subChartData) {
//        mKLineChartView.initData(data, extremeValue, subChartData);
//        mVolumeView.initData(data, extremeValue, TechParamType.VOLUME, subChartData);
//        mMacdView.initData(data, extremeValue, TechParamType.MACD, subChartData);
//    }
//
//    /**
//     * 主图的横向滑动
//     */
//    @Override
//    public void onChartTranslate(MotionEvent me, float dX) {
//        if (mHelper != null) {
//            mHelper.initKMoveDrawData(dX, ChartDataSourceHelper.SourceType.MOVE);
//        }
//    }
//
//    /**
//     * 主图的手势fling
//     */
//    @Override
//    public void onChartFling(float distanceX) {
//        if (mHelper != null) {
//            mHelper.initKMoveDrawData(distanceX, ChartDataSourceHelper.SourceType.FLING);
//        }
//    }
//
//    @Override
//    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
//        ChartDataSourceHelper.K_D_COLUMNS = (int) (ChartDataSourceHelper.K_D_COLUMNS / scaleX);
//        ChartDataSourceHelper.K_D_COLUMNS =
//                Math.max(MIN_COLUMNS, Math.min(MAX_COLUMNS, ChartDataSourceHelper.K_D_COLUMNS));
//        if (mHelper != null) {
//            mHelper.initKMoveDrawData(0, ChartDataSourceHelper.SourceType.SCALE);
//        }
//    }


//    KLineView  K线图控件
//    public void showKdj() 显示kdj指标
//    public void showMacd() 显示macd指标
//    public void showVolume() 显示交易量指标
//    public void initData(List<HisData> hisDatas) 初始化数据，获取到数据后调用
//    public void addData(HisData hisData) 图表末尾增加一个数据
//    public void refreshData(float price) 刷新最后一个点的价格（不增加数据）
//
//
//    TimeLineView 分时图控件
//    public void initData(List<HisData> hisDatas) 初始化数据，获取到数据后调用
//    public void addData(HisData hisData) 图表末尾增加一个数据
//    public void refreshData(float price) 刷新最后一个点的价格（不增加数据）
//    public void initDatas(List<HisData>... hisDatas) 初始化多日的数据，比如说5日的数据，就传5个list过去
//    public void setLastClose(double lastClose)  设置昨天的收盘价，用于计算涨跌幅的坐标
//
//    两个类共同的api：
//    public void setCount(int init, int max, int min) 设置图标的可见个数，分别是初始值，最大值，最小值。比如(100,300,50)就是开始的时候100个点，最小可以缩放到300个点，最大可以放大到50个点
//    public void setDateFormat(String format) 设置x轴时间的格式
//    public void setLastClose(douhle lastClose) 设置昨收价格，用于计算涨跌幅


    public void initView() {
//        mKLineView = v.findViewById(R.id.kline);
////        RadioGroup rgIndex = v.findViewById(R.id.rg_index);
//        mKLineView.setDateFormat("yyyy-MM-dd");


//        mTimeLineView = new TimeLineView(this);  //初始化分时图
//        mTimeLineView.setDateFormat("HH:mm");  // 设置x轴时间的格式
//        List<HisData> hisData =  ...  // 初始化数据，一般通过网络获取数据
//        mTimeLineView.setLastClose(hisData.get(0).getClose());  // 设置昨收价
//        mTimeLineView.initData(hisData);  // 初始化图表数据
        viewModel.laodMoreData(1, 2000, bean.getVariety());

        viewModel.marketBeans.observe(this, new Observer<List<MarkettBean>>() {
            @Override
            public void onChanged(List<MarkettBean> markettBeans) {
//                initialData(markettBeans);
            }
        });

        initLineData();

    }


    public void showVolume() {

        binding.kline.post(() -> binding.kline.showVolume());
    }
//    public HisData(double open, double close, double high, double low,  long vol, long date)

    protected void initLineData() {
//        binding.kline.setDateFormat("yyyy-MM-dd HH:mm:ss");
        binding.kline.setDateFormat("yyyy-MM-dd");
        List<HisData> hisData = Util.getK(this, 1);
        binding.kline.initData(hisData);
        binding.kline.setLimitLine();
        showVolume();

        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mKLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = (int) (Math.random() * hisData.size());
                        HisData data = hisData.get(index);
                        HisData lastData = hisData.get(hisData.size() - 1);
                        HisData newData = new HisData();
                        newData.setVol(data.getVol());
                        newData.setClose(data.getClose());
                        newData.setHigh(Math.max(data.getHigh(), lastData.getClose()));
                        newData.setLow(Math.min(data.getLow(), lastData.getClose()));
                        newData.setOpen(lastData.getClose());
                        newData.setDate(System.currentTimeMillis());
                        hisData.add(newData);
                        mKLineView.addData(newData);
                    }
                });
            }
        }, 1000, 1000);*/

       /* new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mKLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = (int) (Math.random() * 100);
                        HisData data = hisData.get(index);
                        mKLineView.refreshData((float) data.getClose());
                    }
                });
            }
        }, 1000, 1000);*/
    }

}
