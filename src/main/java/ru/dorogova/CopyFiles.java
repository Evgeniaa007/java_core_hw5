package ru.dorogova;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;


public class CopyFiles {

    public static void main(String[] args) {
        try {
            backupProcedure("src/homework", "backup");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void backupProcedure(String originDir, String backupDir) throws IOException {
        File origin = new File(originDir);
        File backup = new File(backupDir);

        if(!backup.exists()){
            backup.mkdir(); // если не существует директории для бэкапа, мы её создаём
        }

        if (origin.exists()) {
            File[] files = origin.listFiles();
            for(File file : files){
                if(!file.isDirectory()){
                    File backupFile = new File(backup, file.getName());
                    backupFile.createNewFile(); //создаем пустой файл для записи в него файла из первоначальной директории
                    try(FileInputStream fis = new FileInputStream(file);
                        FileOutputStream fos = new FileOutputStream(backupFile)){
                        int n;
                        while((n = fis.read()) != -1){
                            fos.write(n);
                        }
                    }

                }
            }
        }

    }
}


