package com.maraujo.requestproject.sync.requests.toolbox;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * The representation of a MultiPart parameter
 */
public final class MultipartBody {

    private final String hyphens = "--";
    private final String endLine = "\r\n";


    private final String boundary;
    private final String mimeType;
    private final String charset;
    private final Map<String, DataPart> parts;
    private final Map<String, TextPart> textParts;
    private final String contentType;

    /**
     * Initialize a multipart request param with the value and content type
     * @param boundary The content type of the param
     * @param mimeType The value of the param
     * @param charset of params
     * @param contentType of request
     */
    private MultipartBody(String boundary, String mimeType, String charset, String contentType) {
        this.boundary = boundary;
        this.mimeType = mimeType;
        this.charset = charset;
        this.contentType = contentType;
        this.parts = new HashMap<>();
        this.textParts = new HashMap<>();
    }

    public static MultipartBody create() {
        final String boundary = "apiclient-" + System.currentTimeMillis();
        final String mimeType = "multipart/form-data;boundary=" + boundary;
        return new MultipartBody(boundary, mimeType, "UTF-8", null);
    }

    public static MultipartBody create(String charset) {
        final String boundary = "apiclient-" + System.currentTimeMillis();
        final String mimeType = "multipart/form-data;boundary=" + boundary;
        return new MultipartBody(boundary, mimeType, charset, null);
    }

    public static MultipartBody create(String boundary, String mimeType, String charset) {
        return new MultipartBody(boundary, mimeType, charset, null);
    }

    public static MultipartBody create(String boundary, String mimeType, String charset, String contentType) {
        return new MultipartBody(boundary, mimeType, charset, contentType);
    }

    /**
     * Add a image to be uploaded in the multipart request
     *
     * @param fileParameterName Key that references the file in multipart.
     * @param fileName Name of the file sent to the server.
     * @param imageFile The FileImage to uploaded. This file MUST exist.
     * @return The MultipartBody request for chaining method calls
     */
    public MultipartBody addImage(String fileParameterName, String fileName, File imageFile) throws IOException {

        this.addImage(fileParameterName, fileName, imageFile, null);
        return this;
    }

    /**
     * Add a image to be uploaded in the multipart request
     *
     * @param fileParameterName Key that references the file in multipart.
     * @param fileName Name of the file sent to the server.
     * @param imageFile The FileImage to uploaded. This file MUST exist.
     * @param progress The Progress Listener from upload this image.
     * @return The MultipartBody request for chaining method calls
     */
    public MultipartBody addImage(String fileParameterName, String fileName, File imageFile, MultiPartProgressItem progress) throws IOException {

        this.addFile(fileParameterName, fileName, "image/jpeg", imageFile, progress);
        return this;
    }

    /**
     * Add a file to be uploaded in the multipart request
     *
     * @param fileParameterName Key that references the file in multipart.
     * @param fileName Name of the file sent to the server.
     * @param contentType Optional. If you know the content type of the file. If it is <code>null</code>, the value will try to be found automatically for you.
     * @param file The file to uploaded. This file MUST exist.
     * @return The MultipartBody request for chaining method calls
     */
    public MultipartBody addFile(String fileParameterName, String fileName, String contentType, File file) throws IOException {

        this.addFile(fileParameterName, fileName, contentType, file, null);
        return this;
    }

    /**
     * Add a file to be uploaded in the multipart request
     *
     * @param fileParameterName Key that references the file in multipart.
     * @param fileName Name of the file sent to the server.
     * @param contentType Optional. If you know the content type of the file. If it is <code>null</code>, the value will try to be found automatically for you.
     * @param file The file to uploaded. This file MUST exist.
     * @param progress The Progress Listener from upload this file.
     * @return The MultipartBody request for chaining method calls
     */
    public MultipartBody addFile(String fileParameterName, String fileName, String contentType, File file, MultiPartProgressItem progress) throws IOException {

        FileInputStream inputStream = new FileInputStream(file);

        if(contentType == null) {
            contentType = URLConnection.guessContentTypeFromName(file.getPath());
        }

        this.addFile(fileParameterName, fileName, contentType, inputStream, progress);
        return this;
    }

