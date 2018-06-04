package kr.co.mystockhero.geotogong.data;

import org.json.JSONObject;

import kr.co.mystockhero.geotogong.common.CommonUtil;

/**
 * Created by sesang on 16. 7. 10..
 */
public class PriceData extends JsonData {

    public int id;
    public String product;
    public String product_id;
    public int discount;
    public int period;
    public int priceid;
    public int ori_price;
    public int discountrate;
    public double price;
    public double vat;
    public double total;

    public String unit;
    public String start_date;
    public String end_date;

    public PriceData() {
        super();
    }

    public PriceData(String data) {
        super(data);
    }
    public PriceData(JSONObject json) {
        super(json);
    }

    @Override
    public void init() {

    }

    public String getUnit() {
        return ( unit != null ) ? unit : "원";
    }

    public String currencyString(double value) {

        if ( "원".equals(getUnit()) ) {
            return CommonUtil.moneyFormat(value) + getUnit();
        } else {
            return getUnit() + CommonUtil.moneyFormat(value);
        }
    }
}
