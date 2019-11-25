package org.sang.utils;

/**
 * Created by caiping on 2019/11/15.
 */
public interface HasDefaultInterface<T> extends HasValueInterface {
    T getDefault();
}
