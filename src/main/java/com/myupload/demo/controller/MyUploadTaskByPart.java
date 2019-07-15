package com.myupload.demo.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author suyiming3333@gmail.com
 * @version V1.0
 * @Title: MyUploadTask
 * @Package com.myupload.demo.controller
 * @Description: TODO
 * @date 2019/7/13 11:54
 */
public class MyUploadTaskByPart implements Runnable {

    private Part part;

    private HttpServletRequest request;

    public MyUploadTaskByPart(Part part) {
        this.part = part;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        System.out.println("线程"+Thread.currentThread().getName()+"开始啦");
        try {
            Long totalSize = part.getSize();
            System.out.println(part.getName());
            System.out.println(part.getSize());
            System.out.println(part.getSubmittedFileName());
            String filename = part.getSubmittedFileName();
            inputStream = part.getInputStream();

            File saved = new File("D:\\uplaod", filename);
            saved.getParentFile().mkdirs();  //保证路径存在

            outputStream = new FileOutputStream(saved);

            byte[] tmp = new byte[1024];
            int len = -1;
            Long currentSize = 0L;
            while ((len = inputStream.read(tmp)) != -1) {
                System.out.println(len);
                currentSize += len;
                outputStream.write(tmp, 0, len);
                DecimalFormat df = new DecimalFormat("#.00");
                System.out.println("进度：" + df.format((currentSize / (double) totalSize) * 100) + "%");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
