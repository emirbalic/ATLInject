package atlinject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.event.ObjectChangeListener;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.ocl.conditions.BooleanOCLCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.m2m.atl.engine.compiler.atl2006.Atl2006Compiler;
import org.eclipse.m2m.atl.engine.parser.AtlParser;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;

import ATL.ATLPackage;
import ATL.MatchedRule;
import OCL.NavigationOrAttributeCallExp;
import OCL.OCLPackage;
import OCL.OclModelElement;
import OCL.impl.AttributeImpl;
import atlinject.config.LogPrinterConfig;
import atlinject.config.PathConfig;
import atlinject.utils.CombinedTransformationPath;
import atlinject.utils.LogPrinter;
import atlinject.utils.WorkspaceNavigator;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		
		List<CombinedTransformationPath> list = new ArrayList<CombinedTransformationPath>();
		Map<String,Double> output = new HashMap<String, Double>();
		Map<String,Double> output2 = new HashMap<String, Double>();
		
		list = WorkspaceNavigator.getInstance().getTransformationsPaths();
		
		boolean verbose = false, overwrite = true;
		LogPrinter.getInstance().newLog(verbose, overwrite);
		
		for(CombinedTransformationPath ctp : list) {
			System.out.println("===================================");
			System.out.println("Checking transformation: " + ctp.getTransformationName());
			System.out.println("Transformation path: " + ctp.getTransformationPath().toString());
			System.out.println("Input metamodel path: " + ctp.getInMMPath().toString());
			System.out.println("Output metamodel path: " + ctp.getOutMMPath().toString());
			System.out.println("===================================");
			double covIn = CoverageCalculator.getInstance().CalculateCov(ctp.getTransformationPath().toString(),ctp.getInMMPath().toString());
			System.out.println("===================================");
			System.out.println("Coverage is:" + covIn);
			System.out.println("===================================");
			System.out.println("\n");
			
			/*---------------------------------------------Print-log-----------------------------------------------*/
			
			if(LogPrinterConfig.getInstance().isVerbose()) {
				LogPrinter.getInstance().printLog("===================================");
				LogPrinter.getInstance().printLog("Checking transformation: " + ctp.getTransformationName());
				LogPrinter.getInstance().printLog("Transformation path: " + ctp.getTransformationPath().toString());
				LogPrinter.getInstance().printLog("Input metamodel path: " + ctp.getInMMPath().toString());
				LogPrinter.getInstance().printLog("Output metamodel path: " + ctp.getOutMMPath().toString());
				LogPrinter.getInstance().printLog("===================================");
				LogPrinter.getInstance().printLog("Coverage is:" + covIn);
				LogPrinter.getInstance().printLog("===================================");
				LogPrinter.getInstance().printLog("\n");
			}
			/*-----------------------------------------------------------------------------------------------------*/
			
			output.put(ctp.getTransformationName(), covIn);
		}
		
		
		for(CombinedTransformationPath ctp : list) {
			System.out.println("===================================");
			System.out.println("Checking transformation: " + ctp.getTransformationName());
			System.out.println("Transformation path: " + ctp.getTransformationPath().toString());
			System.out.println("Input metamodel path: " + ctp.getInMMPath().toString());
			System.out.println("Output metamodel path: " + ctp.getOutMMPath().toString());
			System.out.println("===================================");
			double covOut = CoverageCalculator.getInstance().CalculateCov(ctp.getTransformationPath().toString(),ctp.getOutMMPath().toString());
			System.out.println("===================================");
			System.out.println("Coverage is:" + covOut);
			System.out.println("===================================");
			System.out.println("\n");
			
			/*---------------------------------------------Print-log-----------------------------------------------*/
			
			if(LogPrinterConfig.getInstance().isVerbose()) {
				LogPrinter.getInstance().printLog("===================================");
				LogPrinter.getInstance().printLog("Checking transformation: " + ctp.getTransformationName());
				LogPrinter.getInstance().printLog("Transformation path: " + ctp.getTransformationPath().toString());
				LogPrinter.getInstance().printLog("Input metamodel path: " + ctp.getInMMPath().toString());
				LogPrinter.getInstance().printLog("Output metamodel path: " + ctp.getOutMMPath().toString());
				LogPrinter.getInstance().printLog("===================================");
				LogPrinter.getInstance().printLog("Coverage is:" + covOut);
				LogPrinter.getInstance().printLog("===================================");
				LogPrinter.getInstance().printLog("\n");
			}
			/*-----------------------------------------------------------------------------------------------------*/
			
			output2.put(ctp.getTransformationName(), covOut);
		}
		
		
		if(LogPrinterConfig.getInstance().isVerbose())
			System.out.println("\n");
		
		String equals = "";
		for(int i=0; i<180; i++) {
			equals = equals + "=";
		}
		System.out.println("Number of transformation inspected: " + output.size() + "\n");
		System.out.println(equals);
		System.out.println("TRANFORMATIONS COVERAGE:");

		String format = "%-60s %-40s %-40s\n";
		for(String transf : output.keySet()) {
			System.out.printf(format, "\t-Transformation: " + transf + ";", "\t (Tij/MMi) coverage: " + output.get(transf) , "\t (Tij/MMj) coverage: " + output2.get(transf));
		}		
		System.out.println(equals);
		
		/*---------------------------------------------Print-log-----------------------------------------------*/
		LogPrinter.getInstance().printLog("\n");
		LogPrinter.getInstance().printLog("Number of transformation inspected: " + output.size() + "\n");
		LogPrinter.getInstance().printLog(equals);
		LogPrinter.getInstance().printLog("\tTRANFORMATIONS COVERAGE:");		
		for(String transf : output.keySet()) {
			LogPrinter.getInstance().printLogFormatted(format, "\t-Transformation: " + transf + ";", "\t (Tij/MMi) coverage: " + output.get(transf) , "\t (Tij/MMj) coverage: " + output2.get(transf));
		}		
		LogPrinter.getInstance().printLog(equals);
		/*-----------------------------------------------------------------------------------------------------*/

		
	}

}
