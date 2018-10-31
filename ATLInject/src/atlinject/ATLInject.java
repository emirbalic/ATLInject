package atlinject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.m2m.atl.emftvm.compiler.AtlResourceImpl;

public class ATLInject {

	public static void inject (String atlPath, String outputFilePath) {
		
		AtlResourceImpl ri = new AtlResourceImpl();
		ResourceSet rs = new ResourceSetImpl();
		rs.getResources().add(ri);
		try {
			FileInputStream fis = new FileInputStream(new File(atlPath));
			ri.load(fis ,null);
			Resource xmiRes = rs.createResource(URI.createURI(outputFilePath));
			xmiRes.getContents().addAll(ri.getContents());
			xmiRes.save(null);

		} catch (FileNotFoundException e) {
			e.printStackTrace();;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
