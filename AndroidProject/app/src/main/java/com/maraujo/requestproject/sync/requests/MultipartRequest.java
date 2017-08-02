
package com.maraujo.requestproject.sync.requests;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.maraujo.requestproject.sync.requests.toolbox.MultipartBody;
import com.maraujo.requestproject.sync.requests.toolbox.MultipartBodyOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Class Used to upload files from the device to the server.
 * 
 * @author Mateus Emanuel Araújo
 *
 * @param <T>
 */
public class MultipartRequest<T> extends GsonRequest<T> {

	private static final int DEFAULT_TIMEOUT_MS = 60 * 2 * 1000;

	private final MultipartBody body;

	public MultipartRequest(int method, String url, Type clazz, Map<String, Object> params, MultipartBody body, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, clazz, params, null, listener, errorListener);

		this.body = body;

		setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}

	@Override
	public String getBodyContentType() {
		return body.getContentType();
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			MultipartBodyOutputStream.build(body).writeTo(bos);
		} catch (IOException e) {
			Log.e("MultipartRequest", "IOException writing to ByteArrayOutputStream bos, building the multipart request.");
		}

		return bos.toByteArray();
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders();

		headers.put("mimeType", body.getMimeType());

		return headers;
	}
}
