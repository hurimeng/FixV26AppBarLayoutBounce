package hello.kay.chestnut.fix;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.OverScroller;

import java.lang.reflect.Field;

/**
 * 功能概要描述：反射注入，勿删
 * 功能详细描述：在26版本的sdk上，谷歌解决了之前存在已久的一个问题：AppBarLayout、CollapsingToolbarLayout和RecyclerView共存时，无法通过fling快速展开AppBarLayout
 * 但是随之而来的是一个新问题，当给AppBarlayout施加一个向上的fling后在自行滑动未结束前给下方的RecyclerView(代指)施加一个向下的fling会导致抖动
 * 作者： Kay
 * 创建日期： 18/1/26
 * 修改人：
 * 修改日期：
 * 版本号：
 * 版权所有：Copyright © 2015-2016 上海览益信息科技有限公司 http://www.lanyife.com
 */

public class FixBounceV26Behavior extends AppBarLayout.Behavior {

    private OverScroller mScroller1;

    public FixBounceV26Behavior() {
        super();
    }

    public FixBounceV26Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        bindScrollerValue(context);
    }

    /**
     * 反射注入Scroller以获取其引用
     *
     * @param context
     */
    private void bindScrollerValue(Context context) {
        if (mScroller1 != null) return;
        mScroller1 = new OverScroller(context);
        try {
            Class<?> clzHeaderBehavior = getClass().getSuperclass().getSuperclass();
            Field fieldScroller = clzHeaderBehavior.getDeclaredField("mScroller");
            fieldScroller.setAccessible(true);
            fieldScroller.set(this, mScroller1);
        } catch (Exception e) {}
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            //fling上滑appbar然后迅速fling下滑list时, HeaderBehavior的mScroller并未停止, 会导致上下来回晃动
            if (mScroller1.computeScrollOffset()) {
                mScroller1.abortAnimation();
            }
            //当target滚动到边界时主动停止target fling,与下一次滑动产生冲突
            if (getTopAndBottomOffset() == 0) {
                ViewCompat.stopNestedScroll(target, type);
            }
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }
}

    

