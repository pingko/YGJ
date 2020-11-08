package com.guannan.simulateddata.parser;

import android.text.TextUtils;

import com.guannan.simulateddata.entity.KTimeItem;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import java.util.ArrayList;

/**
 * @author guannan
 * @date on 2020-03-14 16:32
 * @des k线数据解析
 */
public class KTimeParser {

  /**
   * k线原始数据
   */
  private String mKlineJson;

  /**
   * k线列表
   */
  public ArrayList<KTimeItem> klineList = new ArrayList<>();

  public KTimeParser(String klineJson) {
    this.mKlineJson = klineJson;
  }

  /**
   * 解析K线数据
   */
  public void parseKlineData() {
    Object obj = JSONValue.parse(mKlineJson);
    if (obj instanceof JSONArray) {
      JSONArray jsonArray = (JSONArray) obj;
      if (jsonArray != null && jsonArray.size() > 0) {
        for (int i = 0; i < jsonArray.size(); i++) {
          JSONObject jsonObject = (JSONObject) jsonArray.get(i);
          KTimeItem KTimeItem = getKTimeItem(jsonObject);
          klineList.add(KTimeItem);
        }
      }
    }
  }

  /**
   * 获取每一个交易日数据
   */
  public KTimeItem getKTimeItem(JSONObject obj) {
    KTimeItem KTimeItem = new KTimeItem();
    KTimeItem.uptime = obj.getAsString("uptime");
    String open = obj.getAsString("openPrice");
    if (!TextUtils.isEmpty(open)) {
      KTimeItem.openPrice = Float.parseFloat(open);
    }
    String high = obj.getAsString("highPrice");
    if (!TextUtils.isEmpty(high)) {
      KTimeItem.highPrice = Float.parseFloat(high);
    }
    String low = obj.getAsString("lowPrice");
    if (!TextUtils.isEmpty(low)) {
      KTimeItem.lowPrice = Float.parseFloat(low);
    }
    String close = obj.getAsString("yesyPrice");
    if (!TextUtils.isEmpty(close)) {
      KTimeItem.yesyPrice = Float.parseFloat(close);
    }
    String volume = obj.getAsString("volume");
    if (!TextUtils.isEmpty(volume)) {
      KTimeItem.volume = Long.parseLong(volume);
    }
    return KTimeItem;
  }


}
