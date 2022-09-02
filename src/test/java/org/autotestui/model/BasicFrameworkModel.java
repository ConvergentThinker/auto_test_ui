package org.autotestui.model;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;
import lombok.ToString;
import org.autotestui.interfaces.ScenarioModel;
import java.util.Date;
import java.util.List;

@Data
@ExcelSheet("ExcelBasicScenario")
public class BasicFrameworkModel implements ScenarioModel {

    @ExcelCellName("Scenario")
    private String scenario;

    @ExcelCellName("Integer Data")
    private int intData;

    @ExcelCellName("Float Data")
    private float floatData;

}
