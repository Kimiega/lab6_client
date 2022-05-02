package ioManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ReaderFile implements IReadable{
    private String path;
    private BufferedReader br;
    private List<String> lines;
    private int it;
    Scanner scanner;

    public ReaderFile(String path) throws IOException {
        lines = Files.readAllLines(Paths.get(path));
        it = 0;
    }

    @Override
    public String readline() {
        if (it==lines.size())
            return null;
        return lines.get(it++);
    }
    public Float readFloat(){
        if (scanner.hasNextFloat())
            return scanner.nextFloat();
        else
            scanner.next();
        return null;
    }
}
