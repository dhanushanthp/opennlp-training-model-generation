package core.util;

import java.io.File;

public class FileUtils {
	public static void CreateMultiDirec(String direcPath) {
		File direc = new File(direcPath);
		direc.mkdirs();
	}
}
