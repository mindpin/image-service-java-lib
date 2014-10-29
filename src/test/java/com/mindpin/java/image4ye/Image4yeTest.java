package com.mindpin.java.image4ye;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Unit test for simple App.
 */
public class Image4yeTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Image4yeTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(Image4yeTest.class);
    }

    public String getTestImagePath() {
        URL url = getClass().getResource("/test.png");
        return url.getPath();
    }

    public String getUploadedUrl() {
        return "http://img.teamkn.com/i/hqJDrLhd.jpg";
    }

    public void testDownload() {
        System.out.println("test Download " + getUploadedUrl());
        Image4ye image4ye = new Image4ye(getUploadedUrl());
        File download_tmp_file = null;
        try {
            download_tmp_file = image4ye.download(100, 100, true);
            System.out.println("download_tmp_file.length():" + download_tmp_file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull("下载失败", download_tmp_file);
        assertNotSame(download_tmp_file.length(), 0);
    }

    public void testCrop() {
        System.out.println("testCrop image4ye = new Image4ye(" + getUploadedUrl() + ")");
        Image4ye image4ye = new Image4ye(getUploadedUrl());
        String url = image4ye.url(100, 100, false);
        String crop_url = image4ye.url(100, 100, true);
        System.out.println("image4ye.url(100, 100, false):" + url);
        System.out.println("image4ye.url(100, 100, true):" + crop_url);
        assertEquals(url.substring(url.length() - 14), "@100w_100h.png");
        assertEquals(crop_url.substring(crop_url.length() - 20), "@100w_100h_1e_1c.png");
    }

    public void testUpload() {
        System.out.println("upload " + getTestImagePath());
        File image_file = new File(getTestImagePath());
        Image4ye image4ye = Image4ye.upload(image_file);
        System.out.println("uploaded image4ye.url:" + image4ye.url);
        assertNotNull("upload failure", image4ye.url);
        System.out.println("upload success");
    }
}
