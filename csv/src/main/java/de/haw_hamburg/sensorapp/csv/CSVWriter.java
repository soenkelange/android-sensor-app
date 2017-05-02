package de.haw_hamburg.sensorapp.csv;

import java.io.IOException;
import java.io.Writer;

public class CSVWriter {

    public static final String DEFAULT_LINE_END = "\n";
    public static final String DEFAULT_SEPARATOR = ",";

    private final Writer writer;
    private String lineEnd = DEFAULT_LINE_END;
    private String separator = DEFAULT_SEPARATOR;

    public CSVWriter(Writer writer) {
        this.writer = writer;
    }

    public void writeNextLine(String[] fields) throws IOException {
        StringBuilder stringAppendable = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i != 0) {
                stringAppendable.append(separator);
            }
            String field = fields[i];
            stringAppendable.append(field);
        }
        stringAppendable.append(lineEnd);
        writer.write(stringAppendable.toString());
    }

    public String getLineEnd() {
        return lineEnd;
    }

    public void setLineEnd(String lineEnd) {
        this.lineEnd = lineEnd;
    }

    public String getSeparator() {
        return separator;
    }

    private void setSeparator(String separator) {
        this.separator = separator;
    }
}
