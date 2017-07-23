package rothschild.henning.jacob.noriginmedia.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * See https://stackoverflow.com/a/36614266/1329733
 * Created by Jacob H. Rothschild on 24.07.2017.
 */

public class DynamicViewPager extends ViewPager {
	
	private final AtomicBoolean touchesAllowed = new AtomicBoolean();
	
	public DynamicViewPager(Context context) {
		super(context);
	}
	
	public DynamicViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private boolean touchesAllowed() {
		return touchesAllowed.get();
	}
	
	public void enableTouches() {
		touchesAllowed.set(true);
	}
	
	public void disableTouches() {
		touchesAllowed.set(false);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (touchesAllowed()) {
			return super.onInterceptTouchEvent(ev);
		} else {
			if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {
				// ignore move action
			} else {
				if (super.onInterceptTouchEvent(ev)) {
					super.onTouchEvent(ev);
				}
			}
			return false;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (touchesAllowed()) {
			return super.onTouchEvent(ev);
		} else {
			return MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev);
		}
	}
	
}
