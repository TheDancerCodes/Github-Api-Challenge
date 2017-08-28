package com.nabwera.github_api_challenge.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabwera on 26/08/2017.
 */

public class ShareUtil {

    public static void shareCustomUtil(String message, Context context){
        String text = message;
        List<Intent> targetShareIntents=new ArrayList<>();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        List<ResolveInfo> resInfos =context.getPackageManager().queryIntentActivities(shareIntent, 0);

        if(!resInfos.isEmpty()){
            for(ResolveInfo resInfo : resInfos){
                String packageName = resInfo.activityInfo.packageName;

                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_TEXT, text);
                intent.setPackage(packageName);
                targetShareIntents.add(intent);
            }

            if(!targetShareIntents.isEmpty()){
                Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Share with friends");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                context.startActivity(chooserIntent);
            }else{
            }
        }
    }
}
