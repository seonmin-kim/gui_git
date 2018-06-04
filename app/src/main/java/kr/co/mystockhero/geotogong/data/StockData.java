package kr.co.mystockhero.geotogong.data;

import org.json.JSONObject;

/**
 * Created by sesang on 16. 5. 25..
 */
public class StockData extends JsonData {

    public String code;
    public String stock;
    public String section;
    public String stock_initial;
    public String search;


    public StockData() {
        super();
    }

    public StockData(String data) {
        super(data);
    }

    public StockData(JSONObject json) {
        super(json);
    }

    public StockData(String code, String stock) {

        this.code = code;
        this.stock = stock;
    }
}
