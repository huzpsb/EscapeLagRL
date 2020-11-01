package com.mcml.space.util;

import javax.management.MBeanServer;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;

public class HeapDumper {
    private static volatile Object MXBean;

    public static void dumpHeap(File file) {
        if (MXBean == null && !init()) {
            AzureAPI.log(Locale.isNative() ? "Dump内存堆失败!" : "Failed to dump heap!");
        } else {
            try {
                Method method = Class.forName("com.sun.management.HotSpotDiagnosticMXBean").getMethod("dumpHeap", String.class, Boolean.TYPE);
                method.invoke(MXBean, file.toString(), true);
            } catch (Exception var2) {
                AzureAPI.log(Locale.isNative() ? "Dump内存堆失败!" : "Failed to dump heap!");
                AzureAPI.log(var2.toString());
            }

        }
    }

    private static boolean init() {
        try {
            Class<?> clazz = Class.forName("com.sun.management.HotSpotDiagnosticMXBean");
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            MXBean = ManagementFactory.newPlatformMXBeanProxy(server, "com.sun.management:type=HotSpotDiagnostic", clazz);
            return true;
        } catch (Exception var2) {
            AzureAPI.log(Locale.isNative() ? "无法初始化Dump内存堆系统!" : "Failed to init dump heap server!");
            AzureAPI.log(var2.toString());
            return false;
        }
    }
}
