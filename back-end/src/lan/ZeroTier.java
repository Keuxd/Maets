package lan;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

import core.Main;

public class ZeroTier {
	
	public static final String INSTALLER_PATH = Main.MAETS_FOLDER_PATH + "Redist/ZeroTier One.msi";
	
	public static void install() throws IOException, InterruptedException {
		Process installer = runMsi("/i");
		printMsiProcess(installer.getInputStream());
	
		int exitCode = installer.waitFor();

		deleteStartMenuShortcut();
		
		System.out.println(exitCode);
	}
	
	public static void uninstall() throws IOException, InterruptedException {
		Process uninstaller = runMsi("/x");
		printMsiProcess(uninstaller.getInputStream());
	
		int exitCode = uninstaller.waitFor();
		
		File remainingFilesFolder = new File(System.getenv("ProgramData") + "/ZeroTier/");
		deleteDir(remainingFilesFolder);

		System.out.println(exitCode);
	}
	
	public static boolean isInstalled() {
		File cli = new File(ZeroTierCLI.PATH);
		return cli.exists();
	}
	
	// Flags: /i to install | /x to uninstall
	private static Process runMsi(String flag) throws IOException {
		File msi = new File(INSTALLER_PATH);
		ProcessBuilder pb = new ProcessBuilder("msiexec", flag, msi.getPath(), "/quiet");
		
		pb.redirectErrorStream(true);
		return pb.start();
	}
	
	private static void printMsiProcess(InputStream msiProcessInputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(msiProcessInputStream));
		String inputLine;
		
        while((inputLine = br.readLine()) != null) {
            System.out.println(inputLine);
        }
        
        br.close();
	}
	
	private static void deleteStartMenuShortcut() {
		String shortcutPath = System.getenv("ProgramData") + "/Microsoft/Windows/Start Menu/Programs/ZeroTier.lnk";
		File shortcut = new File(shortcutPath);
		shortcut.delete();
	}
	
	private static boolean deleteDir(File file) {
		try {
			File[] contents = file.listFiles();
			if(contents != null) {
				for(File f : contents) {
					if(!Files.isSymbolicLink(f.toPath())) {
						deleteDir(f);
					}
				}
			}
			file.delete();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
}