package com.vreader.keep;

/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.vreader.domain.User;

/**
 * 该类定义了微博授权时所需要的参数。
 * 
 * @author SINA
 * @since 2013-10-07
 */
public class UserInfoKeeper {
    private static final String PREFERENCES_NAME = "com_weibo_sdk_android_user";

    private static final String USER_NAME  = "username";
    private static final String USER_ID  = "userid";
    private static final String USER_URL = "userPictureUrl";
    
    /**
     * 保存 Token 对象到 SharedPreferences。
     * 
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
    public static void writeUser(Context context, User user) {
        if (null == context || null == user) {
            return;
        }
        clear(context);
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(USER_NAME, user.getUsername());
        editor.putString(USER_ID, user.getUserid());
        editor.putString(USER_URL, user.getUserPictureUrl());
        editor.commit();
    }

    /**
     * 从 SharedPreferences 读取 Token 信息。
     * 
     * @param context 应用程序上下文环境
     * 
     * @return 返回 Token 对象
     */
    public static User readUser(Context context) {
        if (null == context) {
            return null;
        }

        User user=new User();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        user.setUsername(pref.getString(USER_NAME, ""));
        user.setUserid(pref.getString(USER_ID, ""));
        user.setUserPictureUrl(pref.getString(USER_URL, ""));
        return user;
    }

    /**
     * 清空 SharedPreferences 中 Token信息。
     * 
     * @param context 应用程序上下文环境
     */
    public static void clear(Context context) {
        if (null == context) {
            return;
        }
        
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
