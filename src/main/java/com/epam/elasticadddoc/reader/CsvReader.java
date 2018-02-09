package com.epam.elasticadddoc.reader;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.List;

public interface CsvReader {
    public List<CSVRecord> readCsvFile(String fileName) throws IOException;
}
