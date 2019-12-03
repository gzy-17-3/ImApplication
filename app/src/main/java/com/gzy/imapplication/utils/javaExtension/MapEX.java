package com.gzy.imapplication.utils.javaExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXF on 2015/07/10.
 */

public class MapEX {

    public interface IEXFilter<K, V>{
        boolean filter(Map.Entry<K, V> entry);
    }
    public interface IEXCompose<K, V,T>{
        T compose(Map.Entry<K, V> entry);
    }

    public static <K,V,T> List<T> generate(Map<K,V> map, IEXCompose<K, V, T> compose){
        List<T> res = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            T obj = compose.compose(entry);
            if (obj != null){
                res.add(obj);
            }
        }
        return res;
    }
    public static <K, V> Map<K, V> filter(Map<K, V> map, IEXFilter<K, V> compose){
        Map<K, V> res = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (compose.filter(entry)){
                res.put(entry.getKey(),entry.getValue());
            }
        }
        return res;
    }
}
