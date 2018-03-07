import io.FileListerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import pdf.PdfTextSearcherImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FileListerTest.class,
        PdfTextSearcherImplTest.class
})
public class TestSuite {
}
