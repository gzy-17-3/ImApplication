package com.gzy.imapplication.module.base;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    /**
     *
     * @param delay 毫秒
     * @param action
     */
    public void runOnBackgroundThread(final long delay, final Runnable action) {
        runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                action.run();
            }
        });
    }

    public void runOnBackgroundThread(Runnable action) {
        new Thread(action).start();
    }

    /**
     *
     * @param delay 毫秒
     * @param action
     */
    public void runOnUiThread(final long delay, final Runnable action) {
        runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(action);
            }
        });
    }
}
