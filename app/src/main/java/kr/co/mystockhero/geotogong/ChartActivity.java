package kr.co.mystockhero.geotogong;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.FontUtil;
import kr.co.mystockhero.geotogong.common.layout.LinearLayout;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.common.widget.Button;
import kr.co.mystockhero.geotogong.common.widget.TextView;

/**
 * Created by sesang on 16. 5. 22..
 */
public class ChartActivity extends CommonActivity {

    @Override
    protected void makeLayout() {

        bodyLayout = rootLayout.makeBody();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                makeChart();

            }
        },1000);


    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_activity);
//
//
//    }

    private void makeChart() {

        RelativeLayout layout = RelativeLayout.createLayout(null, this, bodyLayout,
                RelativeLayout.createScaledHeightLayoutParams(Gravity.FILL, 20, 40, 20, 42, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LineChart lineChart = new LineChart(this);
        layout.addView(lineChart);
        lineChart.setLayoutParams(RelativeLayout.createScaledHeightLayoutParams(Gravity.FILL, 20, 40, 20, 42, ViewGroup.LayoutParams.MATCH_PARENT, 300));

        // creating list of entry
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June11111");


        LineDataSet dataset = new LineDataSet(entries, "# of Calls");
        LineData data = new LineData(labels, dataset);
        lineChart.setData(data); // set the data and list of lables into chart
        lineChart.setDescription("Description");

        lineChart.animateY(5000);

    }

}
