package kr.co.mystockhero.geotogong.data;

import org.json.JSONObject;

/**
 * Created by sesang on 16. 6. 8..
 */
public class RankingData extends JsonData {

    public int id;
    public int rank;
    public String name;
    public double ratio;

    public String regDate;
    public String endDate;
    // public int rise;
    // public int prev_rank;
    public String photo;
    public String img;

    public String ranking_photo;
    public String ranking_img;

    public String stock;
    public String code;
    public String masters;

    public int master_id;
    public String Compliance;

    public RankingData() {
        super();
    }

    public RankingData(String data) {
        super(data);
    }

    public RankingData(JSONObject json) {
        super(json);
    }

    @Override
    public void init() {

        super.init();

        if ( photo != null ) {
            img = photo.replace(".png", "").replace("master", "r_masters");
        }

        if(ranking_photo != null ) {
            ranking_img = ranking_photo.replace(".png", "").replace("master", "ranking_masters");
        }
        if ( master_id == 0 ) {
            master_id = 1;
        }
    }
}
