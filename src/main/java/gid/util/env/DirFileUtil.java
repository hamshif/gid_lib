package gid.util.env;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gideon on 23/05/15.
 */
public class DirFileUtil
{
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String TAG = DirFileUtil.class.getSimpleName();


    public static void writeToText(Context context, String fileName, String[]text)
    {
        BufferedWriter writer = null;

        try
        {
            FileOutputStream openFileOutput = context.openFileOutput(fileName + ".txt", Context.MODE_PRIVATE);

            for(String line: text)
            {
                openFileOutput.write((line + "\n").getBytes());
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            if (writer != null) {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    Log.e(TAG, "", e);
                }
            }
        }
    }


    public static StringBuffer readFileFromInternalStorage(Context context, String fileName)
    {
        String eol = System.getProperty("line.separator");
        BufferedReader input = null;
        StringBuffer buffer = new StringBuffer();

        try
        {
//            Log.d(TAG, "context.getFilesDir(): " + context.getFilesDir());

            input = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));
            String line;

            while ((line = input.readLine()) != null)
            {
                buffer.append(line + eol);
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "", e);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "", e);
                }
            }
        }

        return buffer;
    }


    public static String[] readFileFromInternalStorage1(Context context, String fileName) throws FileNotFoundException
    {
//        Log.d(TAG, "context.getFilesDir(): " + context.getFilesDir());

        String filePath = context.getFilesDir() + "/" + fileName;

        File file = new File(filePath);
        if(!file.exists())
        {
            throw new FileNotFoundException();
        }


        final int max = 50;
        BufferedReader input = null;
        String [] buffer = new String[max];

        //TODO dispose of this java nonsense
        for (int i=0; i<max; i++)
        {
            buffer[i] = "";
        }

        try
        {
            input = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));
            String line;

            int count = 0;

            while ((line = input.readLine()) != null)
            {
//                Log.d(TAG, "count: " + count);

                if(count<max)
                {
                    buffer[count] = line;
                    count++;
                }
                else
                {
                    Log.w(TAG, "The config file has more than the " + max + " read lines");
                    break;
                }
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "", e);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "", e);
                }
            }
        }

        return buffer;
    }

    public static String[] readFileFromInternalStorage(Context context, String fileName, int max)
    {
        BufferedReader input = null;
        String [] buffer = new String[max];

        //TODO dispose of this java nonsense
        for (int i=0; i<max; i++)
        {
            buffer[i] = "";
        }

        try
        {
//            Log.d(TAG, "context.getFilesDir(): " + context.getFilesDir());

            input = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));
            String line;

            int count = 0;

            while ((line = input.readLine()) != null)
            {
                Log.d(TAG, "count: " + count);

                if(count<max)
                {
                    buffer[count] = line;
                    count++;
                }
                else
                {
                    Log.w(TAG, "The config file has more than the " + max + " read lines");
                    break;
                }
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "", e);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "", e);
                }
            }
        }

        return buffer;
    }



    /** Create a file Uri for saving an image or video */
    private Uri getOutputMediaFileUri(Context context, int type)
    {
        return Uri.fromFile(getOutputMediaFile(context, type));
    }

    /** Create a File for saving an image or video */
    public File getOutputMediaFile(Context context, int type)
    {
        String storage_path = context.getExternalFilesDir(null).getAbsolutePath();
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(storage_path + File.separator +
                    "Bulzi_IMG_"+ timeStamp + ".jpg");
        }
        else if(type == MEDIA_TYPE_VIDEO)
        {
            mediaFile = new File(storage_path + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        }
        else
        {
            return null;
        }

        return mediaFile;
    }

    public static String verifyGetMediaPath(Context context)
    {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        String storage_path = context.getExternalFilesDir(null).getAbsolutePath();

        Log.i(TAG, "storage_path: " + storage_path);

        return storage_path;
    }


    public static String verifyPath(String path)
    {

        File f = new File(path);

        // Create the storage directory if it does not exist
        if (! f.exists())
        {
            if (! f.mkdirs())
            {
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        return f.getPath();
    }

    public static File createDirs(String path)
    {
        File f = new File(path);

        // Create the storage directory if it does not exist
        if (! f.exists())
        {
            if (! f.mkdirs())
            {
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        return f;
    }

    public static void copyFile(File src, File dst) throws IOException
    {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

}
