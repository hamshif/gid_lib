package gid.util;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GidUtil
{
	private static final String TAG = "Utility";
	private static Toast t;
	
	public static void clearWindow(Activity a)
	{
		a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public static void clearTitle(Activity a)
	{
		a.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public static void dontRotate(Activity a)
	{
		a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	public static boolean checkFile(String path, String tag)
	{
		if(path == null)
		{
			return false;
		}
		
		File f = new File(path);
		boolean exists = false; 
	
		if(f.exists())
		{
			exists = true;
			Log.i(TAG + " called by " + tag, "found file " + f.getName());
		}
		else
		{
			Log.w(TAG + " called by " + tag, "Bug! didn't find file: " + f.getName() + " at path: " + path);
		}
		
		return exists;
	}

	public static void tellUser(Context context, String message, int duration)
	{
		t = Toast.makeText(context, message, duration);
		t.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
		t.show();
	}
	
	public static boolean isExternalStorageReadOnly()
    { 
        String extStorageState = Environment.getExternalStorageState();
        
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState))
        { 
            return true; 
        } 
        
        return false; 
    } 

    public static boolean isExternalStorageAvailable() 
    { 
        String extStorageState = Environment.getExternalStorageState();
        
        if (Environment.MEDIA_MOUNTED.equals(extStorageState))
        { 
            return true; 
        } 
        
        return false; 
    }

	public static void killToasts() 
	{
		if(t == null)
		{
			return;
		}
		
		t.cancel();
		Log.i(TAG, "Toast cancelled");
	} 
	
	public static String getPicFromDir(String dirPath, String fileName) //throws NoSuchFileException
	{
		if(dirPath == null || fileName == null)
		{
			return null;
		}
		
		File dir = new File(dirPath);
		
		if(dir.isDirectory())
		{
			Log.d(TAG, "directory: " + dirPath + "  was found");
			
			String[] fileList = dir.list();
			
			for(String f: fileList)
			{
				String fileType = null;
				// different file types can't use ends with because of the middle e.g. s0anchor.png / s0.png
				if(f.startsWith(fileName))
				{
					if(f.equals(fileName + ".jpg"))
					{
						fileType = ".jpg";
					}
					else if(f.equals(fileName + ".JPG"))
					{
						fileType = ".JPG";
					}
					else if(f.equals(fileName + ".png"))
					{
						fileType = ".png";
					}
				}
				
				if(fileType != null)
				{
					String s = dirPath + "/" + fileName + fileType;
					Log.d(TAG, "pic file:  " + s + "  was found");
					
					return s;
				}
			}
		}
		else
		{

			Log.e(TAG, "directory: " + dirPath + "   not found!");
		}
		
		Log.e(TAG, "didn't find pic file: " + dirPath + "/" + fileName);
		
		return null;
	}
	
	public static String getAudioFromDir(String dirPath, String fileName) //throws NoSuchFileException
	{
		if(dirPath == null || fileName == null)
		{
			return null;
		}
		
		File dir = new File(dirPath);
		
		if(dir.isDirectory())
		{
			Log.d(TAG, "directory: " + dirPath + "  was found");
			
			String[] fileList = dir.list();
			
			for(String f: fileList)
			{
				String fileType = null;
				// different file types can't use ends with because of the middle e.g. s0anchor.png / s0.png
				if(f.startsWith(fileName))
				{
					if(f.equals(fileName + ".wma"))
					{
						fileType = ".wma";
					}
					else if(f.equals(fileName + ".mp3"))
					{
						fileType = ".mp3";
					}
				}
				
				if(fileType != null)
				{
					String s = dirPath + "/" + fileName + fileType;
					Log.d(TAG, "Audio file:  " + s + "  was found");
					
					return s;
				}
			}
		}
		else
		{
			Log.e(TAG, "directory: " + dirPath + "   not found!");
		}
		
		return null;
	}
	
	public static String fillSpaces(String query)
	{
		String fillspaces = "";
		
		for(int i=0; i<query.length(); i++)
	    {
	    	if(query.charAt(i) == ' ')
	    	{
	    		fillspaces += "%20";
	    	}
	    	else if(query.charAt(i) == '\n')
	    	{
	    		fillspaces += "%0D%0A";
	    	}
	    	else
	    	{
	    		fillspaces += query.charAt(i);
	    	}
	    }
		
		return fillspaces;
	}


	static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";


	public static String getUTCDatetime()
	{
		final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		final String utcTime = sdf.format(new Date());

		return utcTime;
	}

	public static int pxToDp(Context c, int px)
	{
		DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
		int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return dp;
	}

	public static int dpToPx(Context c, int dp)
	{
		DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}


	public static boolean isEmailValid(String email)
	{
		String regExpn =
				"^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
						+"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
						+"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
						+"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
						+"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
						+"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if(matcher.matches())
			return true;
		else
			return false;
	}

	public static void toggle(View view)
	{
		if(view.isShown())
		{
			view.setVisibility(View.GONE);
		}
		else
		{
			view.setVisibility(View.VISIBLE);
		}
	}

}
