package org.autotestui.uibase.webdriver.beanscope;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.SimpleThreadScope;
import java.util.Objects;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */
public class BrowserScope extends SimpleThreadScope {

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object objDriver = super.get(name, objectFactory);
        SessionId sessionId = ((RemoteWebDriver) objDriver).getSessionId();
        if (Objects.isNull(sessionId)) {
            super.remove(name);
            objDriver = super.get(name, objectFactory);
        } else {
        }
        return objDriver;
    }

}
