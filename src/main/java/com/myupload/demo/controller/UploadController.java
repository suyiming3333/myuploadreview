package com.myupload.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author suyiming3333@gmail.com
 * @version V1.0
 * @Title: UploadController
 * @Package com.myupload.demo.controller
 * @Description: TODO
 * @date 2019/7/12 23:46
 */

@Controller
public class UploadController {

    /**
     * form-data方式提交，通过part获取文件流
     *
     * @param request
     * @param response
     */
    @RequestMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        // 检测是否为上传请求
//        String contentType = request.getContentType();
        try {
            Collection<Part> collection = request.getParts();
//            Iterator<Part> iterator = collection.iterator();
//            if(iterator.hasNext()){
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
//        System.out.println(contentType);
        System.out.println("1111");
    }


    /**
     * response返回图片流
     * @param request
     * @param response
     */
    @RequestMapping("/getPhoto")
    public void upload2(HttpServletRequest request, HttpServletResponse response) {
        try {
//            InputStream inputStream = request.getInputStream();
//            System.out.println(request.getContentType());
//            System.out.println(inputStream.available());
//            response.setContentLength(inputStream.available());

            FileInputStream inputStream = new FileInputStream(new File("D:\\upload\\aaa.JPG"));

            response.setContentType("image/jpg");
            response.setContentLength(inputStream.available());
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] tmp = new byte[1024];
            int len = -1;
            Long currentSize = 0L;
            while ((len = inputStream.read(tmp)) != -1) {

                outputStream.write(tmp, 0, len);

            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件输入流转字节数组再输出
     * @param request
     * @param response
     */
    @RequestMapping("/getPhoto2")
    public void getPhoto2(HttpServletRequest request, HttpServletResponse response) {
        try {
            FileInputStream inputStream = new FileInputStream(new File("D:\\upload\\aaa.JPG"));
            /**创建字节数组类型os**/
            ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
            response.setContentType("image/jpg");
            response.setContentLength(inputStream.available());
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] tmp = new byte[1024];
            int len = -1;
            Long currentSize = 0L;
            while ((len = inputStream.read(tmp)) != -1) {
                /**将文件内容写到bos**/
                bos.write(tmp, 0, len);

            }
            outputStream.write(bos.toByteArray());
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * response 响应文件下载
     * @param request
     * @param response
     */
    @RequestMapping("/getFile")
    public void getFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            FileInputStream inputStream = new FileInputStream(new File("D:\\upload\\out.java"));
            /**创建字节数组类型os**/
            ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
            response.setContentType("image/jpg");
            response.setContentLength(inputStream.available());
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("out.java", "UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] tmp = new byte[1024];
            int len = -1;
            Long currentSize = 0L;
            while ((len = inputStream.read(tmp)) != -1) {
                /**将文件内容写到bos**/
                bos.write(tmp, 0, len);

            }
            outputStream.write(bos.toByteArray());
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * binary二进制流读取文件，并保存到本地
     *
     * @param request
     * @param response
     */
    @RequestMapping("/upload3")
    public void upload3(HttpServletRequest request, HttpServletResponse response) {
        try {
            InputStream inputStream = request.getInputStream();

            File saved = new File("D:\\uplaod", "e.png");
            saved.getParentFile().mkdirs();  //保证路径存在

            OutputStream outputStream = new FileOutputStream(saved);

            byte[] tmp = new byte[1024];
            int len = -1;
            Long currentSize = 0L;
            while ((len = inputStream.read(tmp)) != -1) {

                outputStream.write(tmp, 0, len);

            }

            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * springmvc MultipartFile 单文件上传
     *
     * @param multipartFile
     */
    @RequestMapping("/upload4")
    public void upload4(MultipartFile multipartFile) {
        try {
            if (!multipartFile.isEmpty()) {
                String path = "D:\\upload" + "\\" + multipartFile.getOriginalFilename();
                File file = new File(path);
                file.getParentFile().mkdirs();
                multipartFile.transferTo(file);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * springmvc multipartfile 多文件上传
     * @param multipartFiles
     */
    @RequestMapping("/upload5")
    public void upload5(MultipartFile[] multipartFiles) {
        try {
            String path = "D:\\upload" + "\\";
            if (multipartFiles != null && multipartFiles.length != 0) {
                if (null != multipartFiles && multipartFiles.length > 0) {
                    //遍历并保存文件
                    for (MultipartFile file : multipartFiles) {
                        file.transferTo(new File(path + file.getOriginalFilename()));
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @RequestMapping("/upload6")
    public void upload6(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("start");
        ThreadPool.getInstance().execute(new MyUploadTask(request));
        System.out.println("end");
    }
}
