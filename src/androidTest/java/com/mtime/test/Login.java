package com.mtime.test;


import android.widget.TextView;
import com.squareup.spoon.Spoon;

/**
 * Created by zhangmanying on 15/9/11.
 */
public class Login extends BaseTest {
    public Login() throws ClassNotFoundException {
        super(Class.forName("com.mtime.mtmovie.LoginActivity"));
    }
    public void testLogin() throws Exception{
        Thread.sleep(8000);
        Spoon.screenshot(solo.getCurrentActivity(), "gotologin");
        solo.enterText(0, "18600911947");
        Spoon.screenshot(solo.getCurrentActivity(), "enterphonenumber");
        solo.enterText(1, "mtime2");
        Spoon.screenshot(solo.getCurrentActivity(), "enterpassword");
        op.meminfo("Login");
        op.cpuinfo("Login");
        solo.clickOnView(solo.getView("login_btn"));
//        assertEquals("登陆成功", ((TextView)solo.getView("message")).getText());
//        solo.waitForActivity("")
        Spoon.screenshot(solo.getCurrentActivity(),"login");
    }
}
