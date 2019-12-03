package com.gzy.imapplication.module.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class BaseFragment extends Fragment {
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

    public void runOnUiThread(Runnable action) {
        FragmentActivity activity = getActivity();
        assert activity != null;
        activity.runOnUiThread(action);
    }
}
