package com.subinkrishna.android.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.subinkrishna.android.ui.R;

/**
 * Implementation of a {@link ListView} that can have parallax headers. 
 * 
 * @author Subinkrishna Gopi
 */
public class ParallaxHeadListView extends ListView {

	/** Log Tag */
	private final static String TAG = ParallaxHeadListView.class.getSimpleName();
	
	public ParallaxHeadListView(Context context) {
		super(context);
		init();
	}
	
	public ParallaxHeadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		setOnScrollListener(mScrollListener);
	}

	/**
	 * Set the parallax header view.
	 * 
	 * @param headerView
	 */
	public void setParallaxHeader(final View headerView) {
		if (null == headerView)
			return;
		
		if (headerView instanceof ParallaxListHeader) {
			super.addHeaderView(headerView, null, false);
		} else {
			// Wrap the view and add it!
			final ParallaxHeaderWrapper wrapper = ParallaxHeaderWrapper.createInstance(getContext(), headerView);
			if (null != wrapper) {
				super.addHeaderView(wrapper, null, false);
			}
		}
	}
	
	@Override
	public void addHeaderView(final View headerView) {
	}
	
	@Override
	public void addHeaderView(final View headerView, final Object headerData, final boolean isSelectable) {
	}
	
	/** List scroll listener */
	private OnScrollListener mScrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			
		}
		
		@Override
		public void onScroll(AbsListView list, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// Get the first item in the list
			final View aListItem = ((0 == firstVisibleItem) && (null != list)) ? list.getChildAt(0) : null;
			
			if ((null == aListItem) || !(aListItem instanceof ParallaxListHeader))
				return;
			
			final ParallaxListHeader parallaxHeader = (ParallaxListHeader) aListItem;
			parallaxHeader.onListScroll(list, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	};
	
	/**
	 *  
	 * @author Subinkrishna Gopi
	 */
	public static interface ParallaxListHeader {
		public void onListScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount);
	}
	
	/**
	 * Wrapper for the header view. A wrapper is needed to translate the header
	 * view's Y axis with out changing the header height in order to achieve the
	 * parallax effect.
	 * 
	 * @author Subinkrishna Gopi
	 */
	public static class ParallaxHeaderWrapper extends RelativeLayout 
	implements ParallaxListHeader {

		/**
		 * Creates an instance of the {@link ParallaxHeaderWrapper}
		 * 
		 * @param ctx
		 * @param headerView
		 * @return
		 */
		public static ParallaxHeaderWrapper createInstance(final Context ctx, final View headerView) {
			if (null == headerView)
				return null;
			
			ParallaxHeaderWrapper wrapper = new ParallaxHeaderWrapper(ctx);
			final int headerH = ctx.getResources().getDimensionPixelSize(R.dimen.parallax_header_height);
			
			// Layout params - wrapper
			final AbsListView.LayoutParams wrapperParams = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, headerH);
			wrapper.setBackgroundColor(Color.BLACK);
			wrapper.setLayoutParams(wrapperParams);
			
			// Override the child layout params & add it to the wrapper
			RelativeLayout.LayoutParams childParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, headerH);
			headerView.setLayoutParams(childParams);
			
			wrapper.addView(headerView);
			
			return wrapper;
		}
		
		public ParallaxHeaderWrapper(Context context) {
			super(context);
		}
		
		@Override
		public void onListScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			final int top = Math.abs(getTop());
			final int height = getHeight();
			final View child = getChildAt(0);
			final float alphaOffset = (top) / (float) height;
			
			child.setTranslationY(top / 2); // Translate Y
			child.setAlpha((1 - alphaOffset) + .3f); // Alpha
		}
		
	}

}
