package lan;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ZeroTierCLI {

	protected static String PATH = System.getenv("ProgramData") + "/ZeroTier/One/zerotier-one_x64.exe";

	public static String status() {
		try {
			Process cli = runCli("status");
			return getCliOutput(cli.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
			return "null";
		}
	}
	
	public static String join(String networkId) {
		try {
			Process cli = runCli("join", networkId);
			return getCliOutput(cli.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
			return "null";
		}
	}
	
	public static String leave(String networkId) {
		try {
			Process cli = runCli("leave", networkId);
			return getCliOutput(cli.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
			return "null";
		}
	}
	
	public static String listNetworks() {
		try {
			Process cli = runCli("listnetworks");
			return getCliOutput(cli.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
			return "null";
		}
	}
	
	public static String peers() {
		try {
			Process cli = runCli("peers");
			return getCliOutput(cli.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
			return "null";
		}
	}
	
	private static Process runCli(String command, String... argument) throws IOException {
		File cli = new File(PATH);
		ProcessBuilder pb = new ProcessBuilder(cli.getPath(), "-q", command, (argument.length == 0 ? "" : argument[0]));
		
		return pb.start();
	}
	
	private static String getCliOutput(InputStream cliProcessInputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(cliProcessInputStream));
		String inputLine;
		
		StringBuilder sb = new StringBuilder();
		while((inputLine = br.readLine()) != null) {
			sb.append(inputLine);
			sb.append(System.lineSeparator());
		}
		
		br.close();
		
		return sb.toString();
	}
}