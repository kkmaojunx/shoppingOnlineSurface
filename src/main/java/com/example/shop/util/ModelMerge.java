package com.example.shop.util;

import com.example.shop.entity.User;

import java.lang.reflect.Field;
import java.util.Date;

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

    public static void main(String[] args) {
        User user = new User();

        User user1 = new User();
        user1.setId(1);
        user1.setHeadLocal("www.zhangdanling.cn");
        user1.setBirthday(new Date());
        user1.setObjectFlowIndent(1);

        User user2 = user1;

        mergeObject(user, user2);

        System.out.printf(user.toString());
    }


    public static <T> void mergeObject(T destination, T origin) {
        if (origin == null || destination == null)
            return;
        if(!origin.getClass().equals(destination.getClass()))
            return;

        Field[] fields =origin.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object value = fields[i].get(origin);
                if (null != value) {
                    fields[i].set(destination, value);
                }
                fields[i].setAccessible(false);
            }catch (Exception e) {
            }
        }
    }
}
