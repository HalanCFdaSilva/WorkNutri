package com.example.worknutri.support;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DrawableMatcher extends TypeSafeMatcher<View> {

    private final int expectedId;
    private String resourceName;

    private DrawableMatcher(@DrawableRes int expectedId) {
        this.expectedId = expectedId;
    }

    public static DrawableMatcher withDrawable(@DrawableRes int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    @Override
    protected boolean matchesSafely(View target) {
        if (!(target instanceof ImageView)) {
            return false;
        }
        ImageView imageView = (ImageView) target;
        if (expectedId < 0) {
            return imageView.getDrawable() == null;
        }

        Context context = TestUtil.getThemedContext();
        Drawable expectedDrawable = context.getDrawable(expectedId);
        try {
            resourceName = context.getResources().getResourceEntryName(expectedId);
        } catch (Exception ignored) {}

        if (expectedDrawable == null) {
            return false;
        }

        Drawable actualDrawable = imageView.getDrawable();
        if (actualDrawable == null) {
            return false;
        }

        Drawable.ConstantState expectedState = expectedDrawable.getConstantState();
        Drawable.ConstantState actualState = actualDrawable.getConstantState();
        if (expectedState != null && actualState != null) {
            if (expectedState.equals(actualState)) {
                return true;
            }
        }

        if (expectedDrawable instanceof BitmapDrawable && actualDrawable instanceof BitmapDrawable) {
            Bitmap expectedBitmap = ((BitmapDrawable) expectedDrawable).getBitmap();
            Bitmap actualBitmap = ((BitmapDrawable) actualDrawable).getBitmap();
            if (expectedBitmap != null && actualBitmap != null) {
                return expectedBitmap.sameAs(actualBitmap);
            }
        }

        Bitmap expectedBitmap = drawableToBitmap(expectedDrawable);
        Bitmap actualBitmap = drawableToBitmap(actualDrawable);
        return expectedBitmap.sameAs(actualBitmap);

    }

    @Override
    public void describeTo(Description description) {
        description.appendText("ImageView with drawable resource id: ");
        if (resourceName != null) {
            description.appendText(resourceName);
        } else {
            description.appendValue(expectedId);
        }
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
            if (bmp != null) return bmp;
        }

        int width = drawable.getIntrinsicWidth() > 0 ? drawable.getIntrinsicWidth() : 1;
        int height = drawable.getIntrinsicHeight() > 0 ? drawable.getIntrinsicHeight() : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
