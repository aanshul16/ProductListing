package com.productlisting.util;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Utility class to instantiate Fragment
 */
public class FragmentManagerUtil {
    /**
     * This creates fragment
     *
     * @param id
     * @param pFragment
     * @param tagName
     * @param context
     * @param backStateMaintained
     * @param imageViewTransition
     */
    public static void createFragment(int id, Fragment pFragment, String tagName, AppCompatActivity context, boolean backStateMaintained, ImageView imageViewTransition) {
        if (isActivityActive(context)) {
            FragmentManager fm = context.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (backStateMaintained) {
                ft.addSharedElement(imageViewTransition, ViewCompat.getTransitionName(imageViewTransition));
                ft.addToBackStack(tagName);
            }
            ft.add(id, pFragment, tagName);
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * This is safety check in case of fragment launches and its parent activity is not live
     *
     * @param activity
     * @return
     */
    public static boolean isActivityActive(Activity activity) {
        return !(activity == null || activity.isFinishing()
                || Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 &&
                activity.isDestroyed());
    }
}
