package com.john.johndevframe.base;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
