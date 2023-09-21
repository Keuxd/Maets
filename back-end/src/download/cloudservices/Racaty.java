package download.cloudservices;

import java.io.IOException;
import java.net.CookiePolicy;

import download.CloudStorageDownloader;
import download.SocketConnection;

public class Racaty extends CloudStorageDownloader {

	private Racaty() {}
	
	public static void downloadFile(String fileId, String localFilePath) throws IOException {
		downloadFromUrl(getRacatyDownloadLink("https://racaty.net/" + fileId, fileId), localFilePath);
	}
	
	private static String getRacatyDownloadLink(String racatyLink, String fileId) throws IOException {
		String[] racatyFormParams = {"op", "id", "rand", "referer", "method_free", "method_premium"};
		String[] racatyFormValues = {"download2", fileId, "", "https://thenewscasts.com/", "", ""};
		
		String line = SocketConnection.findLineInInputStreamWhichContains(SocketConnection.sendHttpPostRequest(racatyLink, racatyFormParams, racatyFormValues, CookiePolicy.ACCEPT_ALL), "https://srv");

    	String downloadLink = line.substring(line.indexOf("https://srv")).split("\"")[0];
    	System.out.println("Download Link -> " + downloadLink + "\n\n");
    	return downloadLink;
	}
}