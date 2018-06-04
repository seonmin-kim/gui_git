package kr.co.mystockhero.geotogong.common.network;

/**
 * Created by sesang on 16. 5. 22..
 */

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import static kr.co.mystockhero.geotogong.common.CommonUtil.errmsg;

public class AsyncNetworkTask extends AsyncTask<Object, Void, Object[]> {

    public interface NetworkTaskListener {

        void onError(int errorCode, String message);
        void onRespons(Object result) throws Exception;
    }

    private String type = "";

    public AsyncNetworkTask()	{


    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object[] result)	{

        super.onPostExecute(result);

        Integer errorCode = (Integer)result[0];
        String message = (String)result[1];

        if ( errorCode == 1 ) {
            if ( "get".equals(type) || "post".equals(type) ) {
                try {
                    JSONObject response = (JSONObject)result[2];
                    if ( Integer.parseInt(response.getString("result")) == 1 ) {
                        onRespons(response.has("data") ? response.getJSONObject("data") : null);
                        return;
                    } else {
                        errorCode = Integer.parseInt(response.getString("code"));

                        if ( response.has("message") ) {
                            message = response.getString("message");
                        }


                    }

                    //

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    errorCode = -1;
                    message = e.getMessage();
                } catch (JSONException e) {
                    e.printStackTrace();
                    errorCode = -1;
                    message = "JSONObject : " + e.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorCode = -1;
                    message = e.toString();
                }
            } else if ( "bitmap".equals(type) ) {

                try {
                    JSONObject response = (JSONObject)result[2];
                    if ( Integer.parseInt(response.getString("resultCode")) == 0 ) {
                        onRespons(response);
                        return;
                    } else {
                        errorCode = Integer.parseInt(response.getString("resultCode"));
                        if ( response.has("resultMsg") ) {
                            message = response.getString("resultMsg");
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    errorCode = -1;
                    message = e.getMessage();
                } catch (JSONException e) {
                    e.printStackTrace();
                    errorCode = -1;
                    message = "JSONObject : " + e.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorCode = -1;
                    message = e.getMessage();

                }
            }
        }else{
            message = errmsg;
        }

        onError(errorCode, message);
    }

    @Override
    protected Object[] doInBackground(Object... params) {

        if ( params.length > 1 ) {
            try {

                type = (String)params[0];
                String url = (String)params[1];
                JSONObject data = null;
                if ( params.length > 1 && params[2] != null ) {
                    data = (JSONObject)params[2];
                }

                if ( "get".equals(type) ) {

//                    String result = HttpUtil.getInstance().get(url);
//
//                    if (result.equals("1")) return new Object[]{1, "",new JSONObject("{\"resultCode\":\"0\",\"login\":\"success\",\"message\":\"로그인 성공\"}") };
//                    else if (result.equals("-1")) return new Object[]{1, "",new JSONObject("{\"resultCode\":\"0\",\"login\":\"fail\",\"message\":\"잘못된 아이디입니다.\"}") };
//                    else if (result.equals("-2")) return new Object[]{1, "",new JSONObject("{\"resultCode\":\"0\",\"login\":\"fail\",\"message\":\"잘못된 비밀번호입니다.\"}") };
//                    else if (result.equals("-3")) return new Object[]{1, "",new JSONObject("{\"resultCode\":\"0\",\"login\":\"fail\",\"message\":\"이미 다른 기기에서 로그인되었습니다.\"}") };
//                    else if (result.equals("login error")) return new Object[]{1, "",new JSONObject("{\"resultCode\":\"0\",\"login\":\"fail\",\"message\":\"로그인 후 이용하시기 바랍니다.\"}") };
//
//                    JSONObject jsonObject = new JSONObject(result);
//                    jsonObject.put("resultCode", "0");
//
//                    return new Object[]{1, "", jsonObject};

                } else if ( "post".equals(type) ) {
                    // CommonUtil.DebugLog(url + ", " + data);
                    String result = HttpUtil.getInstance().post(url, data);
                    return new Object[]{1, "", new JSONObject(result)};

                } else if ( "bitmap".equals(type) ) {

//                    int sampleSize = (Integer)params[2];
//                    Bitmap result = HttpUtil.getInstance().downloadImage(url, sampleSize);
//                    return new Object[]{1, "", result};
                }
            } catch(Exception e) {
                e.printStackTrace();
                return new Object[]{-1, e.getMessage(), null};
            }
        }
        return new Object[]{-1, "", null};
    }

    @Override
    protected void onCancelled() {

        super.onCancelled();
    }

    public boolean cancelConnection() {

        return this.cancel(true);
    }

    protected void onError(int errorCode, String message) {

    }

    protected void onRespons(Object result) throws Exception {

    }

}