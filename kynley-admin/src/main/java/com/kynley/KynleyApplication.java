package com.kynley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author kynley
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class KynleyApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(KynleyApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  进销存管理系统启动成功   ლ(´ڡ`ლ)ﾞ " +
                ".__                                 .__\n" +
                "|  |__  __ _______      ____ _____  |__|\n" +
                "|  |  \\|  |  \\__  \\   _/ ___\\\\__  \\ |  |\n" +
                "|   Y  \\  |  // __ \\_ \\  \\___ / __ \\|  |\n" +
                "|___|  /____/(____  /  \\___  >____  /__|\n" +
                "     \\/           \\/       \\/     \\/"
        );
    }
}
