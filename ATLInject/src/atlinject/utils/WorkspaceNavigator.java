package atlinject.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import atlinject.ATLInject;
import atlinject.config.PathConfig;

public class WorkspaceNavigator {
	private static WorkspaceNavigator instance;
	
	private WorkspaceNavigator() {}
	
	public static WorkspaceNavigator getInstance() {
		if(instance == null)
			instance = new WorkspaceNavigator();
		return instance;
	}
	
	public List<CombinedTransformationPath> getTransformationsPaths() {
		
		List<CombinedTransformationPath> list = new ArrayList<CombinedTransformationPath>();
		
		File currentDir = new File(PathConfig.getInstance().getRootFolder());		
		File[] files = currentDir.listFiles();		
		
		List<String> transfoNames = new ArrayList<String>();
						
		for (File file : files) {
			if (file.isDirectory()) {
				transfoNames.add(file.getName());
			} else {
				System.out.println("File " + file.getName() + " in root folder ignored because out of expected path.");
			}
		}	
		
		
		for(String s : transfoNames) {
			
			String transformationName = s;
			
			Path transformationPath = null;
			Path inMMPath = null;
			Path outMMPath = null;
			
			Path tempTPath = Paths.get( PathConfig.getInstance().getRootFolder(), s, PathConfig.getInstance().getTransformationFolder() );
			Path tempInPath = Paths.get( PathConfig.getInstance().getRootFolder(), s, PathConfig.getInstance().getInputMetamodelFolder() );
			Path tempOutPath = Paths.get( PathConfig.getInstance().getRootFolder(), s, PathConfig.getInstance().getOutputMetamodelFolder() );
			
			File[] tDir = tempTPath.toFile().listFiles();
			for (File f : tDir) {
				if (f.isFile()) {
					
					if(f.getName().substring(f.getName().lastIndexOf('.'), f.getName().length()).equals(".atl")) {
						
						//TODO - Start routine ATLInject
						String filename = (String) f.getPath().substring(0, f.getPath().lastIndexOf("."));
						
						//ATLInject.inject(filename + ".atl", filename + ".xmi");

						System.out.println("ATL Transormations files: " + f.getPath());						
						
					} else {
						if(f.getName().substring(f.getName().lastIndexOf('.'), f.getName().length()).equals(".xmi")) {
							
							transformationPath = Paths.get(f.getPath());
							System.out.println("XMI Transormations files: " + f.getPath());		
							
						}					
					}					
				} else {
					System.out.println("Directory " + f.getPath() + " in" + PathConfig.getInstance().getTransformationFolder() + " folder ignored because out of expected path.");
				}
			}
		
			
			File[] inMMDir = tempInPath.toFile().listFiles();
			for (File f : inMMDir) {
				if (f.isFile()) {
					
					if(f.getName().substring(f.getName().lastIndexOf('.'), f.getName().length()).equals(".ecore")) {
						
						//TODO - Do something instead of printing only names
						inMMPath = Paths.get(f.getPath());
						System.out.println("Input Ecore Metamodel Files: " + f.getPath());		
						
					} else {
						System.out.println("File " + f.getPath() + " in folder: " + PathConfig.getInstance().getTransformationFolder() + "; ignored because of unexpected extension.");											
					}					
				} else {
					System.out.println("Directory " + f.getPath() + " in folder: " + PathConfig.getInstance().getTransformationFolder() + "; ignored because out of expected path.");
				}
			}
		
			
			File[] outMMDir = tempOutPath.toFile().listFiles();
			for (File f : outMMDir) {
				if (f.isFile()) {
					
					if(f.getName().substring(f.getName().lastIndexOf('.'), f.getName().length()).equals(".ecore")) {
						
						//TODO - Do something instead of printing only names
						outMMPath = Paths.get(f.getPath());
						System.out.println("Output Ecore Metamodel Files: " + f.getPath());		
						
					} else {
						System.out.println("File " + f.getPath() + " in folder: " + PathConfig.getInstance().getTransformationFolder() + "; ignored because of unexpected extension.");											
					}					
				} else {
					System.out.println("Directory " + f.getPath() + " in folder: " + PathConfig.getInstance().getTransformationFolder() + "; ignored because out of expected path.");
				}
			}
			
			list.add( new CombinedTransformationPath(transformationName, transformationPath, inMMPath, outMMPath) );
			
		}
		
		return list;
	}
}
