package config;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class ConfigUtils {
	
	protected static FileChannel initializeFile(String folderPath, String fileNameWithExtension) throws IOException {
		File configFile = new File(folderPath + "/" + fileNameWithExtension);
		configFile.getParentFile().mkdir();
		
		configFile.createNewFile();
		FileChannel channel = createFileChannel(configFile);
		
		System.out.println("\"" + configFile.getPath() + "\" initialized.");
		
		return channel;
	}
	
	protected static void writeToFileChannel(String stuffToWrite, FileChannel channel) throws IOException {
		byte[] byteArray = stuffToWrite.getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(byteArray);
		channel.write(buffer, 0);
	}
	
	protected static String getContentInChannelAsString(FileChannel channel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
		channel.read(buffer, 0);
		return new String(buffer.array());
	}
	
	protected static JsonObject getContentInChannelAsJson(FileChannel channel) throws IOException {
		String content = getContentInChannelAsString(channel);
		JsonElement json = JsonParser.parseString(content);
		return json.getAsJsonObject();
	}
	
	private static FileChannel createFileChannel(File configFile) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(configFile.getPath(), "rw");
		return raf.getChannel();
	}
}