    /**
     * Add a fileStream to be uploaded in the multipart request
     *
     * @param fileParameterName Key that references the file in multipart.
     * @param fileName Name of the file sent to the server.
     * @param contentType Optional. If you know the content type of the file. If it is <code>null</code>, the value will try to be found automatically for you.
     * @param inputStream The InputStream file to uploaded. This file MUST exist.
     * @return The MultipartBody request for chaining method calls
     */
    public MultipartBody addFile(String fileParameterName, String fileName, String contentType, InputStream inputStream) throws IOException {


       this.addFile(fileParameterName, fileName, contentType, inputStream, null);
        return this;
    }

    /**
     * Add a file to be uploaded in the multipart request
     *
     * @param fileParameterName Key that references the file in multipart.
     * @param fileName Name of the file sent to the server.
     * @param contentType Optional. If you know the content type of the file. If it is <code>null</code>, the value will try to be found automatically for you.
     * @param inputStream The InputStream file to uploaded. This file MUST exist.
     * @param progress The Progress Listener from upload this file.
     * @return The MultipartBody request for chaining method calls
     */
    public MultipartBody addFile(String fileParameterName, String fileName, String contentType, InputStream inputStream, MultiPartProgressItem progress) throws IOException {

        if(contentType == null) {
            contentType = URLConnection.guessContentTypeFromStream(inputStream);
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] byteArray = buffer.toByteArray();

        this.addFileByteArray(fileParameterName, fileName, contentType, byteArray, progress);

        return this;
    }

    /**
     * Add a file to be uploaded in the multipart request
     *
     * @param fileParameterName Key that references the file in multipart.
     * @param fileName Name of the file sent to the server.
     * @param contentType Optional. If you know the content type of the file. If it is <code>null</code>, the value will try to be found automatically for you.
     * @param array The array of bytes file to uploaded. This file MUST exist.
     * @return The MultipartBody request for chaining method calls
     */
    public MultipartBody addFileByteArray(String fileParameterName, String fileName, String contentType,  byte[] array) {

        this.addFileByteArray(fileParameterName, fileName, contentType, array, null);
        return this;
    }

    /**
     * Add a file to be uploaded in the multipart request
     *
     * @param fileParameterName Key that references the file in multipart.
     * @param fileName Name of the file sent to the server.
     * @param array The array of bytes file to uploaded. This file MUST exist.
     * @param contentType Optional. If you know the content type of the file. If it is <code>null</code>, the value will try to be found automatically for you.
     * @param progress The Progress Listener from upload this file.
     * @return The MultipartBody request for chaining method calls
     */
    public MultipartBody addFileByteArray(String fileParameterName, String fileName, String contentType, byte[] array, MultiPartProgressItem progress) {

        parts.put(fileParameterName != null ? fileParameterName : "uploaded_file", new DataPart(contentType, array, fileName, progress));
        return this;
    }

    /**
     * Add a text to be uploaded in the multipart request
     *
     * @param parameterName The name of the file key
     * @param parameterValue The path to the file. This file MUST exist.
     * @return The MultipartBody request for chaining method calls
     */
    public MultipartBody addText(String parameterName, String parameterValue) {

        textParts.put(parameterName, new TextPart(parameterValue));
        return this;
    }

	public  static final class DataPart {

		final String contentType;
		final byte[] data;
        final String fileName;
        final MultiPartProgressItem progress;

		public DataPart(String contentType, byte[] data, String fileName, MultiPartProgressItem progress) {
			this.contentType = contentType;
			this.data = data;
            this.progress = progress;
            this.fileName = fileName;
        }
	}

	public  static final class TextPart {

		final String contentType;
		final String parameterValue;

		public TextPart(String parameterValue) {
			this.contentType = "text/plain";
			this.parameterValue = parameterValue;
		}
	}

	public String getHyphens() {
		return hyphens;
	}

	public String getEndLine() {
		return endLine;
	}

	public String getBoundary() {
		return boundary;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getCharset() {
		return charset;
	}

	public Map<String, DataPart> getParts() {
		return parts;
	}

	public Map<String, TextPart> getTextParts() {
		return textParts;
	}

    public String getContentType() {
        return contentType != null ? contentType : this.generateContentType(boundary,  Charset.forName(charset)) ;
    }

    private String generateContentType(
            final String boundary,
            final Charset charset) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("multipart/form-data; ");

        if (charset != null) {
            buffer.append("; charset=");
            buffer.append(charset.name());
            buffer.append("; ");
        }

        buffer.append("boundary=");
        buffer.append(boundary);
        return buffer.toString();
    }
}
