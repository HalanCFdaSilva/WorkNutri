package com.example.worknutri.support;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.test.espresso.ViewAssertion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class TextViewAssertions {



    public static ViewAssertion matchesTextColor(final int expectedColor) {
        return (view, noViewFoundException) -> {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }
            if (!(view instanceof TextView)) {
                fail("View não é TextView");
            }
            int actual = ((TextView) view).getCurrentTextColor();
            assertEquals("Cor do texto diferente", expectedColor, actual);
        };
    }

    public static ViewAssertion matchesHintColor(final int expectedColor) {
        return (view, noViewFoundException) -> {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }
            if (!(view instanceof TextView)) {
                fail("View não é TextView");
            }
            int actual = ((TextView) view).getCurrentHintTextColor();
            assertEquals("Cor do texto diferente", expectedColor, actual);
        };
    }

    public static ViewAssertion matchesText(final String expectedText) {
        return (view, noViewFoundException) -> {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }
            if (!(view instanceof TextView)) {
                fail("View não é TextView");
            }
            String actual = ((TextView) view).getText().toString();
            assertEquals("Texto diferente", expectedText, actual);
        };
    }



    public static ViewAssertion matchesVisibility(final int expectedVisibility) {
        return (view, noViewFoundException) -> {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }
            assertEquals("Cor do texto diferente", expectedVisibility, view.getVisibility());
        };
    }

    /**
     * Verifica se o background do TextView corresponde ao drawable esperado.
     * Use expectedDrawableResId < 0 para verificar ausência de drawable (null).
     */
    public static ViewAssertion matchesBackgroundDrawable(final int expectedDrawableResId) {
        return (view, noViewFoundException) -> {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }
            if (!(view instanceof TextView)) {
                fail("View não é TextView");
            }

            Drawable actualDrawable = view.getBackground();

            if (expectedDrawableResId < 0) {
                // Espera que não exista drawable
                if (actualDrawable != null) {
                    fail("Esperado sem background drawable, mas encontrado um");
                }
                return;
            }

            Context ctx = view.getContext();
            Drawable expectedDrawable = ContextCompat.getDrawable(ctx, expectedDrawableResId);
            if (expectedDrawable == null) {
                fail("Drawable esperado não encontrado no contexto: " + expectedDrawableResId);
            }

            if (actualDrawable == null) {
                fail("Background do TextView é null");
            }

            boolean match = drawablesMatch(expectedDrawable, actualDrawable);
            if (!match) {
                fail("Background drawable diferente do esperado (resId=" + expectedDrawableResId + ")");
            }
        };
    }

    private static boolean drawablesMatch(Drawable a, Drawable b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;

        Drawable.ConstantState stA = a.getConstantState();
        Drawable.ConstantState stB = b.getConstantState();
        if (stA != null && stA.equals(stB)) {
            return true;
        }

        // If both are BitmapDrawable compare bitmaps directly
        if (a instanceof BitmapDrawable && b instanceof BitmapDrawable) {
            Bitmap ba = ((BitmapDrawable) a).getBitmap();
            Bitmap bb = ((BitmapDrawable) b).getBitmap();
            if (ba != null && bb != null) return ba.sameAs(bb);
        }

        Bitmap ba = drawableToBitmap(a);
        Bitmap bb = drawableToBitmap(b);
        return ba.sameAs(bb);
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
