package core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {
	
	private ZipExtractor() {}
	
	public static void extract(String zipFilePath, String dirFilePath) throws IOException {
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		
		while(entry != null) {
			String filePath = dirFilePath + File.separator + entry.getName();
			if(!entry.isDirectory()) {
				extractZipFile(zipIn, filePath);
			} else {
				new File(filePath).mkdirs();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}
	
	private static void extractZipFile(ZipInputStream zipIn, String filePath) throws IOException {
		// Default buffer size is 4096(4x) however 16384(16x) worked pretty well in tests
		final int BUFFER_SIZE = 1024 * 16;
		
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		
		int read = 0;
		while((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
}
