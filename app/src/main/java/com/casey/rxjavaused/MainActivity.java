package com.casey.rxjavaused;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    private String tag = "TAG";
    /*
    1）RxJava 有四个基本概念：Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)、事件。
    Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。
    2）与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，
    还定义了两个特殊的事件：onCompleted() 和 onError()。
    3）onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。
    RxJava 规定，当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。
    4）onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。
    5）在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，
    并且是事件序列中的最后一个。需要注意的是，onCompleted() 和 onError() 二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个。
     */


    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            Log.e(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.e(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(tag, "Error!");
        }
    };
    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.e(tag, "subscriber Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.e(tag, "subscriber Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(tag, "subscriber Error!");
        }
    };
    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        observable.subscribe(observer);
        observable.subscribe(subscriber);
    }
}
