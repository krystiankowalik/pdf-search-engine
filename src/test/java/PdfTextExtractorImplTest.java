import com.krystiankowalik.pdfsearchengine.pdf.searcher.RegexPdfSearcher;
import com.krystiankowalik.pdfsearchengine.pdf.searcher.RegexPdfSearcherImpl;
import kotlin.text.Regex;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import util.PdfTestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PdfTextExtractorImplTest {

    private final static String fileName = "fileName.pdf";
    private final static String text = "This is 1 sample text to be tested";

    private static String fullPath;
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private RegexPdfSearcher regexPdfSearcher;

    @Before
    public void setup() throws IOException {
        fullPath = temporaryFolder + File.pathSeparator + fileName;
        PdfTestUtils.createSamplePdf(fullPath, text);
        regexPdfSearcher = new RegexPdfSearcherImpl(fullPath);
    }

    @Test
    public void shouldReturnNonEmptyString() {
        assertTrue(regexPdfSearcher.containsRegex(new Regex(text)));
    }

    @Test
    public void shouldMatchEachWord() {
        Arrays.asList(text.split(" ")).forEach(w ->
                assertTrue(regexPdfSearcher.containsRegex(new Regex(w)))
        );
    }

    @Test
    public void shouldMatchPartialWords() {
        assertTrue(regexPdfSearcher.containsRegex(new Regex(".*ample")));
    }

    @Test
    public void shouldNotMatchNonPresentData(){
        assertFalse(regexPdfSearcher.containsRegex(new Regex(".*it is not present in text.*")));
    }

    @After
    public void tearDown() {
        File file = new File(fullPath);
        file.delete();
    }

    /*@Test
    public void shouldReturnSpecificText() {
        assertEquals(text.trim(), pdfTextExtractorImpl.getText().trim());
    }*/

}
