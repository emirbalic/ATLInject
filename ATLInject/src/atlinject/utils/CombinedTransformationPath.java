package atlinject.utils;

import java.nio.file.Path;

public class CombinedTransformationPath {	
	
	private String transformationName;
	private Path transformationPath;
	private Path inMMPath;
	private Path outMMPath;
	
	public CombinedTransformationPath(String n, Path tP, Path inP, Path outP) {
		this.setTransformationName(n);
		this.setTransformationPath(tP);
		this.setInMMPath(inP);
		this.setOutMMPath(outP);
	}
	
	public String getTransformationName() {return transformationName;}
	public Path getTransformationPath() {return transformationPath;}
	public Path getInMMPath() {return inMMPath;}
	public Path getOutMMPath() {return outMMPath;}	
	private void setTransformationName(String transformationName) {this.transformationName = transformationName;}
	private void setTransformationPath(Path transformationPath) {this.transformationPath = transformationPath;}
	private void setInMMPath(Path inMMPath) {this.inMMPath = inMMPath;}
	private void setOutMMPath(Path outMMPath) {this.outMMPath = outMMPath;}
	
}
