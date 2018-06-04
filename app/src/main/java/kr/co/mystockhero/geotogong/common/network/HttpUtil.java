package kr.co.mystockhero.geotogong.common.network;

/**
 * Created by sesang on 16. 5. 22..
 */

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import kr.co.mystockhero.geotogong.InvestmentApplication;


public class HttpUtil {

    private static String serverUrl = "";

    private static HttpUtil instance = null;
    private static int connectionTimeoutMillis = 5000;
    private static int readTimeoutMillis = 5000;
//    private final HttpClient httpClient;


    private ArrayList<AsyncNetworkTask> taskList = new ArrayList<AsyncNetworkTask>();

    public static HttpUtil getInstance() {
        if ( instance == null ) {
            instance = new HttpUtil();
        }
        return instance;
    }

    public HttpUtil()
    {
        serverUrl = InvestmentApplication.server_url;
//        httpClient=new DefaultHttpClient();
//        httpClient.getParams().setParameter("http.socket.timeout", readTimeoutMillis);
//        httpClient.getParams().setParameter("http.connection.timeout", connectionTimeoutMillis);
//        httpClient.getParams().setParameter("http.useragent", "android");
    }

//    public DefaultHttpClient getHttpClient()
//    {
//        return (DefaultHttpClient) httpClient;
//    }



    public String makeParam(JSONObject params) {

//        JSONObject json = new JSONObject();
//
//        try {
//            json.put("status", 0);
//            json.put("postdata", params);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return json.toString();


        try {
            if (InvestmentApplication.access_token != null) {
                params.put("access_token", InvestmentApplication.access_token);
            } else {
//                params.put("access_token", "3978e40329b4ff1650f9ecffd20793a75782a583c8cff8.52114786");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String data = "status=0&";
        data += "postdata=" + params.toString();

        return data;
    }

    public String post(String address, JSONObject postdata) throws Exception, OutOfMemoryError {

        URL url = parseURL(address);

        String params = makeParam(postdata);

        // CommonUtil.DebugLog("HttpUtil post : url [%s]\n", address + ", " + params);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setReadTimeout(readTimeoutMillis);
        conn.setConnectTimeout(connectionTimeoutMillis);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(params.getBytes().length));

//        conn.setUseCaches(false);
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "text/plain");
//        conn.setRequestProperty("Content-Length",
//                Integer.toString(params.length()));

//        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("POST");
        //conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//        conn.setDoInput(false);

//        conn.connect();

//        PrintWriter postReq = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
//        postReq.write(params);
//        postReq.flush();
        OutputStream os = conn.getOutputStream();
        os.write(params.getBytes());
        os.flush();

//        DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
//        dataOutputStream.writeBytes(URLEncoder.encode(params.toString(),"UTF-8"));
//        dataOutputStream.flush();
//        dataOutputStream.close();

        System.out.println("Response Code: " + conn.getResponseCode());

        String result = "";

        InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
        try {
            BufferedReader buffer = new BufferedReader(inputStreamReader);
            String line = null;
            result = buffer.readLine();

            while((line = buffer.readLine()) != null) {
                result += line;
            }
            buffer.close();
            // CommonUtil.DebugLog("HttpUtil post : response [%s]\n", result);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStreamReader != null) {
                inputStreamReader.close();
            }
        }
        return result;


//        CommonUtil.DebugLog("HttpUtil get : url [%s]\n", url.getPath());
//
//        DefaultHttpClient httpClient = getHttpClient();
//
//        List<Cookie> mCookies = null;
//        mCookies = httpClient.getCookieStore().getCookies();
//
//        CookieStore cookieStore = new BasicCookieStore();
//
//        for(Cookie cook : mCookies){
//            cookieStore.addCookie(cook);
//        }
//
//        httpClient.setCookieStore(cookieStore);
//        HttpPost post = new HttpPost(url.getPath());
//        //post.setHeader("User-Agent", "android");
//
//        HttpResponse response = httpClient.execute(post);
//        String result = "";
//
//        if ( response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ) {
//            HttpEntity entity = response.getEntity();
//            if(entity != null) {
//                InputStreamReader inputStreamReader = new InputStreamReader(entity.getContent(), "euc-kr");
//                try {
//                    BufferedReader buffer = new BufferedReader(inputStreamReader);
//                    String line = null;
//                    result = buffer.readLine();
//                    while((line = buffer.readLine()) != null) {
//                        result += line;
//                    }
//                    buffer.close();
//                    CommonUtil.DebugLog("post " + result);
//
//                } catch(Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if(inputStreamReader != null) {
//                        inputStreamReader.close();
//                    }
//                    entity.consumeContent();
//                }
//            }
//        }
//        return result;
    }

    public String get(String address) throws Exception {

        URL url = parseURL(address);

        // CommonUtil.DebugLog("HttpUtil get : url [%s]\n", address);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        System.out.println("Response Code: " + conn.getResponseCode());

        String result = "";

        InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
        try {
            BufferedReader buffer = new BufferedReader(inputStreamReader);
            String line = null;
            result = buffer.readLine();

            while((line = buffer.readLine()) != null) {
                result += line;
            }
            buffer.close();
            // CommonUtil.DebugLog("HttpUtil get : response [%s]\n", result);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStreamReader != null) {
                inputStreamReader.close();
            }
        }
        return result;

    }

    public URL parseURL(String address) throws Exception {

        if ( !address.startsWith("http") ) {
            address = serverUrl + address;
        }
        // CommonUtil.DebugLog("parseURL : " + address);
        URL url = new URL(address);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        url = uri.toURL();

        return url;

    }
}

