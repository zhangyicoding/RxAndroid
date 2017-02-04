package org.mobiletrain.rxandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.mobiletrain.rxandroid.MyHttpUtils;
import org.mobiletrain.rxandroid.R;
import org.mobiletrain.rxandroid.entity.PersonEntity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity {
    public static final String IMG_URL = "https://www.baidu.com/img/bd_logo1.png";

    private PersonEntity person;

    private List<PersonEntity> personList;
    private List<PersonEntity.CouseEntity> courseList;
    private ImageView imageView;



//    private List<String> nameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView = (ImageView) findViewById(R.id.iv);

        person = new PersonEntity();
        person.setName("赵四");


        personList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PersonEntity person = new PersonEntity();
            person.setName("姓名"+i);
            courseList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                PersonEntity.CouseEntity course = person.new CouseEntity();
                course.setCourseName(person.getName() + "的课程"+ j);
                courseList.add(course);
            }
            person.setCouseList(courseList);

            personList.add(person);
        }

//        nameList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            PersonEntity person = new PersonEntity();
//            person.setName(i+"胖子");
//            personList.add(person);
//        }
    }

    // map变换.PersonEntity -> String
    public void action1(View view) {
        Observable.just(person)
                .map(new Func1<PersonEntity, String>() {
                    @Override
                    public String call(PersonEntity personEntity) {
                        return personEntity.getName();
                    }
                })
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Toast.makeText(SecondActivity.this, "姓名hashCode ： " + integer, Toast.LENGTH_SHORT).show();
                    }
                });

//        observable
//                .map(person -> person.getName())
//                .subscribe(str -> Toast.makeText(this, str, Toast.LENGTH_SHORT).show());
    }

    // flatMap
    public void action2(View view) {
//        Observable
//                .from(personList)// Observable<PersonEntity>
//                .flatMap(new Func1<PersonEntity, Observable<PersonEntity.CouseEntity>>() {
//                    @Override
//                    public Observable<PersonEntity.CouseEntity> call(PersonEntity personEntity) {
//                        List<PersonEntity.CouseEntity> couseList = personEntity.getCouseList();
//                        return Observable.from(couseList);
//                    }
//                })
//                .flatMap(new Func1<PersonEntity.CouseEntity, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(PersonEntity.CouseEntity couseEntity) {
//                        String couseName = couseEntity.getCourseName();
//                        return Observable.just(couseName);
//                    }
//                })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.d("ee", s);
//                    }
//                });

        Observable
                .from(personList)
                .flatMap(p -> Observable.from(p.getCouseList()))
                .flatMap(c -> Observable.just(c.getCourseName()))
                .subscribe(str -> Log.d("ee", str));

    }

    public void action3(View view) {
        Observable
                .just(IMG_URL)
                .observeOn(Schedulers.newThread())
                .map(url -> MyHttpUtils.getBitmapFromUrl(url))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> imageView.setImageBitmap(bitmap));
    }

    public void action0(View view) {
        // 方式一
//        Observable<List<PersonEntity>> observable = Observable.just(personList);
//        observable
//                .map(new Func1<List<PersonEntity>, List<String>>() {
//                    @Override
//                    public List<String> call(List<PersonEntity> personEntities) {
//                        for (PersonEntity personEntity : personEntities) {
//                            nameList.add(personEntity.getName());
//                        }
//                        return nameList;
//                    }
//                })
//                .subscribe(new Action1<List<String>>() {
//                    @Override
//                    public void call(List<String> names) {
//                        Toast.makeText(SecondActivity.this, "size : " + names.size(), Toast.LENGTH_SHORT).show();
//                    }
//                });

        // 方式二
//        Observable<PersonEntity> observable = Observable.from(personList);
//        observable.subscribe(new Action1<PersonEntity>() {
//            @Override
//            public void call(PersonEntity personEntity) {
//                nameList.add(personEntity.getName());
//                Toast.makeText(SecondActivity.this, "size : " + nameList.size(), Toast.LENGTH_SHORT).show();
//            }
//        });

        // 方式三
//        final Observable<List<PersonEntity>> observable = Observable.just(personList);
//        observable.flatMap(new Func1<List<PersonEntity>, Observable<PersonEntity>>() {
//            @Override
//            public Observable<PersonEntity> call(List<PersonEntity> personEntities) {
//                return Observable.from(personEntities);
//            }
//        })
//                .flatMap(new Func1<PersonEntity, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(PersonEntity personEntity) {
//                        return null;
//                    }
//                })

    }

    private Observable<PersonEntity> getPerson(List<PersonEntity> list) {
        return Observable.from(list);
    }

}
