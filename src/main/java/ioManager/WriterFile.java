package ioManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriterFile implements IWritable{
    private final File file;

    public WriterFile(String path) throws IOException {
        file = new File(path);
        if (!file.isFile())
            while(file.createNewFile());
    }


    @Override
    public void write(String text){
        try (FileOutputStream out = new FileOutputStream(file);
        BufferedOutputStream fw = new BufferedOutputStream(out);) {
            byte[] t = text.getBytes();
            fw.write(t);
        }
        catch (IOException ex){
            System.err.println("Ошибка вывода");
        }

    }

    @Override
    public void writeln(String text){
        write(text+"\n");
    }
}
