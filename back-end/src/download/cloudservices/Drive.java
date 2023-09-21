package download.cloudservices;

import java.io.IOException;
import java.net.URL;

import download.CloudStorageDownloader;

public class Drive extends CloudStorageDownloader {
	
	private Drive() {}

	public static void downloadFile(String fileId, String localFilePath) throws IOException {
//		downloadFromUrl("https://drive.google.com/uc?export=download&id=" + fileId + "&confirm=t", localFilePath);
		downloadFromUrl("https://drive.google.com/uc?export=download&confirm=CfLW&id=" + fileId, localFilePath);
//		downloadFromUrl("https://drive.google.com/file/d/" + fileId + "/view", localFilePath);
	}

	public static boolean isFileAvailable(String fileId) throws IOException {
    	URL url = new URL("https://drive.google.com/uc?export=download&id=" + fileId + "&confirm=t");
    	long fileSize = url.openConnection().getContentLengthLong();
    	
    	if(fileSize == -1) return false;
    	else return true;
	}
}