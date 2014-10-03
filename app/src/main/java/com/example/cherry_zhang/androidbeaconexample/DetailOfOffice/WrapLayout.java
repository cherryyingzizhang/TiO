package com.example.cherry_zhang.androidbeaconexample.DetailOfOffice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by shirakawayoshimaru on 2014/09/21.
 */
public class WrapLayout extends RelativeLayout {

    public WrapLayout(Context context) {
        super(context);
    }

    public WrapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addView(View child) {
        // idが未設定の場合は乱数でどうにかする。
        if (child.getId() == -1) { child.setId(new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE)); }

        super.addView(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        // idが未設定の場合は乱数でどうにかする。
        if (child.getId() == -1) { child.setId(new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE)); }

        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        // idが未設定の場合は乱数でどうにかする。
        if (child.getId() == -1) { child.setId(new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE)); }

        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index) {
        // idが未設定の場合は乱数でどうにかする。
        if (child.getId() == -1) { child.setId(new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE)); }

        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        // idが未設定の場合は乱数でどうにかする。
        if (child.getId() == -1) { child.setId(new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE)); }

        super.addView(child, index, params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {

        super.onWindowFocusChanged(hasWindowFocus);

        // 子供の数を取得
        int l = this.getChildCount();
        // 無いなら何もしない
        if (l == 0) {
            return;
        }

        // ディスプレイの横幅を取得
        WindowManager wm = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        // 1行最大長
        int max = display.getWidth();

        // 一番最初は基点となるので何もしない
        View pline = this.getChildAt(0);
        // 現在の１行の長さ
        int currentTotal = pline.getWidth();
        for (int i = 1; i < l; i++) {
            int w = this.getChildAt(i).getWidth();
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) this.getChildAt(i).getLayoutParams();

            // 横幅を超えないなら前のボタンの右に出す
            if (max > currentTotal + w) {
                // 現在長に処理対象コントロール長を加算
                currentTotal += w;
                layoutParams.addRule(RelativeLayout.ALIGN_TOP, this.getChildAt(i - 1).getId());
                layoutParams.addRule(RelativeLayout.RIGHT_OF, this.getChildAt(i - 1).getId());
            } else {
                // 横最大長を超過した場合は折り返す
                layoutParams.addRule(RelativeLayout.BELOW, pline.getId());

                // 基点を変更
                pline = this.getChildAt(i);

                // 長さをリセット
                currentTotal = pline.getWidth();
            }
        }
    }
}