package atlinject.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.eclipse.core.internal.runtime.PrintStackUtil;

import atlinject.config.LogPrinterConfig;

public class LogPrinter {
	
	private static LogPrinter instance;
	
	private LogPrinter() {}
	
	public static LogPrinter getInstance() {
		if (instance == null)
			instance = new LogPrinter();
		return instance;
	}
	
	public void newLog() {
		File file = new File(LogPrinterConfig.getInstance().getLogPath());
		try {
			boolean result = Files.deleteIfExists(file.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void newLog(boolean verbose, boolean overwrite) {
		File file = new File(LogPrinterConfig.getInstance().getLogPath());
		LogPrinterConfig.getInstance().verbose(verbose);
		LogPrinterConfig.getInstance().overwrite(overwrite);
		if(overwrite) {
			try {
				boolean result = Files.deleteIfExists(file.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void printLog(String text) {
		try(FileWriter fw = new FileWriter(LogPrinterConfig.getInstance().getLogPath(), true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(text);
			} catch (IOException e) {
				//TODO - Manage exception
			    e.printStackTrace();
			}
	}
	
	public void printLogFormatted(String format, String col1, String col2, String col3) {
		try(FileWriter fw = new FileWriter(LogPrinterConfig.getInstance().getLogPath(), true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.printf(format, col1, col2, col3);
			} catch (IOException e) {
				//TODO - Manage exception
			    e.printStackTrace();
			}
	}
	
}
