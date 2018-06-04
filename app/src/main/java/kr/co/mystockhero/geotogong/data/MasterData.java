package kr.co.mystockhero.geotogong.data;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sesang on 16. 5. 20..
 */
public class MasterData extends JsonData {

    public int id;
    public String name;
    public int is_subscribed;
    public int is_reserved;


    public String photo;
    public String photo_r;
    public String slogan;
    public String highlight;
    public String history;
    public String strategy;
    public String formula;
    public float earnings_rate;
    public float volatility;
    public float invest_growth;
    public float invest_valuation;
    public int difficulty;
    public String nickname;
    public String birth;
    public String invest_point;
    public String invest_index;
    public int pricetype;
    public int termtype;
    public int invest_type;
    public String recent_earnings_rate;
    public String topofmonthstockname;
    public String topofmonthstockratio;

    public  String masterpremium_compliance;
    public  String masterstrategy_compliance;
    public  String masterrecommandstock_compliance;

    public String img_photo;
    public String img_round;
    public String img_thumbnail;

//    public int price;
    public int is_opened = 1;

    public boolean detail = false;

    public int is_checkInvestmentTastes = 0;
    public int id_checkInvestmentTastes = 0;
    public String name_checkInvestmentTastes = "-";

    public ArrayList<Object> priceList = null;

    public MasterData() {
        super();
    }

    public MasterData(String data) {
        super(data);
    }

    public MasterData(JSONObject json) {
        super(json);
    }

    @Override
    public void init() {

        super.init();

//        price = 5000;

        img_photo = String.format("masters_%02d", id);
        img_round = String.format("masters1_%02d", id);
        img_thumbnail = String.format("masters_%02d", id);

    }

    public int getMasterId() {
        return id;
    }

    public PriceData getPriceData(int product_id) {

        if ( priceList == null ) return null;

        for( int i=0; i<priceList.size(); i++ ) {
            PriceData price = (PriceData)priceList.get(i);
            if ( price.id == product_id ) return price;
        }
        return null;
    }
}
