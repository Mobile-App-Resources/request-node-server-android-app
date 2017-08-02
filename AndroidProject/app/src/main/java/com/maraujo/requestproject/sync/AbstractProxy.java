package com.maraujo.requestproject.sync;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.maraujo.requestproject.app.App;
import com.maraujo.requestproject.sync.listeners.ProxyListener;
import com.maraujo.requestproject.sync.requests.GsonRequest;
import com.maraujo.requestproject.sync.requests.MultipartRequest;
import com.maraujo.requestproject.sync.requests.toolbox.MultipartBody;
import com.maraujo.requestproject.sync.result.Outcome;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Mateus Emanuel Araújo on 04/12/15.
 * teusemanuel@gmail.com
 */
public abstract class AbstractProxy {
    private WeakReference<Activity> activityRef;
    private String tag;

    protected AbstractProxy(Activity activity) {
        this.activityRef = new WeakReference<>(activity);
    }

    public void destroy() {
        this.activityRef.clear();
    }

    public abstract String getTag();

    protected <T> void createAndScheduleGsonRequest(int method, String url, @NonNull Class clazz, Object body, Map<String, String> params, final ProxyListener proxyListener, View view) {
        Activity activity = activityRef.get();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    proxyListener.onStart();
                }
            });
        }

        App.getInstance().addToRequestQueue(this, view, new GsonRequest<>(method, url, clazz, params, body, new Response.Listener<Object>() {

            @Override
            public void onResponse(final Object response) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onSuccess(response);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onFailure(error);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }
            }
        }));
    }

    protected <T> void createAndScheduleGsonOutcomeRequest(int method, String url, @NonNull Class clazz, Object body, Map<String, String> params, final ProxyListener<Outcome<T>> proxyListener, View view) {
        Activity activity = activityRef.get();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    proxyListener.onStart();
                }
            });
        }

        Type clazzType = clazz.getGenericSuperclass();
        Type type = new GenericParametrizedType(Outcome.class, clazzType != null && clazzType != Object.class  ? clazz.getGenericSuperclass() : clazz);

        App.getInstance().addToRequestQueue(this, view, new GsonRequest<Outcome<T>>(method, url, type, params, body, new Response.Listener<Outcome<T>>() {

            @Override
            public void onResponse(final Outcome<T> response) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onSuccess(response);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onFailure(error);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }
            }
        }));
    }

    protected <T> void createAndScheduleGsonListRequest(int method, String url, @NonNull Class clazz, Object body, Map<String, String> params, final ProxyListener<ArrayList<T>> proxyListener, View view) {
        Activity activity = activityRef.get();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    proxyListener.onStart();
                }
            });
        }

        Type type = new GenericParametrizedType(ArrayList.class, clazz);
        App.getInstance().addToRequestQueue(this, view, new GsonRequest<ArrayList<T>>(method, url, type, params, body, new Response.Listener<ArrayList<T>>() {

            @Override
            public void onResponse(final ArrayList<T> response) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onSuccess(response);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onFailure(error);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }
            }
        }));
    }

    protected void createAndScheduleImageRequest(String url, final ProxyListener<Bitmap> proxyListener, View view) {
        Activity activity = activityRef.get();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    proxyListener.onStart();
                }
            });
        }

        App.getInstance().addToRequestQueue(this, view, new ImageRequest(url, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(final Bitmap response) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onSuccess(response);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }
            }
        },0,0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onFailure(error);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }
            }
        }));
    }

    protected <T> void createAndScheduleMultipartRequest(int method, String url, @NonNull Class<T> clazz, File file, Map<String, String> params, final ProxyListener<T> proxyListener, View view) {
        Activity activity = activityRef.get();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    proxyListener.onStart();
                }
            });
        }

        MultipartBody body = MultipartBody.create();
        try {
            body.addFile(file.getName(), file.getName(), null, file);
        } catch (IOException ex) {
            LogUtils.logError("IOException writing to FileInputStream stream, building the multipart request.", ex);
            proxyListener.onFailure(new VolleyError(ex));
        }

        App.getInstance().addToRequestQueue(this, view, new MultipartRequest<>(method, url, clazz, params, body, new Response.Listener<T>() {

            @Override
            public void onResponse(final T response) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onSuccess(response);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }

                App.getInstance().removeProxy(AbstractProxy.this);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onFailure(error);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }

                App.getInstance().removeProxy(AbstractProxy.this);
            }
        }));
    }

    protected <T> void createAndScheduleMultipartRequest(int method, String url, @NonNull Class<T> clazz, MultipartBody body, Map<String, String> params, final ProxyListener<T> proxyListener, View view) {
        Activity activity = activityRef.get();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    proxyListener.onStart();
                }
            });
        }

        App.getInstance().addToRequestQueue(this, view, new MultipartRequest<>(method, url, clazz, params, body, new Response.Listener<T>() {

            @Override
            public void onResponse(final T response) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onSuccess(response);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }

                App.getInstance().removeProxy(AbstractProxy.this);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Activity activity = activityRef.get();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                proxyListener.onFailure(error);
                                proxyListener.onComplete();
                            } catch (Exception e) {
                                LogUtils.logError("AbstractProxy", e);
                            }
                        }
                    });
                }

                App.getInstance().removeProxy(AbstractProxy.this);
            }
        }));
    }


    public class GenericParametrizedType<X> implements ParameterizedType {

        private final Class<X> container;
        private final Type[] wrapped;

        public GenericParametrizedType(Class<X> container, Type... wrapped) {
            this.container = container;
            this.wrapped = wrapped;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return wrapped;
        }

        @Override
        public Type getRawType() {
            return container;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }

    }

}
