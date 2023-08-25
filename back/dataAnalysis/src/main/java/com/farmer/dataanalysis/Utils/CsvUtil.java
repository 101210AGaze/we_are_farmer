package com.farmer.dataanalysis.Utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class CsvUtil {
    public static<T> void export(File filePath, List<T>data) throws IOException {
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator("\n").withQuote(null);
        FileWriter fileWriter=new FileWriter(filePath);
        try (CSVPrinter printer = new CSVPrinter(fileWriter, formator)) {
            //写入列头数据
            //printer.printRecord(headers);
            if (null != data) {
                for (T lineData : data) {
                    printer.printRecord(lineData);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
