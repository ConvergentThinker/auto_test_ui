package org.autotestui.utilities;


import org.autotestui.exceptions.ScenarioMissingException;
import org.autotestui.interfaces.CreateDataModel;
import org.autotestui.interfaces.ScenarioModel;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */

@Component
public class GeneralUtils {

    public <T> T getData(List<? extends ScenarioModel> inputData, String scenarioFilter, Class<T> clazz) {
        return clazz.cast(inputData.stream()
                .filter(obj -> obj.getScenario().equalsIgnoreCase(scenarioFilter))
                .findAny()
                .orElseThrow(
                        () -> new ScenarioMissingException("scenario \"" + scenarioFilter + "\" not found.")
                )
        );
    }

    public <T> List<T> getData(List<? extends CreateDataModel> inputData, Class<T> clazz) {

        List<T> subset = inputData.stream()
                .filter(obj -> !obj.isSkip())
                .map(clazz::cast)
                .collect(Collectors.toList());
        return subset;
    }
}
