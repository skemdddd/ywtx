package com.example.dddkj.ywtx.Widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dddkj.ywtx.Adapter.HomeClassifyGridViewAdapter;
import com.example.dddkj.ywtx.Adapter.HomeClassifyViewPagerAdapter;
import com.example.dddkj.ywtx.Entity.HomeClassifyRoot;
import com.example.dddkj.ywtx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/2/15 8:35
 */

public class HomeFragmentClassify {

    private ViewPager mPager;
    private List<View> mPagerList;
    private HomeClassifyRoot mDatas;
    private LinearLayout mLlDot;
    private LayoutInflater inflater;
    DisplayMetrics dm;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;
    private Context mContext;
    private View mView;
    public HomeFragmentClassify(Context mContext, HomeClassifyRoot mDatas, View view){
        this.mContext= mContext;
        this.mDatas = mDatas;
        this.mView =view;

    }
    public void setClassify(){
        inflater = LayoutInflater.from(mContext);
        mPager = (ViewPager) mView.findViewById(R.id.viewpager);
        mLlDot = (LinearLayout) mView.findViewById(R.id.ll_dot);
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDatas.getData().size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            // 每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.fragment_home_classify_gridview, mPager, false);

//            gridView.setAdapter(new HomeClassifyGridViewAdapter(this, mDatas, i, pageSize));
            gridView.setAdapter(new HomeClassifyGridViewAdapter(mContext,mDatas,i,pageSize));

            mPagerList.add(gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Toast.makeText(mContext,mDatas.getData().get(pos).getCatsName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //设置适配器
        mPager.setAdapter(new HomeClassifyViewPagerAdapter(mPagerList));
        //设置圆点
        if(pageCount>=2){
            setOvalLayout();
            mLlDot.setVisibility(View.VISIBLE);
        }




    }





    /**
     * 设置圆点
     */
    private void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(inflater.inflate(R.layout.fragment_home_classify_dot, null));
        }
        // 默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                mLlDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                mLlDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
}
