package kz.dev.home.flos.SupportClases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class WebReq {
    private static final String TAG = "WebReq: ";
    private static AsyncHttpClient client;

    static{
        //create object of loopj client
        //443 will save you from ssl exception
        client = new AsyncHttpClient(true,80,443);
    }

    @SuppressLint("LongLogTag")
    static void get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        Log.d(TAG + "GET request params :", String.valueOf(params));
        client.get(context, getAbsoluteUrl(url), params, responseHandler);
    }

    @SuppressLint("LongLogTag")
    public static void post(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        Log.d(TAG + "POST request params:", String.valueOf(params));
        client.post(context, getAbsoluteUrl(url), params, responseHandler);
    }

    //concatenation of base url and file name
    @SuppressLint("LongLogTag")
    private static String getAbsoluteUrl(String relativeUrl) {
        Log.d(TAG +"response URL: ", GlobalClass.BASE_URL + relativeUrl+" ");
        return GlobalClass.BASE_URL + relativeUrl;
    }


}