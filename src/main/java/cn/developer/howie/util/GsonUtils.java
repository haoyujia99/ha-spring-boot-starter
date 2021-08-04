package cn.developer.howie.util;

import com.google.gson.Gson;

/**
 * cn.developer.howie.util.GsonUtils.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 10:46 AM
 */
public class GsonUtils {

    private static final Gson GSON = new Gson();

    private GsonUtils() throws InstantiationException {
        throw new InstantiationException("Prohibited From Instantiating GsonUtils.class");
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

}