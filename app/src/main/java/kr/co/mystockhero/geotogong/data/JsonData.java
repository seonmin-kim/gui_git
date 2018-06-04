package kr.co.mystockhero.geotogong.data;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by sesang on 16. 6. 2..
 */
public abstract class JsonData {

    public static JSONObject toJson(String data) {
        try {
            JSONObject json = new JSONObject(data);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonData() {

        init();
    }

    public JsonData(String data) {
        this(toJson(data));
    }

    public JsonData(JSONObject json) {

        if ( json == null ) return;

        for( Field field : getClass().getDeclaredFields() ) {

            if ( !json.has(field.getName()) ) continue;
            try {
                field.set(this, json.get(field.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        init();
    }

    public void init() {

    }

    public void update(JSONObject json) {

        if ( json == null ) return;

        for( Field field : getClass().getDeclaredFields() ) {

            if ( !json.has(field.getName()) ) continue;
            try {
                field.set(this, json.get(field.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {

        JSONObject json = new JSONObject();

        for (Field field : getClass().getDeclaredFields()) {
            try {
                json.put(field.getName(), field.get(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return json.toString();
    }
}
