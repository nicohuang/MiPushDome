package hwz.com.mipushdome;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * 1、为了打开客户端的日志，便于在开发过程中调试，需要自定义一个Application。
 * 并将自定义的application注册在AndroidManifest.xml文件中
 * 2、为了提高push的注册率，您可以在Application的onCreate中初始化push。你也可以根据需要，在其他地方初始化push。
 * 
 * @author wangkuiwei
 * 
 */
public class DemoApplication extends Application {

    // 申请的APPID
    public static final String APP_ID = "2882303761517359244";
    // 申请的APPKEY
    public static final String APP_KEY = "5191735998244";

    private static DemoMessageReceiver.DemoHandler handler = null;




    @Override
    public void onCreate() {
        super.onCreate();

        // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
        if (handler == null)
            handler = new DemoMessageReceiver.DemoHandler(getApplicationContext());
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static DemoMessageReceiver.DemoHandler getHandler() {
        return handler;
    }
}