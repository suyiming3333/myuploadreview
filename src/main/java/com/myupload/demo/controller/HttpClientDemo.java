package com.myupload.demo.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * @author suyiming3333@gmail.com
 * @version V1.0
 * @Title: HttpClientDemo
 * @Package com.huishibao
 * @Description: TODO
 * @date 2019/7/10 18:19
 */
public class HttpClientDemo {

    public static void main(String[] args) throws IOException {
        addFace();
    }


    public static void addFace() throws IOException {
        CloseableHttpClient client =null;
        String httpUrl="http://119.23.174.153:8082/tf/regist";
        HttpPost httpPost = new HttpPost(httpUrl);

        httpPost.setHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1NjM4NTAyNDQsInN1YiI6ImtldGVuZyIsImNyZWF0ZWQiOjE1NjMyNDU0NDQ1NDJ9.IQJGvaicTLV5HP9rc5bVBEYtGmN0T4wv-JYwY_plwAYc1PGHBlIET4N1haHyG-ZONKZ2qz2DoWhwMqkyeNZg7g");
        String requestId = "keng323212";
        String customerId = "10010";
        String requestType = "add_face";
        String owner = "123456";
        String ownerType = "1";
        String responseUrl = "123456";
        String featureData = "keteng";

        StringBody requestIdBody = new StringBody(requestId,ContentType.APPLICATION_JSON);
        StringBody customerIdBody = new StringBody(customerId,ContentType.APPLICATION_JSON);
        StringBody requestTypeBody = new StringBody(requestType,ContentType.APPLICATION_JSON);
        StringBody ownerBody = new StringBody(owner,ContentType.APPLICATION_JSON);
        StringBody ownerTypeBody = new StringBody(ownerType,ContentType.APPLICATION_JSON);
        StringBody responseUrlBody = new StringBody(responseUrl,ContentType.APPLICATION_JSON);

        FileInputStream inputStream = new FileInputStream(new File("D:\\face2.jpeg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
        byte[] tmp = new byte[1024];
        int len = -1;
        Long currentSize = 0L;
        while ((len = inputStream.read(tmp)) != -1) {
            /**将文件内容写到bos**/
            bos.write(tmp, 0, len);

        }

        ByteArrayBody image = new ByteArrayBody(tmp, ContentType.APPLICATION_JSON,"image.jpg");

        MultipartEntityBuilder me = MultipartEntityBuilder.create();
        me.addPart("requestId",requestIdBody)
                .addPart("customerId",customerIdBody)
                .addPart("requestType",requestTypeBody)
                .addPart("owner",ownerBody)
                .addPart("ownerType",ownerTypeBody)
                .addPart("responseUrl",responseUrlBody)
                .addPart("featureData",image);

        client = HttpClients.createDefault();
        HttpEntity reqEntity = me.build();
        httpPost.setEntity(reqEntity);
        HttpResponse responseRes = null;
        try {
            responseRes=client.execute(httpPost);
            String token = responseRes.getHeaders("Authorization").toString();
            int status = responseRes.getStatusLine().getStatusCode();
            String resultStr =null;
            if (status == 200) {
                byte[] content;
                try {
                    content = getContent(responseRes);
                    resultStr = new String(content,"utf-8");
                    System.out.println("httpPost返回的结果==:"+resultStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            client.close();
        }
    }

    public static void doLogin() throws IOException {
        CloseableHttpClient client =null;
        String httpUrl="http://119.23.174.153:8082/sign-in";
        HttpPost httpPost = new HttpPost(httpUrl);


        String userName = "ket";
        String password = "123456";

        StringBody userNameBody = new StringBody(userName,ContentType.APPLICATION_JSON);
        StringBody passwordBody = new StringBody(password,ContentType.APPLICATION_JSON);
        MultipartEntityBuilder me = MultipartEntityBuilder.create();
        me.addPart("userName",userNameBody).addPart("password",passwordBody);


        client = HttpClients.createDefault();
        HttpEntity reqEntity = me.build();
        httpPost.setEntity(reqEntity);
        HttpResponse responseRes = null;
        try {
            responseRes=client.execute(httpPost);
            String token = responseRes.getHeaders("Authorization").toString();
            int status = responseRes.getStatusLine().getStatusCode();
            String resultStr =null;
            if (status == 200) {
                byte[] content;
                try {
                    content = getContent(responseRes);
                    resultStr = new String(content,"utf-8");
                    System.out.println("httpPost返回的结果==:"+resultStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            client.close();
        }


    }

    private static byte[] getContent(HttpResponse response)
            throws IOException {
        InputStream result = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = resEntity.getContent();
                byte[] tmp = new byte[1024];
                int len = 0;
                while ((len = result.read(tmp)) != -1) {
                    out.write(tmp, 0, len);;
                }
                return out.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("getContent异常", e);
        } finally {
            out.close();
            if (result != null) {
                result.close();
            }
        }
        return null;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n = 0;
        while(-1!=(n=input.read(buffer))){
            bos.write(buffer,0,n);
        }

        return bos.toByteArray();

    }


    public static String httpPost(String httpUrl,byte[] imagebyte) throws IOException {
        CloseableHttpClient client =null;
        httpUrl="http://119.23.174.153:8082/sign-in";
        HttpPost httpPost = new HttpPost(httpUrl);
//        ByteArrayBody image = new ByteArrayBody(imagebyte, ContentType.APPLICATION_JSON,"image.jpg");//传递图片的时候可以通过此处上传image.jpg随便给出即可

        String userName = "ket";
        String password = "123456";

        StringBody appidbody = new StringBody(userName,ContentType.APPLICATION_JSON);
        StringBody secretbody = new StringBody(password,ContentType.APPLICATION_JSON);
        MultipartEntityBuilder me = MultipartEntityBuilder.create();
//        me.addPart("image", image)//image参数为在服务端获取的key通过image这个参数可以获取到传递的字节流,这里不一定就是image,你的服务端使用什么这里就对应给出什么参数即可
//                .addPart("appid",appidbody )
//                .addPart("secret", secretbody);

//        DefaultHttpClient client= new DefaultHttpClient();
        client = HttpClients.createDefault();
        HttpEntity reqEntity = me.build();
        httpPost.setEntity(reqEntity);
        HttpResponse responseRes = null;
        try {
            responseRes=client.execute(httpPost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            client.close();
        }

        int status = responseRes.getStatusLine().getStatusCode();
        String resultStr =null;
        if (status == 200) {
            byte[] content;
            try {
                content = getContent(responseRes);
                resultStr = new String(content,"utf-8");
                System.out.println("httpPost返回的结果==:"+resultStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(resultStr !=null){
            return resultStr;
        }else{
            return "";
        }
    }
}
