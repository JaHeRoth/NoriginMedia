package rothschild.henning.jacob.noriginmedia.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * See https://stackoverflow.com/a/36614266/1329733
 * Created by Jacob H. Rothschild on 24.07.2017.
 */

public class UnswipeableViewPager extends ViewPager {
	
	public UnswipeableViewPager(Context context) {
		super(context);
	}
	
	public UnswipeableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {
			// ignore move action
		} else if (super.onInterceptTouchEvent(ev)) {
			super.onTouchEvent(ev);
		}
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev);
	}
	
}
