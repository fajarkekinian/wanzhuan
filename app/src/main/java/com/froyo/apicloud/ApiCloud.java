package com.froyo.apicloud;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Administrator on 2015/12/2.
 */
public class ApiCloud {
    private String appId;
    private String appKey;
    private Retrofit retrofit;

    public ApiCloud(String appId, String appKey)
    {
        this.appId = appId;
        this.appKey = appKey;

        retrofit = new Retrofit.Builder()
                .baseUrl("http://d.apicloud.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public String[] getHeader()
    {
        String[] header = {"",""};
        header[0]= this.appId;
        String hashKey = "";
        String now = System.currentTimeMillis()+"";
        hashKey = this.appId+"UZ"+this.appKey+"UZ"+now;
        try {
            hashKey =SHA1(hashKey)+"."+now;
            header[1]= hashKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return header;
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }


    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
