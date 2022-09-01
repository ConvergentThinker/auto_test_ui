package org.autotestui.exceptions;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */

public class ScenarioMissingException extends RuntimeException{
    public ScenarioMissingException (String errorMessage) {
        super (errorMessage);
    }
}
