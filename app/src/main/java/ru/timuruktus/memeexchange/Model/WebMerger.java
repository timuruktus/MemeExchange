package ru.timuruktus.memeexchange.Model;

import android.util.Pair;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WebMerger implements IWebMerger{


    @Override
    public Observable<Object> mergeTwoRequests(Observable<Object> firstObservable, Observable<Object> secondObservable){
        return null;
//                Observable.zip(firstObservable,
//                secondObservable)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());;
    }
}
