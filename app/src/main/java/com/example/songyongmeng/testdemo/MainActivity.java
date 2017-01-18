package com.example.songyongmeng.testdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.sleep;


/**
 * Creat by SongYongmeng
 * <p>
 * 描    述：此类主要用于演示线程池Executor所提供的4种线程，以及如何正确停止线程。
 **/
public class MainActivity extends AppCompatActivity {
    private Thread a;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopThread();//用于测试停止线程的线程

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this,SecondActivity.class));
//                a.interrupt();//设置停止标识
            }
        });

    }


    //创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
    private void cachedThreadPool() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cachedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    System.out.println(index);
                }
            });
        }
//        cachedThreadPool.shutdown();
    }

    //创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
    private void fixedThreadPool() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
//        fixedThreadPool.shutdown();
    }

    //    创建一个定长线程池，延迟执行
    public void scheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(new Runnable() {

            @Override
            public void run() {
                System.out.println("delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);
//        scheduledThreadPool.shutdown();
    }

    //创建一个定长线程池，延迟周期执行
    public void scheduledThreadPool1() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                System.out.println(format.format(new Date()));
            }
        }, 10, 3, TimeUnit.SECONDS);
//        scheduledThreadPool.shutdown();
    }

    //创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，线程异常关闭后会另起新线程，继续执行。   唯一性、持久性
    public void singleThreadExecutor() {

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
//        singleThreadExecutor.shutdown();
    }

    public void stopThread() {
        a = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("睡觉");
                    sleep(5000);
                    System.out.println("醒来");
                }
            }
        });
        a.start();
    }

}
