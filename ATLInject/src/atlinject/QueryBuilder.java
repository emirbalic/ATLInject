package atlinject;

import java.io.FileOutputStream;
import java.util.ArrayList;
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
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EStructuralFeatureValueGetter;
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

public class QueryBuilder {
	
	private static QueryBuilder instance;
	private BooleanOCLCondition condition;
	private Resource myResource;
	
	private QueryBuilder() {}
	
	public static QueryBuilder getInstance() {
		if(instance == null)
			instance = new QueryBuilder();
		return instance;
	}
	
	public BooleanOCLCondition buildCondition ( Object context ) {
		OCL ocl = org.eclipse.ocl.ecore.OCL.newInstance();
		
		try {
			condition = new BooleanOCLCondition(ocl.getEnvironment(), "true", context);
		} 
		catch (ParserException e) {
			e.printStackTrace();
		}
		
		return condition;
	}
	
	public IQueryResult executeQuery( Resource myRes, BooleanOCLCondition cond ) {
		this.setMyResource(myRes);
		this.setCondition(cond);
		IQueryResult result;
		
		SELECT statement = new SELECT( SELECT.UNBOUNDED, false,
				new FROM(this.myResource.getContents()), 
				new WHERE(this.getCondition()));
		
		result = statement.execute();
		return result;
	}
	
	public BooleanOCLCondition getCondition() {
		return condition;
	}
	
	public void setCondition(BooleanOCLCondition condition) {
		this.condition = condition;
	}
	
	public Resource getMyResource() {
		return myResource;
	}
	
	public void setMyResource(Resource myResource) {
		this.myResource = myResource;
	}
	
	
	
}
