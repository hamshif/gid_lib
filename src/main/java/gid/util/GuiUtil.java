package gid.util;

import android.util.Log;
import android.view.View;

/**
 * Created by gideon on 04/06/15.
 */
public class GuiUtil
{
    public static void toggleView(View v)
    {
        if(v.isShown())
        {
            v.setVisibility(View.INVISIBLE);
        }
        else
        {
            v.setVisibility(View.VISIBLE);
        }
    }
}
