package com.porto.isabel.bakingapp.screens;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.porto.isabel.bakingapp.R;

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable drawable;

    public SimpleDividerItemDecoration(Context context) {
        this.drawable = ContextCompat.getDrawable(context, R.drawable.line_divider);
    }

    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingStart();
        int right = parent.getWidth() - parent.getPaddingEnd();
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; ++i) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + this.drawable.getIntrinsicHeight();
            this.drawable.setBounds(left, top, right, bottom);
            this.drawable.draw(canvas);
        }

    }
}