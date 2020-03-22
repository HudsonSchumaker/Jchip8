package br.com.schumaker.jchip8.io;

import br.com.schumaker.jchip8.exceptions.InvalidRomSizeException;
import br.com.schumaker.jchip8.exceptions.ReadFromDiskException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author hudson schumaker
 */
public class Chip8FileSystemTest {

    @Test
    public void testReadFromDisk() throws IOException {
        File tempRomFile = File.createTempFile("hello", ".rom");
        String content = "This is the temporary file content";
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempRomFile));
        bw.write(content);
        bw.close();

        Chip8FileSystem tested = new Chip8FileSystem();
        byte[] result = tested.readFromDisk(tempRomFile.getAbsolutePath());
        String strResult = new String(result);
        assertEquals(content, strResult);
    }

    @Test(expected = ReadFromDiskException.class)
    public void testReadFromDiskIOExecption() throws IOException {
        Chip8FileSystem tested = new Chip8FileSystem();
        tested.readFromDisk("invalid adress");
    }

    @Test(expected = InvalidRomSizeException.class)
    public void testReadFromDiskInvalidRomSizeException() throws IOException {
        File tempRomFile = File.createTempFile("hello", ".rom");
        RandomAccessFile raf = new RandomAccessFile(tempRomFile, "rw");
        raf.setLength(4096);
        raf.close();
        
        Chip8FileSystem tested = new Chip8FileSystem();
        tested.readFromDisk(tempRomFile.getAbsolutePath());
    }
}
