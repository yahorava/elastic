package com.epam.elasticadddoc.reader.impl;

import com.epam.elasticadddoc.constant.Constant;
import com.epam.elasticadddoc.reader.CsvReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class CsvReaderImpl implements CsvReader{
   /* private static final String [] FILE_HEADER_MAPPING = {
            Constant.SUBJECT,
            Constant.DATE_SENT,
            Constant.BODY,
            Constant.FROM_ADDRESS_TYPE,
            Constant.FROM_ADDRESS,
            Constant.FROM_DISPLAY,
            Constant.TO_ADDRESS_TYPE,
            Constant.TO_ADDRESS,
            Constant.TO_DISPLAY,
            Constant.CC_ADDRESS_TYPE,
            Constant.CC_ADDRESS,
            Constant.CC_DISPLAY,
            Constant.BCC_ADDRESS_TYPE,
            Constant.BCC_ADDRESS,
            Constant.BCC_DISPLAY,
            Constant.IMPORTANCE,
    };*/

    public  List<CSVRecord> readCsvFile(String fileName) throws IOException {
        List<CSVRecord> csvRecords;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        try( FileReader fileReader = new FileReader(fileName);
                CSVParser csvFileParser = new CSVParser(fileReader, csvFileFormat)) {
            csvRecords = csvFileParser.getRecords();
        }
        return csvRecords;
    }
}
