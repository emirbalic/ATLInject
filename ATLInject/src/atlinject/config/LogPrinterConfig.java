package atlinject.config;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LogPrinterConfig {
	
	private static LogPrinterConfig instance;
	
	private final String logFolder = "Log";	//Log folder path
	private final String logName = "log";	//Log file name
	private final String extension = "txt"; //Log file extension	
	private boolean verbose = true;			//Log verboseness level	
	private boolean overwrite = true;		//Overwrite existing log file or generate different ones
	
	private final String logPath = logFolder + File.separator + logName + "." + extension;
	private final String logPathNotUnique = logFolder + File.separator + logName + "_" + LocalDateTime.now().toString().replaceAll("[^a-zA-Z0-9]", "") + "." + extension;
	
	private LogPrinterConfig() {};
	
	public static LogPrinterConfig getInstance() {
		if(instance == null)
			instance = new LogPrinterConfig();
		return instance;
	}
	
	public String getLogPath() {
		if (overwrite)
			return this.logPath;
		else
			return this.logPathNotUnique;
	}
	public boolean isVerbose() {return this.verbose;}
	public void verbose(boolean v) {this.verbose = v;}
	public void overwrite(boolean v) {this.overwrite = v;}
}
