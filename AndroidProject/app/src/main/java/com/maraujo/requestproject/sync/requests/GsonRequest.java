package com.maraujo.requestproject.sync.requests;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonSyntaxException;
import com.maraujo.requestproject.app.App;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mateus Emanuel Araújo on 23/10/15.
 * teusemanuel@gmail.com
 */
public class GsonRequest<T> extends Request<T> {

    private static final String TAG = GsonRequest.class.getSimpleName();
    protected static final int DEFAULT_TIMEOUT_MS = (int) (0.5 * 60 * 1000);// min * seg * ms = mil
    protected static final int DEFAULT_RETRIES = 2;

    private final Type type;
    private final Response.Listener<T> listener;
    private final Object body;
    private final Map<String, String> params;

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, Object> params, Object body, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.type = clazz;
        this.params = getParams(params);
        this.body = body;
        this.listener = listener;

        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public GsonRequest(int method, String url, Type clazz, Map<String, Object> params, Object body, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.type = clazz;
        this.params = getParams(params);
        this.body = body;
        this.listener = listener;

        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private Map<String, String> getParams(Map<String, Object> params) {

        if (params != null) {
            Map<String, String> strParams = new HashMap<>();
            for(Map.Entry<String, Object> entry : params.entrySet()) {
                strParams.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            return strParams;
        }

        return null;
    }

    @Override
    public String getUrl() {
        String url = super.getUrl();

        if (getMethod() != Method.GET && getMethod() != Method.DELETE) {
            Log.d(TAG, String.format("[%s] %s", getMethodString(), url));

            return url;
        }

        try {
            Map<String, String> params = getParams();

            if (params == null) {
                Log.d(TAG, String.format("[%s] %s", getMethodString(), url));

                return url;
            }

            StringBuilder builder = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (builder.length() > 0) {
                    builder.append("&");
                }

                try {
                    builder.append(String.format("%s=%s", entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    builder.append(String.format("%s=%s", entry.getKey(), entry.getValue().replace(" ", "+")));
                }
            }

            if (builder.length() > 0) {
                url = String.format("%s?%s", url, builder.toString());
            }
        } catch (AuthFailureError ex) {
            Log.e(TAG, ex.getLocalizedMessage(), ex);
        }

        Log.d(TAG, String.format("[%s] %s", getMethodString(), url));

        return url;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = this.params != null ? this.params : super.getParams();

        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = getDefaultHeaders();

        return headers;
    }

    @Override
    protected void deliverResponse(T response) {
        if (response != null) {
            Log.d(TAG, response.toString());
        }

        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            //App.getInstance().checkSessionCookie(response.headers); // Check session cookie
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            T parseObject = App.getInstance().getGson().fromJson(json, this.type);
            return Response.success(parseObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return body != null ? App.getInstance().getGson().toJson(body).getBytes() : super.getBody();
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    private Map<String, String> getDefaultHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", getBodyContentType());
        headers.put("Accept", "application/json");

        //TODO add your headers here

        return headers;
    }

    private String getMethodString() {
        switch (getMethod()) {
            case Request.Method.GET:
                return "GET";
            case Request.Method.POST:
                return "POST";
            case Request.Method.PUT:
                return "PUT";
            case Request.Method.DELETE:
                return "DELETE";
            default:
                return "";
        }
    }
}
