package kr.co.mystockhero.geotogong;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import kr.co.mystockhero.geotogong.common.layout.LinearLayout;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.common.widget.Button;
import kr.co.mystockhero.geotogong.common.widget.ImageView;
import kr.co.mystockhero.geotogong.login.LoginActivity;

/**
 * Created by sesang on 16. 5. 22..
 */
public class GuideActivity extends CommonActivity {

    @Override
    protected void makeLayout() {

        bodyLayout = rootLayout.makeBody(0xff000000);

        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(new GuidePagerAdapter(this));
        bodyLayout.addView(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setPageControl(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        LinearLayout pageLayout = LinearLayout.createLayout("page_control", this, bodyLayout,
                RelativeLayout.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 165, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                android.widget.LinearLayout.HORIZONTAL);

        int page = 0;

        for( int i=0; i<4; i++ ) {

            int pageId = getResources().getIdentifier((page == i ) ? "guidepage_on" : "guidepage_off", "drawable", getPackageName());

            ImageView.createImageView("page_control_" + i, this, pageLayout,
                    LinearLayout.createScaledLayoutParams(Gravity.CENTER_HORIZONTAL, 10, 0, 10, 0, new Point(30,30)),
                    pageId);
        }
    }

    private void setPageControl(int page) {

        LinearLayout pageLayout = (LinearLayout)bodyLayout.findViewWithTag("page_control");

        for( int i=0; i<4; i++ ) {

            int pageId = getResources().getIdentifier((page == i) ? "guidepage_on" : "guidepage_off", "drawable", getPackageName());

            ImageView pageView = (ImageView) bodyLayout.findViewWithTag("page_control_" + i);
            pageView.setImageResource(pageId);

        }

//        if(page != 4){
//
//            for( int i=0; i<5; i++ ) {
//
//                int pageId = getResources().getIdentifier((page == i) ? "guidepage_on" : "guidepage_off", "drawable", getPackageName());
//
//                ImageView pageView = (ImageView) bodyLayout.findViewWithTag("page_control_" + i);
//                pageView.setImageResource(pageId);
//
//            }
//
//
//        }else{
//
//            for( int i=0; i<5; i++ ) {
//
//                ImageView pageView = (ImageView) bodyLayout.findViewWithTag("page_control_" + i);
//                pageView.setImageResource(-1);
//
//            }
//
//        }

    }


    public ViewGroup makeGuideView(Context context, int page) {

        RelativeLayout layout = RelativeLayout.createLayout(null, context, null,
                RelativeLayout.createLayoutParams(Gravity.FILL, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT), 0xffffffff);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;



        int resId = getResources().getIdentifier("clanding_0" + page + "_bg", "drawable", getPackageName());

        ImageView imageView = ImageView.createImageView(null, this, layout,
                RelativeLayout.createLayoutParams(Gravity.FILL, 0, -40, 0, -1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT),
                resId);
//        imageView.setAdjustViewBounds(true);
//        LinearLayout pageLayout = LinearLayout.createLayout(null, this, layout,
//                layout.createLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 237, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),
//                android.widget.LinearLayout.HORIZONTAL);
//
//        for( int i=0; i<4; i++ ) {
//
//            int pageId = getResources().getIdentifier((page == i ) ? "common_on" : "common_off", "drawable", getPackageName());
//
//            ImageView.createImageView(null, this, pageLayout,
//                    pageLayout.createScaledLayoutParams(Gravity.CENTER_HORIZONTAL, 9, 0, 9, 0, ControlUtil.getImageSize(context, pageId)),
//                    pageId);
//        }
        if ( page == 3 ) {

            LinearLayout layout2 = LinearLayout.createLayout(null, this, layout,
                    RelativeLayout.createScaledHeightLayoutParams(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0, 0, 50, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                    android.widget.LinearLayout.HORIZONTAL);


            Button.createButton("btn_entrance", this, layout2,
                    LinearLayout.createLayoutParams(Gravity.CENTER_HORIZONTAL, 0, 0, 0, 0, 580, 90),
                    R.drawable.startgeotogong, R.drawable.startgeotogong, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        }
                    });

//            Button.createButton("btn_entrance", this, layout2,
//                    LinearLayout.createLayoutParams(Gravity.CENTER_HORIZONTAL, 8, 0, 0, 0, 288, 96),
//                    R.drawable.btn_entrance_intro, R.drawable.btn_entrance_intro, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                        InvestmentApplication.access_token = null;
//                        Intent intent = new Intent(GuideActivity.this, MainTabActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        }
//                    });
        }

        if ( page == 0 ) {
            layout.setBackgroundColor(Color.RED);
        }

        return layout;
    }

    public class GuidePagerAdapter extends PagerAdapter {

        private Context context;

        public GuidePagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
//            CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//            ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);

            ViewGroup layout = makeGuideView(context, position);

            collection.addView(layout);

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }



        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {

//            CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
//            return mContext.getString(customPagerEnum.getTitleResId());
            return "";
        }

    }
}
