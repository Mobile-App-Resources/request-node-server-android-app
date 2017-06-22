package com.maraujo.requestproject.sync.requests.toolbox;

/**
 * Created by Mateus Emanuel Araújo on 29/02/16.
 * teusemanuel@gmail.com
 */
public interface MultiPartProgressItem {

    void updateProgress(String fileKey, long readBytes, long contentLength, long bytesAvailable, int transferredPercent);
}
