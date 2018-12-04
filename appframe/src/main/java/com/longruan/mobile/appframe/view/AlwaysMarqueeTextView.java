package com.longruan.mobile.appframe.view;

import android.content.Context;
import android.util.AttributeSet;

public class AlwaysMarqueeTextView extends android.support.v7.widget.AppCompatTextView {
	 
	public AlwaysMarqueeTextView(Context context) {
	super(context);
	}
	 
	public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
	super(context, attrs);
	}
	 
	public AlwaysMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	}
	 
	//始终返回true，即一直获得焦点
	@Override
	public boolean isFocused() {
	   return true;
	}
}