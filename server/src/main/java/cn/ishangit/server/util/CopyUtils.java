package cn.ishangit.server.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyUtils {

    public static <T> List<T> copyList(List source, Class<T>clazz){
        List<T> target = new ArrayList<>();
        if(!CollectionUtils.isEmpty(source)){
            for (Object obj:source) {
                T objs = copy(obj,clazz);
                target.add(objs);
            }
        }
        return target;
    }

    public static <T>T copy(Object source,Class<T> tClass){
        if(source==null){
            return null;
        }
        T obj = null;
        try {
            obj = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source,obj);
        return obj;
    }
}