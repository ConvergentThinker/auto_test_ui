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

    //Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * Read excel data from given {@code sourceFile} location and map into Java model with specified class {@code typeClass}
     *
     * <br><br>
     * <b>Sample Usage :</b>
     * <pre>
     *     {@code
     *     // map data from MySampleTestData.xlsx into MySamplePOJOModel class
     *     excelUtils.retrieveExcelData(MySamplePOJOModel.class, "testdata/MySampleTestData.xlsx");
     *     }
     * </pre>
     *
     * @param typeClass  target Java class to be mapped into
     * @param sourceFile excel file location
     * @return List of java objects (each row in excel is 1 object)
     */
    public <T> List<T> retrieveExcelData(Class<T> typeClass, String sourceFile, PoijiOptions...poijiOptionalOpts) {
        //todo - handling large excel file ( 1000+ rows ) -- see Consumer class in poiji
        log.debug("[IAF] Retrieving excel data for model : {}", typeClass.getSimpleName());
        PoijiOptions opts = PoijiOptions.PoijiOptionsBuilder
                .settings() // skip first row after header (header is already skipped by default)
                .preferNullOverDefault(false) // Date,Float,Double,Integer,Long,String will have 'null' value rather than default value.
                .addListDelimiter(";") // default is comma(,)
                .caseInsensitive(true) // used by @ExcelCellName to ignore the case-sensitivity
                .ignoreWhitespaces(true) // used by @ExcelCellName to ignore whitespaces (trim)
                //.namedHeaderMandatory(true) // it'll check if @ExcelCellName has corresponding header
                .build();

        // if user passed-in the poijiOptions, use it instead of default one.
        if ( poijiOptionalOpts.length >= 1 ) {
            opts = poijiOptionalOpts[0];
            log.debug("[IAF] Excel reader is using user-defined POIJI options");
        }

        long debugStartTime = System.nanoTime();

        System.out.println("%%%%%%%%%%%%%"+ System.getProperty("user.dir")+"\\src\\test\\resources\\"+ sourceFile);

        List<T> data = Poiji.fromExcel(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\"+ sourceFile), typeClass, opts);

        log.info("[IAF] Excel reading for model {} took : {} ms", typeClass.getSimpleName(), (System.nanoTime() - debugStartTime) / 1_000_000);
        return data;
    }

    /**
     * Read excel data from default directory (defined in {@code application.properties} as {@code testdata.default}) and map into Java model with specified class {@code typeClass}
     *
     * <br><br>
     * <b>Sample Usage :</b>
     * <pre>
     *     {@code
     *     // map data from ${testdata.directory} into MySamplePOJOModel class
     *     excelUtils.retrieveExcelData(MySamplePOJOModel.class);
     *     }
     * </pre>
     *
     * @param typeClass target Java class to be mapped into
     * @return List of java objects (each row in excel is 1 object)
     */
    public <T> List<T> retrieveExcelData(Class<T> typeClass) {
        return retrieveExcelData(typeClass, defaultTestData);
    }
}
