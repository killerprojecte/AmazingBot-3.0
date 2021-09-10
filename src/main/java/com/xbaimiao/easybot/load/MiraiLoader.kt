package com.xbaimiao.easybot.load

/* Download the mirai library during the first loading
 * This can highly reduce the size of jar file
 * Currently this system work but still not the best solution
 *
 * This system is completely depent on Taboolib library system
 */
import com.xbaimiao.easybot.EasyBot
import com.xbaimiao.easybot.utils.IO
import java.io.File

object MiraiLoader {

    private val folder = "libs"

    init {
        val f = File(folder)
        if (!f.exists()) {
            f.mkdirs()
        }
    }

    private val libs = ArrayList<Lib>().also {
        val miraiVersion = "2.7.1-dev-1"
        it.add(Lib("https://maven.aliyun.com/repository/public/org/bouncycastle/bcprov-jdk15on/1.64/bcprov-jdk15on-1.64.jar"))
        it.add(Lib("https://maven.aliyun.com/repository/public/net/mamoe/mirai-core-all/$miraiVersion/mirai-core-all-$miraiVersion-all.jar"))
    }

    @JvmStatic
    fun start() {
        for (lib in libs) {
            if (!lib.file.exists()) {
                EasyBot.INSTANCE.logger.info("下载机器人依赖 ${lib.file.name} ...")
                IO.downloadFile(lib.url, lib.file)
                EasyBot.INSTANCE.logger.info("${lib.file.name} 下载完成!")
            }
            Loader.addPath(lib.file)
        }
        EasyBot.INSTANCE.logger.info("EasyBot 所需依赖加载完成 Support By jim20071128")
    }

    class Lib(val url: String) {

        private val args = url.split("/")

        val file = File(folder, args[args.size - 1])

    }

}
