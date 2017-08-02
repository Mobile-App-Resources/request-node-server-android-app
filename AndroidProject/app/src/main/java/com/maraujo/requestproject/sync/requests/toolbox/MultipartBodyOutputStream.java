package com.maraujo.requestproject.sync.requests.toolbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

import com.maraujo.requestproject.sync.requests.toolbox.MultipartBody.*;

/**
 * Created by Mateus Emanuel Araújo on 27/01/16.
 * teusemanuel@gmail.com
 */
public class MultipartBodyOutputStream {

    private final MultipartBody body;

    private MultipartBodyOutputStream(MultipartBody body) {
        this.body = body;
    }

    public static MultipartBodyOutputStream build(MultipartBody body) {

        return new MultipartBodyOutputStream(body);
    }

    public void writeTo(final ByteArrayOutputStream out) throws IOException {

        DataOutputStream dos = new DataOutputStream(out);

        Set<Entry<String, DataPart>> filesEntries = body.getParts().entrySet();
        for (Entry<String, DataPart> entry : filesEntries) {
            String fileMultipartName = entry.getKey();
            DataPart value = entry.getValue();

            buildPart(dos, fileMultipartName, value);
        }

        Set<Entry<String, TextPart>> textEntries = body.getTextParts().entrySet();
        for (Entry<String, TextPart> entry : textEntries) {
            String textMultipartName = entry.getKey();
            TextPart value = entry.getValue();

            buildTextPart(dos, textMultipartName, value);

        }

        dos.writeBytes(body.getHyphens() + body.getBoundary() + body.getHyphens() + body.getEndLine());

    }


    private void buildTextPart(DataOutputStream dataOutputStream, String textMultipartName, TextPart part) throws IOException {

        dataOutputStream.writeBytes(body.getHyphens() + body.getBoundary() + body.getEndLine());

        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + textMultipartName + "\"" + body.getEndLine());
        dataOutputStream.writeBytes("Content-Type: " + part.contentType + "; charset=" + body.getCharset() + body.getEndLine());
        dataOutputStream.writeBytes(body.getEndLine());
        dataOutputStream.writeUTF(part.parameterValue);
        dataOutputStream.writeBytes(body.getEndLine());
    }

    private void buildPart(DataOutputStream dataOutputStream, String fileMultipartName, DataPart part) throws IOException {

        dataOutputStream.writeBytes(body.getHyphens() + body.getBoundary() + body.getEndLine());

        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + fileMultipartName + "\"; filename=\"" + part.fileName + "\"" + body.getEndLine());
        dataOutputStream.writeBytes("Content-Type: " + part.contentType + "; charset=" + body.getCharset() + body.getEndLine());
        dataOutputStream.writeBytes(body.getEndLine());

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(part.data);

        MultiPartProgressItem progress = part.progress;

        int bytesAvailable = fileInputStream.available();
        long total = 0;
        int fileLength = part.data.length;

        int maxBufferSize = 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // read file and write it into form...
        int bytesRead;

        while ((bytesRead = fileInputStream.read(buffer, 0, bufferSize)) > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);

            total += bytesRead;
            int transferredPercent = (int) (100.0f * total / fileLength) ;

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);

            if(progress != null) {
                progress.updateProgress(fileMultipartName, bytesRead, total, bytesAvailable, transferredPercent);
            }

        }

        dataOutputStream.writeBytes(body.getEndLine());
    }

}
