package gid.util.connect;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import gid.util.env.DirFileUtil;

/**
 * Created by gideon on 22/05/15.
 */

public class FileUtility
{
    private static final String TAG = FileUtility.class.getCanonicalName();

    public static InputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
        {
            throw new IOException("Not an HTTP connection");
        }

        try
        {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK)
            {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");
        }
        return in;
    }

    public boolean downloadImage(Context context, String aurl, String targetFileName)
    {
        int count;
        try
        {
            URL url = new URL(aurl);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            int lenghtOfFile = conexion.getContentLength();

            Log.d(TAG, "file length: " + lenghtOfFile);

            String PATH = DirFileUtil.verifyGetMediaPath(context) + "/" + "blzi";
            File folder = new File(PATH);
            if(!folder.exists())
            {
                folder.mkdir();//If there is no folder it will be created.
            }

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(PATH + "/" + targetFileName);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1)
            {
                total += count;
//                publishProgress ((int)(total*100/lenghtOfFile));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        }
        catch (Exception e)
        {
            Log.e(TAG, "", e);

            return false;
        }

        return true;
    }

    public boolean getProcessed(String aurl, String path, String targetFileName)
    {
        int count;
        try
        {
            URL url = new URL(aurl);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            int lenghtOfFile = conexion.getContentLength();

//            Log.d(TAG, "file length: " + lenghtOfFile);

            File folder = new File(path);
            if(!folder.exists())
            {
                folder.mkdir();//If there is no folder it will be created.
            }

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(path + "/" + targetFileName);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1)
            {
                total += count;
//                publishProgress ((int)(total*100/lenghtOfFile));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        }
        catch (Exception e)
        {
            Log.e(TAG, "", e);

            return false;
        }

        Log.i(TAG, "download complete");
        return true;
    }
}
