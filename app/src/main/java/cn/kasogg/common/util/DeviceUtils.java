package cn.kasogg.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

public class DeviceUtils {

    public static enum NetProviderName {
        chinaMobile("中国移动"),
        chinaUnicom("中国联通"),
        chinaTelecom("中国电信"),
        chinaNetcom("中国网通"),
        other("未知");

        private String text;

        private NetProviderName(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
    }

    /**
     * 获取手机的IMEI(International Mobile Equipment Identity) 	输入*#06#即可查询
     * 中文翻译为国际移动装备辨识码,即通常所说的手机序列号,用于在手机网络中识别每一部独立的手机，是国际上公认的手机标志序号。
     * 序列号共有15位数字，
     * 前6位（TAC）:型号核准号码，代表手机类型。
     * 接着2位（FAC）:最后装配号，代表产地。
     * 接着6位（SNR）:串号，即生产顺序号。
     * 最后1位（SP）一般为0，是检验码，备用。
     *
     * @param context
     * @param context
     * @return
     * @date 2014-2-27 下午10:38:48
     * @author leo
     */
    public static String getIMEI(Context context) {
        TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ts.getDeviceId();
    }

    /**
     * 获取手机的IMSI(International Mobile Subscriber Identity)
     * 中文翻译为国际移动用户识别码。它是在公众陆地移动电话网（PLMN）中用于唯一识别移动用户的一个号码。在GSM网络，这个号码通常被存放在SIM卡中
     * IMSI由MCC、MNC、MSIN组成，
     * MCC:移动国家号码，由3位数字组成，唯一地识别移动客户所属的国家，我国为460；
     * MNC:网络id，由2位数字组成， 用于识别移动客户所归属的移动网络，中国移动为00、02、07，中国联通为01,中国电信为03；
     * MSIN:移动客户识别码，采用等长11位数字构成。 唯一地识别国内GSM移动通信网中移动客户。
     * 所以要区分是移动还是联通，只需取得SIM卡中的MNC字段即可
     *
     * @param context
     * @return
     * @date 2014-2-27 下午10:43:07
     * @author leo
     */
    public static String getIMSI(Context context) {
        TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ts.getSubscriberId();
    }

    /**
     * 判断用户GPS是否开启()
     *
     * @param context
     * @return
     * @date 2014-2-27 下午10:45:26
     * @author leo
     */
    public static boolean isGPSEnable(Context context) {
        String str = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        Log.v("GPS", str);
        if (!StringUtils.isEmpty(str)) {
            return str.contains("gps");
        }
        return false;
    }

    /**
     * 将GPS开启状态设置成相反状态(如果原来开着则关闭，如果关着则打开 )
     *
     * @param context
     * @date 2014-2-27 下午10:54:51
     * @author leo
     */
    public static void toggleGPS(Context context) {
        boolean isOpen = Settings.Secure.isLocationProviderEnabled(context.getContentResolver(), LocationManager.GPS_PROVIDER);
        Settings.Secure.setLocationProviderEnabled(context.getContentResolver(), LocationManager.GPS_PROVIDER, !isOpen);
    }

    /**
     * 关闭GPS功能
     *
     * @param contex
     * @date 2014-2-27 下午11:17:44
     * @author leo
     */
    public static void closeGps(Context contex) {
        if (isGPSEnable(contex)) {
            toggleGPS(contex);
        }
    }

    /**
     * 将WIFI开启状态设置成相反状态(如果原来开着则关闭，如果关着则打开 )
     *
     * @param context
     * @date 2014-2-27 下午11:18:40
     * @author leo
     */
    public static void toggleWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(!wifiManager.isWifiEnabled());
    }

    /**
     * 获取当前手机的网络提供商
     *
     * @param context
     * @return
     * @date 2014-2-27 下午10:38:06
     * @author leo
     */
    public static NetProviderName getNetProviderName(Context context) {
        String imsi = getIMSI(context);
        if (!StringUtils.isEmpty(imsi)) {
            // 因为移动网络编号46000下的IMSI已经用完,所以虚拟了一个46002编号，134/159号段使用了此编号
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
                return NetProviderName.chinaMobile;
            } else if (imsi.startsWith("46001")) {
                return NetProviderName.chinaUnicom;
            } else if (imsi.startsWith("46003")) {
                return NetProviderName.chinaTelecom;
            } else {
                return NetProviderName.other;
            }
        } else {
            return NetProviderName.other;
        }
    }

    /**
     * 得到系统的版本号
     *
     * @return
     * @date 2014-2-28 下午3:13:29
     * @author leo
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 得到应用的版本号
     *
     * @return
     * @date 2014-2-28 下午3:13:48
     * @author leo
     */
    public static int getAppVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        int versionCode = 0;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 得到应用版本名称
     *
     * @param context
     * @return
     * @date 2014-2-28 下午3:15:59
     * @author leo
     */
    public static String getAppVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String versionCode = null;
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static int getSDKVersionInt() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static String getSDKVersion() {
        return String.valueOf(android.os.Build.VERSION.SDK_INT);
    }

    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    public static String getPhoneNumber(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getLine1Number();
        } catch (Exception e) {
            return "";
        }
    }

}