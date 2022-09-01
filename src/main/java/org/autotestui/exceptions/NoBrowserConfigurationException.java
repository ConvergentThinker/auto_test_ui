package org.autotestui.exceptions;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */

public class NoBrowserConfigurationException extends RuntimeException{
    public NoBrowserConfigurationException(String errorMessage) {
        super (errorMessage);
    }
}
