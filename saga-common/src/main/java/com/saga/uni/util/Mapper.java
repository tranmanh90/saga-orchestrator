package com.saga.uni.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper {

    private static ModelMapper modelMapper;

    public static <T> T mapper(Object sourceData, Class<T> destinationClassType) {
        return getInstance().map(sourceData, destinationClassType);
    }

    public static <T> T mapper(Object sourceData, T targetData) {
        getInstance().map(sourceData, targetData);
        return targetData;
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream()
                .map(e -> getInstance().map(e, targetClass))
                .collect(Collectors.toList());
    }

    private static ModelMapper getInstance(){
        if(modelMapper == null){
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
            modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
        }

        return modelMapper;
    }

}
