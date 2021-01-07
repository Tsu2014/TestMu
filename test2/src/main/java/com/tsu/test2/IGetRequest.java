package com.tsu.test2;

import io.reactivex.Observable;

public interface IGetRequest {

    Observable<Translation> getCall();
}
