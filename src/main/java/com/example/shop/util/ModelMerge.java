package com.example.shop.util;

import java.lang.reflect.Field;

/**
 * 对象合并，用于修改使用
 */
public class ModelMerge {

    public static void modelMergeByModel(Object a, Object b) {
        Class model1 = a.getClass();
        Class model2 = b.getClass();
        Field[] fields1 = model1.getDeclaredFields();
        Field[] fields2 = model2.getDeclaredFields();
        for (int i = 0; i < fields1.length; i++) {
            
        }
    }
}
