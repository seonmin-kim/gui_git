package kr.co.mystockhero.geotogong;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by Administrator on 2016-10-05.
 */
public class MyValueFormatter implements ValueFormatter {
    @Override
    public String  getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler){
        return String.format("%.1f", value);
    }
}
