package cn.kasogg.common.util;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {

    /**
     * Debug Log tag
     */
    public static final String TAG = "CrashHandler";
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;
    /**
     * 程序的Context对象
     */
    private Context mContext;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     * @return
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {

            // Sleep一会后结束程序
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error : ", e);
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {

        if (ex == null) {
            Log.w(TAG, "handleException --- ex==null");
            return true;
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        final String msg = writer.toString();
        if (msg == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出。", Toast.LENGTH_LONG)
                        .show();

            }
        }.start();

        // 发送错误报告到服务器
        sendCrashToServer(msg);
        return true;
    }

    /**
     * 把错误报告发送给服务器.
     *
     * @param errorMsg
     */
    @SuppressWarnings("deprecation")
    private void sendCrashToServer(String errorMsg) {
        String sdk_version = "";
        String model = "";
        String os_version = "";
        String phoneNumber = "";
        String memorySize = "";

        String app_version = "";

        try {
            TelephonyManager tm = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);

            sdk_version = android.os.Build.VERSION.SDK; // SDK号
            model = android.os.Build.MODEL; // 手机型号
            os_version = android.os.Build.VERSION.RELEASE; // android系统版本号
            phoneNumber = tm.getLine1Number();// 获取本机号码
            memorySize = getAvailMemory();
        } catch (Exception e) {
            sdk_version = "未知";
            model = "未知";
            os_version = "未知";
            phoneNumber = "未知";
            memorySize = "未知";
        }

        if (StringUtils.isEmpty(phoneNumber)) {
            phoneNumber = "获取手机号码失败";
        }
    }

    private String getAvailMemory() {// 获取android当前可用内存大小

        ActivityManager am = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存

        return Formatter.formatFileSize(mContext, mi.availMem);// 将获取的内存大小规格化
    }
}