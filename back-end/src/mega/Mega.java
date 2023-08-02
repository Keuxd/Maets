package mega;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Mega {
	
	public static String lastOutput;

	public static int run(CommandsEnum command, String... arguments) {
		try {
			final String MEGA_DIR = System.getenv("LOCALAPPDATA") + "/MEGAcmd/";
			ProcessBuilder pb = new ProcessBuilder(MEGA_DIR + "mega-" + command.name() + ".bat");
			
			for(String argument : arguments) {
				pb.command().add(argument);
			}
			
			pb.redirectErrorStream(true);
			
			Process process = pb.start();
			
			System.out.println("Running: mega-" + command.name());
			
			Thread logReader = new Thread(() -> {
				Scanner scanner = new Scanner(process.getInputStream(), "UTF-8");
				while(scanner.hasNextLine()) {
					lastOutput = scanner.nextLine();
					System.out.println(lastOutput);
				}
				scanner.close();
			});
			
			logReader.setName("MegaCMD listener");
			logReader.start();
			
			// Stops main thread until process finish it's execution
			int code = process.waitFor();
			
			// Stops the thread tracking process logs
			logReader.interrupt();
			
			System.out.println("# Command '" + command.name() + "' returned code " + code);
			return code;
		} catch(IOException | InterruptedException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static boolean isInstalled() {
		return new File(MEGA_DIR + "uninst.exe").exists();
	}
}