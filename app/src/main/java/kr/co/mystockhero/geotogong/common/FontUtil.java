package kr.co.mystockhero.geotogong.common;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sesang on 16. 5. 20..
 */
public class FontUtil {

    public static final String Font_NotoSansKR = "NotoSansKR";

    public static final String Type_Normal = "";
    public static final String Type_Bold = "Bold";
    public static final String Type_Light = "Light";
    public static final String Type_Medium = "Medium";
    public static final String Type_Regular = "Regular";


    private static Map<String, Typeface> fontList = new HashMap<String, Typeface>();

    public static void addFont(Context context, String name, String kinds) {

        String fontName = name;
        if ( !Type_Normal.equals(kinds) ) fontName += "-" + kinds;

        if ( fontList.containsKey(fontName) ) return;

        // CommonUtil.DebugLog("addFont : " + fontName);

        try {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName + ".otf");
            fontList.put(fontName, typeface);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Typeface getFont(String name, String kinds) {

        String fontName = name;
        if ( !Type_Normal.equals(kinds) ) fontName += "-" + kinds;

        if ( fontList.containsKey(fontName) ) return fontList.get(fontName);
        return null;
    }
}
