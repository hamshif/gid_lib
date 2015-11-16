package gid.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;

/**
 * Created by gideon on 04/06/15.
 */
public class ImageUtil
{
    private static final String TAG = ImageUtil.class.getSimpleName();

    public static void readImageOrientation(String fullPathName)
    {
        ExifInterface exif = null;
        try
        {
            exif = new ExifInterface(fullPathName);
            Log.d(TAG, "EXIF value: " + exif.getAttribute(ExifInterface.TAG_ORIENTATION));

        }
        catch (IOException e)
        {
            Log.e(TAG, "No exif", e);
        }
    }

    public static void renameToDatetime(String fullPathName)
    {
        ExifInterface exif = null;
        try
        {
            exif = new ExifInterface(fullPathName);
            Log.d(TAG, "EXIF value: " + exif.getAttribute(ExifInterface.TAG_DATETIME));

        }
        catch (IOException e)
        {
            Log.e(TAG, "No exif", e);
        }
    }





    public static Bitmap rotate(Bitmap bitmap, int degree)
    {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }


    //TODO Mot tested
    public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera)
    {
        android.hardware.Camera.CameraInfo cameraInfo = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, cameraInfo);

        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation)
        {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
        {
            result = (cameraInfo.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        }
        else // back-facing
        {
            result = (cameraInfo.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
}
