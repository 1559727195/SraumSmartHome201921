package com.massky.sraum.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhu on 2016/12/27.
 */
public class DynamicFragmentViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private List<Fragment> mFragmentList; // 每个Fragment对应一个Page
    private FragmentManager mFragmentManager;
    private ViewPager mViewPager; // viewPager对象
    private int mCurrentPageIndex = 0; // 当前page索引（切换之前）
    private List<String> mTitles;

    //
    private OnExtraPageChangeListener onExtraPageChangeListener; // ViewPager切换页面时的额外功能添加接口

    public DynamicFragmentViewPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager, List<Fragment> fragments,
                                           List<String> list_title) {
        this.mFragmentList = fragments;
        this.mFragmentManager = fragmentManager;
        this.mTitles = list_title;
        this.mViewPager = viewPager;
        this.mViewPager.setAdapter(this);

        this.mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mTitles.size() != 0 ? mTitles.get(position) : "";
    }

    // 动态设置我们标题的方法
    public void setPageTitle(int position, String title) {
        if (position >= 0 && position < mTitles.size()) {
            mTitles.set(position, title);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mFragmentList.get(position).getView());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = mFragmentList.get(position);
        fragment.setMenuVisibility(true);
        if (!fragment.isAdded()) { // 如果fragment还没有added
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            //            ft.commit();
            ft.commitAllowingStateLoss();
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            mFragmentManager.executePendingTransactions();
        }

        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView()); // 为viewpager增加布局
        }

        return fragment.getView();
    }


    /**
     * 当前page索引（切换之前）
     *
     * @return
     */
    public int getCurrentPageIndex() {
        return mCurrentPageIndex;
    }

    public OnExtraPageChangeListener getOnExtraPageChangeListener() {
        return onExtraPageChangeListener;
    }

    /**
     * 设置页面切换额外功能监听器
     *
     * @param onExtraPageChangeListener
     */
    public void setOnExtraPageChangeListener(OnExtraPageChangeListener onExtraPageChangeListener) {
        this.onExtraPageChangeListener = onExtraPageChangeListener;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mFragmentList.get(mCurrentPageIndex).onPause(); // 调用切换前Fargment的onPause()
        //        fragments.get(currentPageIndex).onStop(); // 调用切换前Fargment的onStop()
        if (mFragmentList.get(position).isAdded()) {
            //            fragments.get(i).onStart(); // 调用切换后Fargment的onStart()
            mFragmentList.get(position).onResume(); // 调用切换后Fargment的onResume()
        }
        mCurrentPageIndex = position;

        if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageSelected(position);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageScrollStateChanged(state);
        }
    }

    /**
     * page切换额外功能接口
     */
    public static class OnExtraPageChangeListener {

        public void onExtraPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onExtraPageSelected(int position) {
        }

        public void onExtraPageScrollStateChanged(int state) {
        }
    }
}
