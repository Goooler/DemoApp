package io.goooler.demoapp.util;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.goooler.demoapp.base.BaseApplication;

/**
 * 读取 asset 目录下文件的工具
 */

public class AssetUtil {
    public static AssetManager getAssetsManager() {
        return BaseApplication.getContext().getAssets();
    }

    public static String readJsonFromAssets(String fileName) {
        String jsonString = null;
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    getAssetsManager().open(fileName)));
            while ((jsonString = reader.readLine()) != null) {
                builder.append(jsonString);
            }
            jsonString = builder.toString();
            reader.close();
        } catch (IOException e) {
            // do nothing
        }
        return jsonString;
    }
}
