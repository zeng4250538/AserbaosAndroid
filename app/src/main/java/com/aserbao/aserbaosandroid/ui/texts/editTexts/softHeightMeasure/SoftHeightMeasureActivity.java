package com.aserbao.aserbaosandroid.ui.texts.editTexts.softHeightMeasure;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aserbao.aserbaosandroid.AUtils.AUI.layout.FlowLayout;
import com.aserbao.aserbaosandroid.R;
import com.aserbao.aserbaosandroid.commonData.ImageSource;
import com.aserbao.aserbaosandroid.commonData.StaticFinalValues;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 非全屏状态下的软键盘显示
 */
public class SoftHeightMeasureActivity extends AppCompatActivity {

    @BindView(R.id.soft_et)
    EditText softEt;
    @BindView(R.id.show_soft_height_tv)
    TextView showSoftHeightTv;
    @BindView(R.id.soft_height_rl)
    RelativeLayout softHeightRl;
    @BindView(R.id.image_view_bg)
    ImageView mImageViewBg;
    @BindView(R.id.soft_height_fl)
    FlowLayout mSoftHeightFl;
    private int mHeightDifference;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int intExtra = getIntent().getIntExtra(StaticFinalValues.TYPE, -1);
        if (intExtra != -1) {
            getWindow().setSoftInputMode(intExtra);
        }
        setContentView(R.layout.activity_soft_height_measure);
        ButterKnife.bind(this);
        initListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mImageViewBg.setImageResource(ImageSource.getRandomImageId());
    }

    private void initPop(int offy) {
        View rootView = LayoutInflater.from(this).inflate(R.layout.test_pop, null);
        mPopupWindow = new PopupWindow(rootView.getRootView(), WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(rootView.getRootView(), Gravity.AXIS_Y_SHIFT, 0, -offy);
    }

    private void initListener() {
        showSoftHeightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPop(mHeightDifference);
            }
        });
        View mChildOfContent = softHeightRl.getChildAt(0);
        mChildOfContent.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        softHeightRl.getWindowVisibleDisplayFrame(r);
                        int screenHeight = softHeightRl.getRootView().getHeight();
                        int screenWidth = softHeightRl.getRootView().getWidth();
                        mHeightDifference = screenHeight - (r.bottom);
                        int widthDifference = screenWidth - (r.right);
                        if(mHeightDifference > 200){
                            //软键盘显示
                            mSoftHeightFl.setVisibility(View.GONE);
                        }else{
                            //软键盘隐藏
                            mSoftHeightFl.setVisibility(View.VISIBLE);
                        }
                        showSoftHeightTv.setText("检测布局屏幕显示的Top:" + r.top + "\n" +
                                "检测布局屏幕显示的Bottom:" + r.bottom + "\n" +
                                "检测布局屏幕显示的Left:" + r.left + "\n" +
                                "检测布局屏幕显示的Right:" + r.right + "\n" +
                                "检测布局屏幕getHeight:" + screenHeight + "\n" +
                                "检测布局屏幕getWidth:" + screenWidth + "\n" +
                                "检测布局屏幕显示高度差为:" + mHeightDifference + "\n" +
                                "检测布局屏幕显示宽度差为:" + +widthDifference);
                    }
                });
    }

    @OnClick({R.id.bt_adjustPan, R.id.bt_adjustResize, R.id.bt_adjustUnspecified, R.id.btn_adjustNothing,
            R.id.btn_stateHidden, R.id.btn_stateAlwaysHidden, R.id.btn_stateVisible, R.id.btn_stateAlwaysVisible})
    public void onViewClicked(View view) {
        int inputMode = 0;
        switch (view.getId()) {
            case R.id.bt_adjustPan:
                inputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
                break;
            case R.id.bt_adjustResize:
                inputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
                break;
            case R.id.bt_adjustUnspecified:
                inputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED;
                break;
            case R.id.btn_adjustNothing:
                inputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING;
                break;
            case R.id.btn_stateHidden:
                inputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
                startActivityOwn(inputMode);
                break;
            case R.id.btn_stateAlwaysHidden:
                inputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
                startActivityOwn(inputMode);
                break;
            case R.id.btn_stateVisible:
                inputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;
                startActivityOwn(inputMode);
                break;
            case R.id.btn_stateAlwaysVisible:
                inputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
                startActivityOwn(inputMode);
                break;
        }
        getWindow().setSoftInputMode(inputMode);
    }

    public void startActivityOwn(int input) {
        Intent intent = new Intent(this, SoftHeightMeasureActivity.class);
        intent.putExtra(StaticFinalValues.TYPE, input);
        startActivity(intent);
    }

}
