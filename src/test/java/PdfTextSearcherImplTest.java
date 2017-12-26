import com.krystiankowalik.pdfsearchengine.pdf.extractor.PdfTextExtractor;
import com.krystiankowalik.pdfsearchengine.pdf.extractor.PdfTextExtractorImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import util.PdfTestUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PdfTextSearcherImplTest {

    private final static String fileName = "fileName.pdf";
    private final static String text = "Hello World";


    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private PdfTextExtractor pdfTextExtractorImpl;

    @Before
    public void setup() throws IOException {
        PdfTestUtils.createSamplePdf(temporaryFolder + File.pathSeparator + fileName, text);
        pdfTextExtractorImpl = new PdfTextExtractorImpl(temporaryFolder + File.pathSeparator + fileName);
    }


    @Test
    public void shouldReturnNonEmptyString() {
        assertTrue(pdfTextExtractorImpl.getText().length() > 0);
    }

    @Test
    public void shouldReturnSpecificText() {
        assertEquals(text.trim(), pdfTextExtractorImpl.getText().trim());
    }

}
