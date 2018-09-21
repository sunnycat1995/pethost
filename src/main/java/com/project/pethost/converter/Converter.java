package com.project.pethost.converter;

public interface Converter<S, T> {
    T convert(S source);
}