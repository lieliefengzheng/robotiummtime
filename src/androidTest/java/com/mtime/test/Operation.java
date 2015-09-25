package com.mtime.test;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStreamReader;
import com.robotium.solo.Solo;
import java.io.FileNotFoundException;
import java.io.*;
import junit.framework.Assert;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by zhangmanying on 15/4/14.
 */
    public class Operation extends Assert {
    private Solo solo;
    static final String TAG = "Test->Operation";

    Operation(Solo so) {
        solo = so;
    }

    /**
     * 日志输出页面中所有View的信息
     */
    public void printAllViews() {
        Log.d(TAG, "以下是此页面的所有View信息");
        ArrayList<View> views = solo.getViews();
        if (views != null) {
            for (int i = 0; i < views.size(); i++) {
                if (views.get(i) != null) {

                    String id = ((View) views.get(i)).getId() + "";
                    String name = ((View) views.get(i)).toString();
                    String nn = ((View) views.get(i)).getClass().toString();
                    Log.d(TAG, "Id:" + id);
                    Log.d(TAG, "Name:" + name);
                    Log.d(TAG, "Class" + nn);
                    if (views.get(i) instanceof TextView) {

                        Log.d(TAG, ((TextView) views.get(i)).getText().toString());
                    }
                }
            }
        }
    }

    /**
     * 获取当前页面的所有文字
     */
    public ArrayList<String> getAllViewsText() {
        Log.d(TAG, "得到页面的所有View的文字");
        ArrayList<View> views = solo.getViews();
        ArrayList<String> viewstext = new ArrayList<String>();
        for (int i = 0; i < views.size(); i++) {
            if (views.get(i) instanceof TextView) {
                viewstext.add(((TextView) views.get(i)).getText().toString());
                Log.d(TAG, "Class" + ((TextView) views.get(i)).getText().toString());
            }
        }
        return viewstext;
    }

    /**
     * 查找某字符串
     */

    public boolean findText(String text) {
        boolean result = false;
        ArrayList<String> viewsText = getAllViewsText();
        for (int i = 0; i < viewsText.size(); i++) {
            if (viewsText.get(i).toString().indexOf(text) >= 0) {
                result = true;
                Log.d(TAG, "找到了");
                break;
            }
        }
        if (result == false)
            Log.d(TAG, "没有找到----" + text);
        return result;
    }

    /**
     * 日志输出现在时间
     */
    public void printDateAndTime() {
        Date date = new Date();
        String formatDay = String.format("%tF", date);
        String formatTime = String.format("%tT", date);
        Log.d(TAG, formatDay + ":" + formatTime);
    }

    /**
     * 获取正在执行的Activity类名
     */
    public String getCurrentActivityName() {
        String activityName = solo.getCurrentActivity().getClass().getName();
        Log.d(TAG, "当前ActivityName是" + activityName);
        return activityName;
    }

    public void getdp() {
        DisplayMetrics dm;
        dm = new DisplayMetrics();
        solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.d(TAG, "手机屏幕分辨率为:" + dm.widthPixels + "　*　" + dm.heightPixels);
    }

    public int scroll(int i) {
        solo.scrollUp();
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        return 1;
    }

    public String screendownscroll(String viewName, int i) {
        View view = solo.getView(viewName);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        solo.drag(location[0], location[0], location[1], location[1] - view.getHeight() * i, 5);
        return null;
    }

    public String screenupscroll(String viewName, int i) {
        View view = solo.getView(viewName);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        solo.drag(location[0], location[0], location[1], location[1] + view.getHeight() * i, 5);
        return null;
    }

    public String screenrightscroll(String viewName, int i) {
        View view = solo.getView(viewName);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        solo.drag(location[0], location[0] - view.getWidth() * i, location[1], location[1], 5);
        return null;
    }

    public String screenleftscroll(String viewName, int i) {
        View view = solo.getView(viewName);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        solo.drag(location[0], location[0] + view.getWidth() * 2, location[1], location[1], 5);
        return null;
    }

    public void resultToFile(String result) {
        String processedStr = result;
        String fullFilename = "output";
        try {
            File newTextFile = new File(fullFilename);
            FileWriter fw;
            fw = new FileWriter(newTextFile);
            fw.write(processedStr);
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void outputToFile(String result) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("/Users/zhangmanying/robotiummtime/src/androidTest/java/com/mtime/test/output.txt", true);
            fw.write(result);//这里向文件中输入结果123
            fw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void meminfo(String name){
        Runtime runTime = Runtime.getRuntime();
        Process process;
        String full = "";
        String error = "";
        try {
        process = runTime.exec("/system/bin/cat /proc/meminfo");
        process.waitFor();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errin = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String inline;
        inline =br.readLine();
        Log.d("sys", inline);
        while ((inline = br.readLine()) != null) {
            full +=inline;
        }
        while ((inline = errin.readLine()) != null) {
            error += inline;
        }
        if (!error.isEmpty()) {
            System.err.println("\n=== ADB reports: ===");
            System.err.println(error);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        System.out.println("App was overenthuasiastic. Couldn't wait for thread to finish.");
    }
        Log.d(name, full);
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void cpuinfo(String name){
        Runtime runTime = Runtime.getRuntime();
        Process process;
        String full = "";
        String error = "";
        try {
            process = runTime.exec("/system/bin/cat /proc/cpuinfo");
            process.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errin = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String inline;
            inline =br.readLine();
            while ((inline = br.readLine()) != null) {
                full +=inline;
            }
            while ((inline = errin.readLine()) != null) {
                error += inline;
            }
            if (!error.isEmpty()) {
                System.err.println("\n=== ADB reports: ===");
                System.err.println(error);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("App was overenthuasiastic. Couldn't wait for thread to finish.");
        }
        Log.d(name,full);
    }

}