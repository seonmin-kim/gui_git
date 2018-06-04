package kr.co.mystockhero.geotogong.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

import kr.co.mystockhero.geotogong.common.CommonUtil;
import kr.co.mystockhero.geotogong.common.ControlUtil;
import kr.co.mystockhero.geotogong.common.FontUtil;
import kr.co.mystockhero.geotogong.common.layout.RelativeLayout;
import kr.co.mystockhero.geotogong.R;

/**
 * Created by sesang on 16. 5. 30..
 */
public class ListView extends android.widget.ListView {

    public interface ListViewAdapter {

        View makeListView(ListView listView, int position, View convertView, Object data);
        void onListItemClick(ListView listView, AdapterView<?> parent, View view, int position, long id);
        View makeBlankView(ListView listView, View convertView);
    }

    private ListViewAdapter listViewAdapter;
    private ListAdapter listAdapter;

    public class ListAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<Object> objectList;

        public ListAdapter(Context context, ArrayList<Object> objectList) {

            this.context= context;
            this.objectList = objectList;
        }

        public void setData(ArrayList<Object> objectList) {

            this.objectList = objectList;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {

            if ( objectList == null ) return 0;
            return (objectList.size() > 0) ? objectList.size() : 1;

        }

        @Override
        public Object getItem(int position) {

            return objectList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if ( objectList == null || objectList.size() == 0 ) {
                if ( listViewAdapter != null ) {
                    return listViewAdapter.makeBlankView(ListView.this, convertView);
                }
            } else {
                if ( listViewAdapter != null ) {
                    return listViewAdapter.makeListView(ListView.this, position, convertView, objectList.get(position));
                }
            }
            return null;
        }
    }

    public ListView(Context context) {

        super(context);
    }

    public ListView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public void setListViewAdapter(ListViewAdapter listViewAdapter) {

        this.listViewAdapter = listViewAdapter;

        if ( listViewAdapter != null ) {
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if ( ListView.this.listViewAdapter != null ) {
                        ListView.this.listViewAdapter.onListItemClick(ListView.this, parent, view, position, id);
                    }
                }
            });
        }
    }

    public void setListAdapter(Context context, ArrayList<Object> listData) {

        if ( listAdapter == null ) {
            listAdapter = new ListAdapter(context, listData);
        }
        setAdapter(listAdapter);
    }

    public void updateData(Context context) {

        if ( listAdapter != null ) {
            listAdapter.notifyDataSetChanged();
        }
    }

    public void updateData(Context context, ArrayList<Object> listData) {

        if ( listAdapter == null ) {
            listAdapter = new ListAdapter(context, listData);
            setAdapter(listAdapter);
        }
        listAdapter.setData(listData);
        listAdapter.notifyDataSetChanged();
    }

    protected View makeBlankView(View convertView) {

        return makeBlankView(convertView, "데이터가 없습니다.");
    }

    public View makeBlankView(View convertView, String text) {

        convertView = new RelativeLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getHeight());
        convertView.setLayoutParams(layoutParams);
        convertView.setBackgroundColor(ControlUtil.getColor(getContext(), R.color.list_bg));

        TextView.createTextView(null, getContext(), (ViewGroup)convertView,
                RelativeLayout.createLayoutParams(Gravity.FILL, 0, 0, 0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT),
                text, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, -1, 32, 10, false, 0xff737373, FontUtil.getFont(FontUtil.Font_NotoSansKR, FontUtil.Type_Bold), false);


        return convertView;
    }

    public static ListView createList(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
                                      ListViewAdapter listViewAdapter, ArrayList<Object> listData, int bgColor, int dividerColor, int dividerHeight) {

        ListView listView = new ListView(context);

        listView.setId(ControlUtil.generateViewId());

        if ( tag != null ) {
            listView.setTag(tag);
        }
        listView.setLayoutParams(layoutParams);
        if ( parent != null ) {
            parent.addView(listView);
        }

        listView.setClipChildren(false);
        listView.setClipToPadding(false);
        listView.setDescendantFocusability(ScrollView.FOCUS_BLOCK_DESCENDANTS);
        listView.setSelector(new PaintDrawable(0x00000000));
        listView.setCacheColorHint(0x00000000);

        listView.setBackgroundColor(bgColor);

        listView.setDivider(new ColorDrawable(dividerColor));
        listView.setDividerHeight(dividerHeight);

        listView.setListViewAdapter(listViewAdapter);
        listView.setListAdapter(context, listData);

        return listView;
    }

    public static ListView createList(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
                                      ListViewAdapter listViewAdapter, ArrayList<Object> listData, int bgColor) {

        return createList(tag, context, parent, layoutParams, listViewAdapter, listData, bgColor, bgColor, 0);
    }

    public static ListView createList(Object tag, Context context, ViewGroup parent, ViewGroup.LayoutParams layoutParams,
                                      ListViewAdapter listViewAdapter, ArrayList<Object> listData) {

        return createList(tag, context, parent, layoutParams, listViewAdapter, listData, Color.BLACK, Color.TRANSPARENT, 0);
    }
}
