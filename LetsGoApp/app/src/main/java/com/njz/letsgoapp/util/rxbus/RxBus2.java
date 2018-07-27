package com.njz.letsgoapp.util.rxbus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by LGQ
 * Time: 2018/7/27
 * Function:
 */

public class RxBus2 {

    /*
    Disposable 是用来控制发送者和接受者之间的纽带的，在调用dispose()方法之后,阀门开启,会阻断发送者和接收者之间的通信
    Consumer 消费 Observer 的简化方法
    Subject：Subject是一个比较特殊的对象，既可充当发射源，也可充当接收源
     */

    /*
    Observable：在观察者模式中称为“被观察者”；
    Observer：观察者模式中的“观察者”，可接收Observable发送的数据；
    subscribe：订阅，观察者与被观察者，通过subscribe()方法进行订阅；
    Subscriber：也是一种观察者，在2.0中 它与Observer没什么实质的区别，
    不同的是 Subscriber要与Flowable(也是一种被观察者)联合使用，该部分内容是2.0新增的，
    Obsesrver用于订阅Observable，而Subscriber用于订阅Flowable
    背压 Backpressure(单个事件处理时间过长，导致接收多个事件通知)
     */

    /*
        操作符说明：https://www.jianshu.com/p/3fdd9ddb534b
        ofType  filter  过滤
        cast 在发射之前强制将Observable发射的所有数据转换为指定类型
     */


    private static RxBus2 defaultRxBus;
    private Subject<Object> bus;

    private RxBus2() {
        bus = PublishSubject.create().toSerialized();
    }

    public static RxBus2 getInstance() {
        if (null == defaultRxBus) {
            synchronized (RxBus2.class) {
                if (null == defaultRxBus) {
                    defaultRxBus = new RxBus2();
                }
            }
        }
        return defaultRxBus;
    }

    public void post(Object obj) {
        if (hasObservable()) {//判断当前是否已经添加订阅
            bus.onNext(obj);
        }
    }

    public boolean hasObservable() {
        return bus.hasObservers();
    }

    /*
     * 转换为特定类型的Obserbale
     */
    public <T> Disposable toObservable(Class<T> type, Consumer<T> consumer) {
        return bus.ofType(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    public void setDispose(Disposable dis){
        if (!dis.isDisposed()) {
            dis.dispose();
        }
    }
}