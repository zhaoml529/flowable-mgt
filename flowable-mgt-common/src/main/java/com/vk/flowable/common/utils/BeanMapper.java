package com.vk.flowable.common.utils;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * bean转换
 */
public class BeanMapper {

    private static Mapper dozer = DozerBeanMapperSingletonWrapper.getInstance();

    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }


    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }
}