package com.maraujo.requestproject.sync.listeners;

/**
 * Created by Mateus Emanuel Araújo on 23/10/15.
 * teusemanuel@gmail.com
 */
public interface AsyncTaskListener<T> {

    void onAsyncTaskFinish(T t);

    void onAsyncTaskException(Exception e);
}