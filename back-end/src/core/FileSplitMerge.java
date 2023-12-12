package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSplitMerge {
	
	// It'll split the file 'even size' in 100 files
	public static void splitFileInPercentage(File inputFile) throws IOException {
		int sizeEven = (int) inputFile.length() / 99;
		splitFile(inputFile, sizeEven);
	}
	
	// Default chunkSize is 1MB (1024 * 1024)
    public static void splitFile(File inputFile, int chunkSizeBytes) throws IOException {
        FileInputStream fis = new FileInputStream(inputFile);
        byte[] buffer = new byte[chunkSizeBytes];

        int bytesRead;
        int fileNumber = 1;
        while ((bytesRead = fis.read(buffer)) > 0) {
            String chunkFileName = inputFile.getPath() + ".db" + fileNumber;
            FileOutputStream fos = new FileOutputStream(chunkFileName);
            fos.write(buffer, 0, bytesRead);
            fos.close();
            fileNumber++;
            System.out.println(fileNumber);
        }
        fis.close();
    }
    
    public static void mergeFilesInPercentage(File fileOne, File outputFile) throws IOException {
    	mergeFiles(fileOne, outputFile, 100);
    }
    
    // It'll use the base number model and its directory
    public static void mergeFiles(File fileOne, File outputFile, int filesAmount) throws IOException {
    	FileOutputStream fos = new FileOutputStream(outputFile);
    	
    	for(int i = 0; i < filesAmount; i++) {
    		String currentFilePath = fileOne.getPath().split("db")[0] + "db" + (i + 1);
    		File currentFile = new File(currentFilePath);
    		FileInputStream fis = new FileInputStream(currentFile);
    		System.out.println(i+1);
    		byte[] buffer = new byte[(int) currentFile.length()];
    		fis.read(buffer);
    		fos.write(buffer);
    		fis.close();
    	}
    	
    	fos.close();
    }

    public static void mergeFiles(File[] files, File outputFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(outputFile);
        for (File file : files) {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fos.write(buffer);
            fis.close();
        }
        fos.close();
    }
}
