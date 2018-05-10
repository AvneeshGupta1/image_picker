package com.nguyenhoanglam.imagepicker.listener;

import java.util.List;

public interface FileResultCallback<T> {
    void onResultCallback(List<T> files);
}