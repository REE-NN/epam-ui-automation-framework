package utils;

import model.Letter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import config.ConfProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSVUtils {
    private static final Logger log = LogManager.getLogger(CSVUtils.class);

    public static List<Letter> loadData()
    {
        List<Letter> list = new ArrayList<>();
        CSVParser parser;
        try {FileReader Data = new FileReader(ConfProperties.getProperty("csvData_path"));
            parser = CSVParser.parse(Data, CSVFormat.DEFAULT);


            for (CSVRecord csvCell : parser) {
                String inAddress = csvCell.get(0);
                String inSubject = csvCell.get(1);
                String inBody = csvCell.get(2);
                Letter letter = Letter.builder()
                        .address(inAddress)
                        .subject(inSubject)
                        .body(inBody)
                        .build();

                list.add(letter);
            }
        } catch (IOException e) {
            log.error("Ошибка при чтении CSV", e);
            return Collections.emptyList();
        }
        return list;
    }
}