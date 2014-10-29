package com.mindpin;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;

public class Image4ye
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    private static final String FORMAT_URL_CROP = "%s@%dw_%dh_1e_1c.png";
    private static final String FORMAT_URL = "%s@%dw_%dh.png";
    public String url;

    public Image4ye(String url) {
        this.url = url;
    }

    public String url(int width, int height, boolean crop) {
        if (crop)
            return String.format(FORMAT_URL_CROP, url, width, height);
        else
            return String.format(FORMAT_URL, url, width, height);
    }

    public static Image4ye upload(File image_file) {
        if (image_file == null) {
            System.out.println("not image_file cancel upload");
            throw new NullPointerException("image_file is null");
        }
        return HttpApi.upload(image_file);
    }

    public File download(int width, int height, boolean crop) throws IOException {
        if (url == null || url.equals("")) {
            System.out.println("not url cancel download");
            throw new NullPointerException("url is null");
        }
        String url = url(width, height, crop);
        return HttpApi.download(url);
    }

    private static class HttpApi {
        public static final String URL_UPLOAD = "http://img.4ye.me/api/upload";
        private static final String TMP_PATH_DIR = "/image4ye/cache";

        public static Image4ye upload(File image_file) {
            HttpRequest request = HttpRequest.post(URL_UPLOAD);
            request.part("file", image_file.getAbsolutePath(), image_file);
            if (request.ok()) {
                String body = request.body();
                return new Gson().fromJson(body, Image4ye.class);
            } else
                return null;
        }

        public static File download(String url) throws IOException {
            File output = File.createTempFile("image4ye", "download");
            HttpRequest.get(url).receive(output);
            return output;
        }
    }
}
