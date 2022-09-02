package org.autotestui.utilities;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */


@Slf4j
@Component
public class ExcelUtils {

    @Value("${testdata.default}")
    private String defaultTestData;

    public <T> List<T> retrieveExcelData(Class<T> typeClass, String sourceFile, PoijiOptions...poijiOptionalOpts) {
        PoijiOptions opts = PoijiOptions.PoijiOptionsBuilder
                .settings() // skip first row after header (header is already skipped by default)
                .preferNullOverDefault(false) // Date,Float,Double,Integer,Long,String will have 'null' value rather than default value.
                .addListDelimiter(";") // default is comma(,)
                .caseInsensitive(true) // used by @ExcelCellName to ignore the case-sensitivity
                .ignoreWhitespaces(true) // used by @ExcelCellName to ignore whitespaces (trim)
                .build();
        if ( poijiOptionalOpts.length >= 1 ) {
            opts = poijiOptionalOpts[0];
        }
        long debugStartTime = System.nanoTime();
        List<T> data = Poiji.fromExcel(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\"+ sourceFile), typeClass, opts);
        log.info("[IAF] Excel reading for model {} took : {} ms", typeClass.getSimpleName(), (System.nanoTime() - debugStartTime) / 1_000_000);
        return data;
    }

    public <T> List<T> retrieveExcelData(Class<T> typeClass) {
        return retrieveExcelData(typeClass, defaultTestData);
    }
}
