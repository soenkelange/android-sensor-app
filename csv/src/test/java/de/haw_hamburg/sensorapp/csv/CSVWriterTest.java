package de.haw_hamburg.sensorapp.csv;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by s.lange on 07.01.17.
 */
public class CSVWriterTest {

    private Writer writer;
    private CSVWriter csvWriter;

    @Before
    public void setUp() throws Exception {
        writer = mock(Writer.class);
        csvWriter = new CSVWriter(writer);
    }

    @Test
    public void construct_ShouldSetDefaults()
    {
        CSVWriter csvWriter = new CSVWriter(writer);
        assertThat(csvWriter.getLineEnd())
                .isEqualTo(CSVWriter.DEFAULT_LINE_END);
        assertThat(csvWriter.getSeparator())
                .isEqualTo(CSVWriter.DEFAULT_SEPARATOR);
    }

    @Test
    public void writeNextLine_ShouldWriteNextLineToWriter() throws Exception {
        List<String> fields = Arrays.asList("hello","world");
        csvWriter.writeNextLine(fields);

        String expectedLine = fields.get(0) + csvWriter.getSeparator() + fields.get(1) + csvWriter.getLineEnd();
        verify(writer).write(eq(expectedLine));
    }
}
