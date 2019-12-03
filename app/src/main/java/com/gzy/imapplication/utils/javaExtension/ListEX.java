package com.gzy.imapplication.utils.javaExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXF on 2015/07/10.
 */

public class ListEX {
    // 获取第一个元素
    public static <T> T firstObj(List<T> list) {
        return safe(list, 0);
    }
    public static <T> void firstObj(List<T> list, IEXValue<T> iex) {
        T v = firstObj(list);
        if (v != null) {
            iex.complete(v);
        }
    }

    public static <T> void firstObj(List<T> list, IEXValue2<T> iex) {
        T v = firstObj(list);
        if (v != null) {
            iex.complete(v);
        } else {
            iex.noValue();
        }
    }

    public static <T> T lastObj(List<T> list) {
        if (list == null) {
            return null;
        }
        return safe(list, list.size() - 1);
    }

    public static <T> void lastObj(List<T> list, IEXValue<T> iex) {
        T v = lastObj(list);
        if (v != null) {
            iex.complete(v);
        }
    }

    public static <T> void lastObj(List<T> list, IEXValue2<T> iex) {
        T v = lastObj(list);
        if (v != null) {
            iex.complete(v);
        } else {
            iex.noValue();
        }
    }

    public static <T> T safe(List<T> list, int index) {
        if (list == null) {
            return null;
        }
        int size = list.size();
        if (size < 1) {
            return null;
        }
        if (size <= index) {
            return null;
        }
        return list.get(index);
    }

    public static <T> void safe(List<T> list, int index, IEXValue<T> iex) {
        T v = safe(list, index);
        if (v != null) {
            iex.complete(v);
        }
    }

    public static <T> void safe(List<T> list, int index, IEXValue2<T> iex) {
        T v = safe(list, index);
        if (v != null) {
            iex.complete(v);
        } else {
            iex.noValue();
        }
    }

    public static <T> List<T> filter(List<T> list, IEXValueFilter<T> filter) {
        List<T> res = new ArrayList<>();
        for (T obj : list) {
            if (filter.filter(obj)) {
                res.add(obj);
            }
        }
        return res;
    }

    public static <T, T2> List<T2> generate(List<T> list, IEXValueCompose<T, T2> compose) {
        List<T2> res = new ArrayList<>();
        for (T obj : list) {
            T2 resObj = compose.compose(obj);
            if (resObj != null) {
                res.add(resObj);
            }
        }
        return res;
    }

    public static String join(List<?> list, String separator) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        int offset = list.size() - 1;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < offset; i++) {
            sb.append(list.get(i));
            sb.append(separator);
        }
        sb.append(list.get(offset));
        return sb.toString();
    }

    public static <T> int count(List<T> list,IEXValueFilter<T> filter){
        int count = 0;
        for (T t : list) {
            if (filter.filter(t)){
                count ++;
            }
        }
        return count;
    }

}
