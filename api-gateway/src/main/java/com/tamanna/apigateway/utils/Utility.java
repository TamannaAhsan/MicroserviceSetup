package com.tamanna.apigateway.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utility {
    public static void copyNonListAndIdAndCreatedDateProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getListPropertyNames(src));
    }

    public static boolean isNotNull(Object object) {
        return object != null;
    }

    private static String[] getListPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            if (pd.getPropertyType().isAssignableFrom(List.class))
                emptyNames.add(pd.getName());
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        emptyNames.addAll(Arrays.asList("id", "createdDate", "createdBy"));
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
