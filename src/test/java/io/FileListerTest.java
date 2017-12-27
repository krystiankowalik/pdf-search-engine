package io;

import com.krystiankowalik.pdfsearchengine.io.FileLister;
import com.krystiankowalik.pdfsearchengine.io.RecursiveFileLister;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FileListerTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private FileLister fileLister;

    @Before
    public void setup() {
        fileLister = new RecursiveFileLister(folder.getRoot().getAbsolutePath());
    }

    @Test
    public void createdFileShouldExist() throws Exception {
        File newFile = folder.newFile("helloMyNewFile");
        boolean exists = newFile.exists();
        assertTrue(exists);
    }

    @Test
    public void createdFilesShouldBeListed() throws Exception {
        Set<File> newFiles = createFilesInTempDirectory("file1", "file2", "file3");

        assertEquals(newFiles, new HashSet<>(fileLister.list()));
    }

    @Test
    public void onlyFilesWithRequiredExtensionShouldBeListed() throws Exception {
        final String MY_FILE = "myFile.ext";
        createFilesInTempDirectory("sampleFile", "anotherFile", MY_FILE);

        assertEquals(
                new ArrayList<>(Arrays.asList(MY_FILE)),
                fileLister.list("ext").stream().map(File::getName).collect(Collectors.toList())
        );
    }

    @Test
    public void nestedFilesShouldBeListed() throws Exception {
        File nestedFolder = folder.newFolder("nestedFolder");
        File nestedFile = new File(nestedFolder, "nestedFile");
        nestedFile.createNewFile();

        assertEquals(nestedFile, fileLister.list().stream().findFirst().get());
    }

    @Test(expected = java.nio.file.NoSuchFileException.class)
    public void nonExistentBaseFolderShouldThrowAnException() throws Exception {
        fileLister = new RecursiveFileLister("nonExistentFolder");
        fileLister.list();
    }

    private Set<File> createFilesInTempDirectory(String... names) throws Exception {
        Set<File> files = new HashSet<>();
        for (String n : names) {
            files.add(createFileInTempDirectory(n));
        }
        return files;
    }

    private File createFileInTempDirectory(String name) throws Exception {
        return folder.newFile(name);
    }

}
