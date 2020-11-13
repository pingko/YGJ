package com.yzg.community.recommend;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vinsonguo.klinelib.chart.ChartInfoView;
import com.vinsonguo.klinelib.chart.KLineChartInfoView;
import com.vinsonguo.klinelib.model.HisData;
import com.vinsonguo.klinelib.util.DateUtils;
import com.yzg.base.activity.MvvmBaseActivity;
import com.yzg.base.model.MarkettBean;
import com.yzg.common.router.RouterActivityPath;
import com.yzg.community.R;
import com.yzg.community.databinding.CommunityActivityQuotationBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * 行情图页面
 */
@Route(path = RouterActivityPath.Quotation.Quotation_main_chart)
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

        viewModel.laodMoreData(1, 200, bean.getVariety());

//        viewModel.marketBeans.observe(this, new Observer<List<MarkettBean>>() {
//            @Override
//            public void onChanged(List<MarkettBean> markettBeans) {
////                initialData(markettBeans);
//            }
//        });
        //        mKLineView = v.findViewById(R.id.kline);
////        RadioGroup rgIndex = v.findViewById(R.id.rg_index);
//        mKLineView.setDateFormat("yyyy-MM-dd");


//        mTimeLineView = new TimeLineView(this);  //初始化分时图
//        mTimeLineView.setDateFormat("HH:mm");  // 设置x轴时间的格式
//        List<HisData> hisData =  ...  // 初始化数据，一般通过网络获取数据
//        mTimeLineView.setLastClose(hisData.get(0).getClose());  // 设置昨收价
//        mTimeLineView.initData(hisData);  // 初始化图表数据

        viewModel.chartDatas.observe(this, s -> {
            initLineData(s);
        });

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


//    public void showVolume() {
//
//        binding.kline.post(() -> binding.kline.showVolume());
//    }
//    public HisData(double open, double close, double high, double low,  long vol, long date)

    protected void initLineData(JSONArray array) {
//        binding.kline.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        binding.kline.setDateFormat("yyyy-MM-dd");
        binding.kline.setDateFormat("MM-dd HH:mm");


        new Thread(() -> {
            List<HisData> hisData = getList(array);
            new Handler(getMainLooper()).post(() -> {
                binding.kline.initData(hisData);
                binding.kline.setLimitLine();
                binding.kline.showVolume();
//               showVolume();
            });
        }).start();


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


    public static List<HisData> getList(JSONArray array) {

//        List<MarkettBean> markettBeans = JSONArray.parseArray(s,MarkettBean.class);
//
//        List<KModel> list = JSONArray.parseArray(s,KModel.class);
        int count=0;
        List<HisData> hisData = new ArrayList<>(1000);
        for (int i = array.size() - 1; i >= 0; i--) {
            JSONObject jsonObject = array.getJSONObject(i);
            String date = jsonObject.getString("uptime");
            HisData data = new HisData();
            data.setClose(jsonObject.getDoubleValue("lastPrice"));
            data.setOpen(jsonObject.getDoubleValue("openPrice"));
            data.setHigh(jsonObject.getDoubleValue("highPrice"));
            data.setLow(jsonObject.getDoubleValue("lowPrice"));
            data.setVol(jsonObject.getIntValue("volume"));
            try {
                if (i > 1) {
                    String date1 = array.getJSONObject(i - 1).getString("uptime");
                    if (date1.equals(date)) {
                        count++;
                        continue;
                    }
                }
                data.setDate(sFormat2.parse(date).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            hisData.add(data);
        }
        return hisData;
    }
    private static SimpleDateFormat sFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
}
