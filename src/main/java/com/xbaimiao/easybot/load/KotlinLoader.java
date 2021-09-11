package com.xbaimiao.easybot.load;

import com.xbaimiao.easybot.EasyBot;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class KotlinLoader {

    private static final String stdlib = "https://maven.aliyun.com/repository/public/org/jetbrains/kotlin/kotlin-stdlib/1.5.20/kotlin-stdlib-1.5.20.jar";
    private static final String reflect = "https://maven.aliyun.com/repository/public/org/jetbrains/kotlin/kotlin-reflect/1.5.20/kotlin-reflect-1.5.20.jar";
    private static final String jdk8 = "https://maven.aliyun.com/repository/public/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.5.20/kotlin-stdlib-jdk8-1.5.20.jar";
    private static final String jdk7 = "https://maven.aliyun.com/repository/public/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.5.20/kotlin-stdlib-jdk7-1.5.20.jar";

    private static final List<Lib> libs = new ArrayList<>();

    static {
        libs.add(new Lib()
                .setUrl(stdlib)
                .setFile(new File("libs/kotlin-stdlib-1.5.20.jar"))
        );
        libs.add(new Lib()
                .setUrl(reflect)
                .setFile(new File("libs/kotlin-reflect-1.5.20.jar"))
        );
        libs.add(new Lib()
                .setUrl(jdk8)
                .setFile(new File("libs/kotlin-stdlib-jdk8-1.5.20.jar"))
        );
        libs.add(new Lib()
                .setUrl(jdk7)
                .setFile(new File("libs/kotlin-stdlib-jdk7-1.5.20.jar"))
        );
    }

    public static void start() {
        for (Lib lib : libs) {
            if (!lib.getFile().exists()) {
                if (!lib.getFile().getParentFile().exists() && lib.getFile().getParentFile().mkdirs()) {
                    System.out.println("loading libs");
                }
                System.out.println("开始下载 " + lib.getFile().getName());
                if (download(lib.getUrl(), lib.getFile())) {
                    System.out.printf("%s 下载完成", lib.getFile().getName());
                }
            }
            Loader.addPath(lib.getFile());
        }
    }

    private static boolean download(String in, File file) {
        try {
            InputStream inputStream = new URL(in).openStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            byte[] buf = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buf)) > 0) {
                bufferedOutputStream.write(buf, 0, len);
            }
            bufferedOutputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static class Lib {

        private File file = null;
        private String url = null;

        public File getFile() {
            return file;
        }

        public Lib setFile(File file) {
            this.file = file;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Lib setUrl(String url) {
            this.url = url;
            return this;
        }

    }

}
