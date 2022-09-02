package org.autotestui.uibase.webdriver.browserconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */

@Data
@Component
@ConfigurationProperties("chrome")
public class ChromeConfig {

    // todo - configure this later
    private List<String> args;

}
