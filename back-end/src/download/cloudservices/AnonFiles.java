package download.cloudservices;

import java.io.FileNotFoundException;
import java.io.IOException;

import download.CloudStorageDownloader;
import download.SocketConnection;

public class AnonFiles extends CloudStorageDownloader {
	
	private AnonFiles() {}

	public static void downloadFile(String fileId, String localFilePath) throws IOException {
		downloadFromUrl(getAnonDownloadLink("https://anonfiles.com/" + fileId), localFilePath);
	}

	public static boolean isAnonFileAvailable(String fileId) throws IOException {
		try {
			int linkIndex = SocketConnection.getAllLinesFromInputStream(SocketConnection.getURLSource("https://anonfiles.com/" + fileId)).indexOf("https://cdn-");
			
			if(linkIndex == -1) return false;
			else return true;
		} catch(FileNotFoundException e) {
			return false;
		}
	}
	
	private static String getAnonDownloadLink(String anonLink) throws IOException {
		String line = SocketConnection.findLineInInputStreamWhichContains(SocketConnection.getURLSource(anonLink), "https://cdn-");
		int linkIndex = line.indexOf("https://cdn-");
		
		String link = line.substring(linkIndex).split("\"")[0];
		
		return link;
	}
}