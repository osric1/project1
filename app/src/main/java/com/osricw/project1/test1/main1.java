package com.osricw.project1.test1;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.osricw.project1.R;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by osricwong on 26/10/15.
 */
public class main1 extends FragmentActivity {

    @InjectView(R.id.tv_countDown)
    TextView tvCountDown;

    private long mCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1_main1);
        ButterKnife.inject(this);

//        countDown1();
        countDown2();
    }

    private void countDown1() {
        Observable.interval(1, TimeUnit.SECONDS)
                .map(new Func1<Long, Long>() {

                    @Override
                    public Long call(Long long1) {
                        Log.d("main1", "map(): " + long1.intValue());
                        return mCount--;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                        Log.d("main1", "onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long integer) {
                        Log.d("main1", "onNext(): " + integer.intValue());
                        tvCountDown.setText("" + (long) integer);
                    }
                });
    }

    private void countDown2() {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> sub) {
                Log.d("main1", " -- ");
                Log.d("main1", "Observable.OnSubscribe call() ");
                sub.onNext(mCount);
                sub.onCompleted();
            }
        })
                .map(new Func1<Long, Long>() {

                    @Override
                    public Long call(Long long1) {
                        Log.d("main1", "map(): " + long1.intValue());
                        return mCount--;
                    }
                })
                .delay(1, TimeUnit.SECONDS)
                .repeat(10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                        Log.d("main1", "onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("main1", "onError ");
                    }

                    @Override
                    public void onNext(Long integer) {
                        Log.d("main1", "onNext(): " + integer.intValue());
                        tvCountDown.setText("" + (long)integer);
                    }
                });
    }
}
