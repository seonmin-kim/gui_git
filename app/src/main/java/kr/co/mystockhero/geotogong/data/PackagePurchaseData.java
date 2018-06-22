package kr.co.mystockhero.geotogong.data;

public class PackagePurchaseData extends JsonData {
    public String google_product_id;
    public String product;
    public int period;
    public float price;
    public float vat;
    public float total;

    public PackagePurchaseData() {
        super();
    }

    @Override
    public void init() {

    }
}
