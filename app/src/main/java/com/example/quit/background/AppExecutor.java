package com.example.quit.background;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private static AppExecutor appExecutor;
    private final Executor diskIOExecutor;
    private final Executor backgroundThread;
    private final Executor mainThread;

    private AppExecutor(Executor diskIOExecutor, Executor backgroundThread, Executor mainThread) {
        this.diskIOExecutor = diskIOExecutor;
        this.backgroundThread = backgroundThread;
        this.mainThread = mainThread;
    }

    public static AppExecutor getAppExecutor() {
        if (appExecutor == null) {
            synchronized (AppExecutor.class) {
                if (appExecutor == null) {
                    appExecutor = new AppExecutor(Executors.newSingleThreadExecutor(),
                                                  Executors.newFixedThreadPool(3),
                                                  new MainThreadExecutor());
                }
            }
        }
        return appExecutor;
    }

    public Executor getDiskIOExecutor() {
        return diskIOExecutor;
    }

    public Executor getBackgroundThread() {
        return backgroundThread;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
