package ru.timuruktus.memeexchange.Model;

import rx.Observable;

public interface IWebMerger{

    Observable<Object> mergeTwoRequests(Observable<Object> firstObservable,Observable<Object> secondObservable);
}
