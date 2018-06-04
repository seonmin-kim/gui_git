package kr.co.mystockhero.geotogong.data;

import kr.co.mystockhero.geotogong.common.CommonUtil;

public class SmartscorePurchaseData extends JsonData {

    public int id;
    public String google_product_id;
    public String product;
    public int period;
    public float price;
    public float vat;
    public float total;

    public SmartscorePurchaseData() {
        super();
    }

    @Override
    public void init() {

    }

}
