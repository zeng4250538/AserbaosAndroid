package com.aserbao.aserbaosandroid.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aserbao.aserbaosandroid.R;
import com.aserbao.aserbaosandroid.base.adapters.BaseRecyclerViewActivityAdapter;
import com.aserbao.aserbaosandroid.base.beans.BaseRecyclerBean;
import com.aserbao.aserbaosandroid.base.interfaces.IBaseRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能:
 *
 * @author aserbao
 * @date : On 2019/2/19 4:40 PM
 * @email: 1142803753@qq.com
 * @project:AserbaosAndroid
 * @package:com.aserbao.aserbaosandroid.base
 */
public abstract class BaseRecyclerViewActivity extends AppCompatActivity implements IBaseRecyclerItemClickListener {

    @BindView(R.id.opengl_recycler_view)
    public RecyclerView mOpenglRecyclerView;

    public BaseRecyclerViewActivityAdapter mCommonAdapter;

    public List<BaseRecyclerBean> mBaseRecyclerBeen = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        ButterKnife.bind(this);
        initGetData();
        initView();
    }

    public abstract  void initGetData();


    public void initView() {
        mCommonAdapter = new BaseRecyclerViewActivityAdapter(this,this, mBaseRecyclerBeen,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mOpenglRecyclerView.setLayoutManager(gridLayoutManager);
        mOpenglRecyclerView.setAdapter(mCommonAdapter);
    }
}
