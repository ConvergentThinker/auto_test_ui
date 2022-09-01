package org.autotestui.model;

import org.autotestui.utilities.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
//@Lazy
public class ModelConfig {
    @Autowired
    private ExcelUtils excelUtils;

    @Bean
    public List<BasicFrameworkModel> getFrameworkModels() {
        return excelUtils.retrieveExcelData(BasicFrameworkModel.class, "testdata\\FrameworkTestData.xlsx");
    }
}
