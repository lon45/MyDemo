package com.example.mydemo.downLoad.pinterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wk
 * @des:
 * @date: 2018/10/18
 */
public interface Observerable extends OnProgressListener {
    public List<OnProgressListener> list = new ArrayList<>();

    public void registerObserver(OnProgressListener o);

    public void removeObserver(OnProgressListener o);

    public void notifyObserver();
}
