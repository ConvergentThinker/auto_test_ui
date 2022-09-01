//todo - to be implemented expected model
package org.autotestui.model;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

@Data
public class ExpectedResultModel {
    @Data
    @ExcelSheet("ExcelBasicScenario")
    public class BasicFrameworkModel {
        @ExcelCellName("Expected Result")
        private String expectedResult;
    }

    @Data
    @ExcelSheet("ExcelAdvancedScenario")
    public class AdvancedFrameworkModel {
        @ExcelCellName("Expected Result")
        private String expectedResult;
    }
}
