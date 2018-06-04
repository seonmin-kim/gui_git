package kr.co.mystockhero.geotogong.data;

import org.json.JSONObject;

import kr.co.mystockhero.geotogong.common.CommonUtil;

/**
 * Created by sesang on 16. 6. 2..
 */
public class PurchaseData extends JsonData {

    public int master_id;

    public String start_time;
    public String end_time;
    public String modified_time;
   public String created_time;;

    public String status;

    public String number;
    public double price;
    public double vat;
    public double period;
    public int total;
    public int invest_type;
    public String name;
    public String slogan;
    public String unit;

    public boolean selected;

    public PurchaseData() {
        super();
    }

    public PurchaseData(String data) {
        super(data);
    }
    public PurchaseData(JSONObject json) {
        super(json);
    }

    @Override
    public void init() {
        number = String.valueOf(master_id);
        selected = false;
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

//    @Override
//    public int getMasterId() {
//        return master_id;
//    }
}