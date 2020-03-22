package br.com.schumaker.jchip8.io;

import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_MEMORY_SIZE;
import static br.com.schumaker.jchip8.config.Chip8Specs.LOAD_ADDR;
import br.com.schumaker.jchip8.exceptions.InvalidRomSizeException;
import br.com.schumaker.jchip8.exceptions.ReadFromDiskException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author hudson schumaker
 */
public class Chip8FileSystem {
    
    public Chip8FileSystem() {
        System.out.println("fileSystem...............OK");
    }
    
    public byte[] readFromDisk(String address) {
        Path path =  Paths.get(address);
        path.toFile().length();
        if (path.toFile().length() + LOAD_ADDR > CHIP8_MEMORY_SIZE) {            
            throw new InvalidRomSizeException();
        }
      
        byte [] buffer = null;
        try {
            buffer = Files.readAllBytes(path);
        } catch (IOException ex) {
            throw new ReadFromDiskException();
        }
        
        return buffer;
    }
}
