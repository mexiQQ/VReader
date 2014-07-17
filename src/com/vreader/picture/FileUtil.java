package com.vreader.picture;

import java.io.File;  
import java.io.IOException;  
  
import android.os.Environment;  
import android.util.Log;  
  
public class FileUtil {  
    private static final String TAG = "FileUtil";  
  
    public static File getCacheFile(String imageUri){  
        File cacheFile = null;  
        try {  
            if (Environment.getExternalStorageState().equals(  
                    Environment.MEDIA_MOUNTED)) {  
                File sdCardDir = Environment.getExternalStorageDirectory();  
                Log.v("test1",imageUri+"=imageUri");
                String fileName = getFileName(imageUri);  
                Log.v("test1","filename="+fileName);
                File dir = new File(sdCardDir.getCanonicalPath()  
                		+ "/"+AsynImageLoader.CACHE_DIR
                		 );  
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
                cacheFile = new File(dir, fileName);  
                Log.i(TAG, "exists:" + cacheFile.exists() + ",dir:" + dir + ",file:" + fileName);  
            }    
        } catch (IOException e) {  
            e.printStackTrace();  
            Log.e(TAG, "getCacheFileError:" + e.getMessage());  
        }  
        return cacheFile;  
    }  
      
    public static String getFileName(String path) {  
		 return path.substring(22,32);
    }  
}  