package download;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

public abstract class CloudStorageDownloader {
	
	protected CloudStorageDownloader() {}
	
	protected static void downloadFromUrl(String link, String localFilePath) throws IOException {
		URL url = new URL(link);
		long totalFileSize = url.openConnection().getContentLengthLong();
		
		Path localPath = Paths.get(localFilePath);
		Thread logThread = initFileSizeLoggingThread(localPath, totalFileSize);

		try (InputStream in = url.openStream()){
			logThread.start();
			Files.copy(in, localPath, StandardCopyOption.REPLACE_EXISTING);
		} catch(Exception e) {
			System.out.println("Download failed, logging thread will be interrupted");
			e.printStackTrace();
			logThread.interrupt();
		}
	}
	
	// Most cases buffer size will be a multiple of 1024, it's standard value is 8x == 8192
	protected static void downloadFromUrl(String link, String localFilePath, int bufferSize) throws IOException {
		if(bufferSize <= 512) bufferSize = 512;
		URL url = new URL(link);
		SizeScaleManager ssm = new SizeScaleManager(url.openConnection().getContentLengthLong());
		
		try (InputStream in = url.openStream();
				BufferedInputStream bis = new BufferedInputStream(in);
		         
				FileOutputStream fos = new FileOutputStream(localFilePath)) {
				 	byte[] data = new byte[bufferSize];
				 	int count;
				 	
				 	while ((count = bis.read(data, 0, bufferSize)) != -1) {
				 		fos.write(data, 0, count);
				 		ssm.add(count);
				 		System.out.println(ssm);
				 	}
				}
	}
	
	private static Thread initFileSizeLoggingThread(Path localFilePath, long fileSize) {
		return new Thread(() -> {
			try {
				SizeScaleManager ssm = new SizeScaleManager(fileSize);

				while(!ssm.isFinished()) {
					TimeUnit.SECONDS.sleep(1);
					ssm.setBytes(Files.size(localFilePath));
					System.out.println(ssm);
				}
			} catch(InterruptedException | IOException e) {
				e.printStackTrace();
			}
		});
	}
}