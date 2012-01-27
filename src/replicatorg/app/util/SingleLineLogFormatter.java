package replicatorg.app.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SingleLineLogFormatter extends Formatter {

  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  private static final DateFormat dateFormat =
      DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

  @Override
  public String format(LogRecord record) {
    StringBuilder sb = new StringBuilder();

    sb.append(dateFormat.format(new Date(record.getMillis())))
      .append(" ")
      .append(record.getLevel().getLocalizedName())
      .append(" ")
      .append(record.getSourceClassName())
      .append(".")
      .append(record.getSourceMethodName())
      .append(": ")
      .append(formatMessage(record))
      .append(LINE_SEPARATOR);

    if (record.getThrown() != null) {
      try {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        record.getThrown().printStackTrace(pw);
        pw.close();
        sb.append(sw.toString());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return sb.toString();
  }
}