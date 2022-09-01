package org.autotestui.uibase.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import ru.yandex.qatools.ashot.AShot;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */


@Lazy
@Configuration
@Slf4j
public class ScreenShotConfig {

    @Bean
    public AShot getScreenshotUtil() {
        return new AShot();
    }
}
