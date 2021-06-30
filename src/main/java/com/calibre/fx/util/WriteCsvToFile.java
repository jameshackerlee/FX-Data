package com.calibre.fx.util;

import com.calibre.fx.json.FxData;
import com.calibre.fx.mapping.ClientFxCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.Unique;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class WriteCsvToFile {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${csv.file.directory}")
  public String _FILE_DIRECTORY;

  public void exportDownload(String fileName, HttpServletResponse response, List<ClientFxCode> clientFxCodeList, String[] header, String[] data) throws IOException {
    ICsvBeanWriter csvBeanWriter = null;

    try {
      response.setContentType("text/csv");
      response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=\"" + fileName + "\"");

      csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
      csvBeanWriter.writeHeader(header);

      for (ClientFxCode clientFxCode : clientFxCodeList) {
        csvBeanWriter.write(clientFxCode, data);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        csvBeanWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void clientFxCodeExportWrite(String fileName, List<ClientFxCode> clientFxCodeList, String[] header, String[] data) throws IOException {
    ICsvBeanWriter csvBeanWriter = null;

    try {
      csvBeanWriter = new CsvBeanWriter(new FileWriter(_FILE_DIRECTORY + fileName), CsvPreference.STANDARD_PREFERENCE);
      final CellProcessor[] processors = getProcessors();

      csvBeanWriter.writeHeader(header);

      for (ClientFxCode clientFxCode : clientFxCodeList) {
        csvBeanWriter.write(clientFxCode, data, processors);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        csvBeanWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void fxDataExportWrite(String fileName, List<FxData> fxDataList) throws IOException {
    ICsvBeanWriter csvBeanWriter = null;

    String[] header = { "NO", "FOREX", "VALUE" };
    String[] data = { "no", "code", "value" };

    try {
      csvBeanWriter = new CsvBeanWriter(new FileWriter(_FILE_DIRECTORY + fileName), CsvPreference.STANDARD_PREFERENCE);
      final CellProcessor[] processors = getProcessors();

      csvBeanWriter.writeHeader(header);

      logger.debug("Data : " + fxDataList.size());

      for (FxData fxData : fxDataList) {
        csvBeanWriter.write(fxData, data, processors);
      }

      logger.debug("csv file created successfully");

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        csvBeanWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static CellProcessor[] getProcessors(){
    return new CellProcessor[] {
        new Unique(new ParseInt()),   // NO
        new NotNull(),                // FOREX Code
        new ParseDouble()};           // Value
  }
}
