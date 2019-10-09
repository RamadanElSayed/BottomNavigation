package info.androidhive.bottomnavigation.helper;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ravi on 21/12/17.
 */

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

    public BottomNavigationBehavior() {
        super();
    }

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
        return dependency instanceof FrameLayout;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        if (dy < 0) {
            showBottomNavigationView(child);
        } else if (dy > 0) {
            hideBottomNavigationView(child);
        }
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }



    private void hideBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(view.getHeight());
    }

    private void showBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(0);
    }
}
