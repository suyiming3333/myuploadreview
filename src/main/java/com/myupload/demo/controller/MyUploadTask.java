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
public class MyUploadTask implements Runnable {

    private HttpServletRequest request;

    public MyUploadTask(HttpServletRequest request){
        this.request = request;
    }
    @Override
    public void run() {
        try {
            Collection<Part> collection = request.getParts();
            for (Iterator<Part> iterator = collection.iterator(); iterator.hasNext(); ) {
                Part part = iterator.next();
                Long totalSize = part.getSize();
                System.out.println(part.getName());
                System.out.println(part.getSize());
                System.out.println(part.getSubmittedFileName());
                String filename = part.getSubmittedFileName();
                InputStream inputStream = part.getInputStream();

                File saved = new File("D:\\uplaod", filename);
                saved.getParentFile().mkdirs();  //保证路径存在

                OutputStream outputStream = new FileOutputStream(saved);

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

                outputStream.close();
                inputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
