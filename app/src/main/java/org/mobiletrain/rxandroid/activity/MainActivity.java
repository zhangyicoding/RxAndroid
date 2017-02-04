package org.mobiletrain.rxandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.mobiletrain.rxandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * http://blog.csdn.net/lzyzsd/article/details/41833541/
 */
public class MainActivity extends AppCompatActivity {
private List<String> list;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("数据"+i);
        }

        Button button = (Button) findViewById(R.id.lambda_btn);
        button.setOnClickListener(v -> lambdaToast());

        CheckBox checkBox = (CheckBox) findViewById(R.id.box);
        checkBox.setOnCheckedChangeListener((v, b) -> lambdaBox(b));
    }

    private void lambdaToast() {
        subscription = Observable.interval(2, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.d("ee",subscription.isUnsubscribed()+"   " +Thread.currentThread().getName());
                    }
                });

    }

    private void lambdaBox(boolean isChecked) {
        Toast.makeText(this, "booean: "+isChecked, Toast.LENGTH_SHORT).show();
    }


    public void action1(View view) {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("HelloWorld");
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };

        observable.subscribe(subscriber);
    }



    public void action2(View view) {
        Observable<String> observable = Observable.just("啊哈哈哈");

        observable
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void action3(View view) {
        Observable<String> observable = Observable.from(list);
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void actionExtra() {
        Observable<String> observable = Observable.just("hahaha");
        observable.subscribe(a -> aa(a));
    }

    private void aa(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    public void nextActivity(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

}
