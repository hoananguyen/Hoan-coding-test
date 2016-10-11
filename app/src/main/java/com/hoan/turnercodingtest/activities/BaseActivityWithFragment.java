package com.hoan.turnercodingtest.activities;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hoan.turnercodingtest.utils.Logger;

/**
 * Created by Hoan on 12/3/2015.
 */
public class BaseActivityWithFragment extends AppCompatActivity {

    public void showFragment(int containerResId, Fragment fragment) {
        Logger.d(BaseActivityWithFragment.class.getSimpleName(), "showFragment(" + containerResId + ")");
        showFragment(containerResId, fragment, false, false);
    }

    public void showFragment(int containerResId, Fragment fragment, boolean addToBackStack) {
        Logger.d(BaseActivityWithFragment.class.getSimpleName(), "showFragment("
                + containerResId + ", " + addToBackStack + ")");
        showFragment(containerResId, fragment, addToBackStack, false);
    }

    public void showFragment(int containerResId, Fragment fragment, boolean addToBackStack, boolean isAnimated) {
        Logger.d(BaseActivityWithFragment.class.getSimpleName(), "showFragment("
                + containerResId + ", " + addToBackStack + ", " + isAnimated + ")");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(containerResId, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }
}