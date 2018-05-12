package com.example.shop.util;

import java.lang.reflect.Field;

/**
 * 对象合并，用于修改使用
 */
public class ModelMerge {

    /**
     * 合并模型，利用引用传递进行操作，不需用返回任何信息
     *
     * @param sourceBean 用于合并的模型
     * @param targetBean 用于被提取的模型
     */
    public static void modelMergeByModel(Object targetBean, Object sourceBean) {
        Class sourceBeanClass = sourceBean.getClass();
        Class targetBeanClass = targetBean.getClass();

        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = targetBeanClass.getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            Field targetField = targetFields[i];
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                if (!(sourceField.get(sourceBean) == null)) {
                    if (!sourceField.get(sourceBean).equals(targetField.get(targetBean)))
                        targetField.set(targetBean, sourceField.get(sourceBean));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
