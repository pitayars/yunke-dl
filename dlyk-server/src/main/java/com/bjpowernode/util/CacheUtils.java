package com.bjpowernode.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 緩存通用工具类
 *
 */
public class CacheUtils {

    public static <T> T getCacheData(Supplier<T> cacheSelector, Supplier<T> dbSelector, Consumer<T> cacheSave) {
        T t = cacheSelector.get();
        if (Objects.isNull(t)) {
            T db = dbSelector.get();
            if (!Objects.isNull(db)) {
                cacheSave.accept(db);
            }
            return db;
        } else {
            return t;
        }
    }
}
