package com.example.shop.util;

import java.lang.reflect.Field;

/**
 * 对象合并，用于修改使用
 */
public class ModelMerge {

    /**
     * 合并模型，利用引用传递进行操作，不需用返回任何信息
     *
     * @param a 用于合并的模型
     * @param b 用于被提取的模型
     */
    public static void modelMergeByModel(Object a, Object b) {
        Class model1 = a.getClass();
        Class model2 = b.getClass();
        Field[] fields1 = model1.getDeclaredFields();
        Field[] fields2 = model2.getDeclaredFields();
        for (int i = 0; i < fields1.length; i++) {
            Field field1 = fields1[i];
            Field field2 = fields2[i];
            field1.setAccessible(true);
            field2.setAccessible(true);
            //  && !field1.get(a).equals(field2.get(b))
            try {
                if (field2.get(b) != null) {
                    if (!field1.get(a).equals(field2.get(b))) {
                        field1.set(a, field2.get(b));
                        System.out.println(field1.get(a) + "=a=," + field2.get(b) + "=b=");
                    }
                }
            } catch (IllegalAccessException e) {
                System.out.println("在合并类中转换出错了=======" + e.getMessage());
            }

        }
    }
}
