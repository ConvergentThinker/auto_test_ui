package org.autotestui.uibase.base;

import org.autotestui.utilities.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;




/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */

public abstract class StepBase {
    @Autowired
    protected GeneralUtils generalUtils;
}
