package com.concise.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 该类用于Glide加载圆角图片
 * 用法：在into()前调用transform(new GlideRoundTransformUtil(context))即可
 *       但该用法会使cnterCrop()、fitCenter()失效
 * 推荐使用：transform(new CenterCrop(context),new GlideRoundTransformUtil(context))
 *           或transform(new MultiTransformation(new CenterCrop(context),new GlideRoundTransformUtil(context)))
 *
 * @author 赵洪苛
 * @version 1.0
 */

public class GlideRoundTransformUtil extends BitmapTransformation {

    private static float radius = 0f;

    public GlideRoundTransformUtil(Context context) {
        this(context, 5);
    }

    public GlideRoundTransformUtil(Context context, int dp) {
        super(context);
        radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}
