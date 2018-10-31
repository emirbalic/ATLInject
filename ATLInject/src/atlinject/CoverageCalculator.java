package atlinject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginContext;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.query.statements.IQueryResult;

import ATL.ATLPackage;
import OCL.NavigationOrAttributeCallExp;
import OCL.OCLPackage;
import OCL.OclModelElement;
import atlinject.config.LogPrinterConfig;
import atlinject.config.WeightConfig;
import atlinject.utils.LogPrinter;
import atlinject.utils.OCLAttributeDerived;

public class CoverageCalculator {
	private static CoverageCalculator instance;

	private double ClassWeigth;
	private double AttributesWeigth;
	private double ReferencesWeight;
	
		
	public static CoverageCalculator getInstance() {
		if(instance == null)
			instance = new CoverageCalculator();
		return instance;
	}
	
	
	private CoverageCalculator() {
		this.setWeights(WeightConfig.getInstance().getClassweigth(), 
						WeightConfig.getInstance().getAttributesweigth(), 
						WeightConfig.getInstance().getReferencesweight());
	}	
	
	private void setWeights(double cw, double aw, double rw){
		this.setClassWeigth(cw);
		this.setAttributesWeigth(aw);
		this.setReferencesWeight(rw);
	}	
	
	public void setClassWeigth(double classWeigth) {ClassWeigth = classWeigth;}
	public void setAttributesWeigth(double attributesWeigth) {AttributesWeigth = attributesWeigth;}
	public void setReferencesWeight(double referencesWeight) {ReferencesWeight = referencesWeight;}
	public double getClassWeigth() {return ClassWeigth;}
	public double getAttributesWeigth() {return AttributesWeigth;}
	public double getReferencesWeight() {return ReferencesWeight;}
	
	
	public double CalculateCov(String transformation, String trgMetamodel) throws URISyntaxException{	
		double cov = 0;		
		double size = 0;

		List<String> oclmodelelements = extractOCLModelElement(transformation);
		
		List<String> mms = new ArrayList<String>();
		mms.add(trgMetamodel);		
		
		List<String> mm_metaclasses = extractMetaclasses(mms);
		
		System.out.println("OclModel elements in the transformation " + transformation + ":");
		System.out.println(oclmodelelements);
		System.out.println("==========================================================================================");
		
		
		if(LogPrinterConfig.getInstance().isVerbose()) {
			LogPrinter.getInstance().printLog("OclModel elements in the transformation " + transformation + ":");
			LogPrinter.getInstance().printLog(oclmodelelements.toString());
			LogPrinter.getInstance().printLog("==========================================================================================");
		}
		
		System.out.println("Metaclasses in the metamodels " + mms.toString() + ":");
		System.out.println(mm_metaclasses);
		System.out.println("==========================================================================================");
		
		
		if(LogPrinterConfig.getInstance().isVerbose()) {
			LogPrinter.getInstance().printLog("Metaclasses in the metamodels " + mms.toString() + ":");
			LogPrinter.getInstance().printLog(mm_metaclasses.toString());
			LogPrinter.getInstance().printLog("==========================================================================================");
		}
		
		System.out.println("Calculating coverage:");
				
		if(LogPrinterConfig.getInstance().isVerbose())
			LogPrinter.getInstance().printLog("Calculating coverage:");
		
		size = size + (mm_metaclasses.size() * this.getClassWeigth());

		for (int i = 0; i < mm_metaclasses.size(); i++) {			
			System.out.println("checking:" + mm_metaclasses.get(i));
			
			
			if(LogPrinterConfig.getInstance().isVerbose())
				LogPrinter.getInstance().printLog("checking:" + mm_metaclasses.get(i));
			
			if (oclmodelelements.contains(mm_metaclasses.get(i)))
				cov = cov + ( 1 * this.getClassWeigth() );
		}		
		System.out.println("==========================================================================================");
		
		if(LogPrinterConfig.getInstance().isVerbose())
			LogPrinter.getInstance().printLog("==========================================================================================");
		
		Map<EClass,List<Object>> mm_attributes = this.extractModelAttributes(mms);
		Map<String,List<String>> oclmodelattr = this.extractOCLRulesAttributes(transformation);
		
		System.out.println("Ocl rules Attributes in the transformation " + transformation + ":");
		System.out.println(oclmodelattr);
		
		
		if(LogPrinterConfig.getInstance().isVerbose()) {
			LogPrinter.getInstance().printLog("Ocl rules Attributes in the transformation " + transformation + ":");
			LogPrinter.getInstance().printLog(oclmodelattr.toString());
		}
			
		for(EClass i : mm_attributes.keySet()) {
			if(oclmodelattr.keySet().contains(i.getName())){
				for(Object j : mm_attributes.get(i)) {
					if(oclmodelattr.get(i.getName()).contains(((ENamedElement) j).getName())) {
						if(j instanceof EAttribute) {
							System.out.println("Comparing attribute " + oclmodelattr.get(i.getName()) + " with " + ((ENamedElement) j).getName());
							
							
							if(LogPrinterConfig.getInstance().isVerbose())
								LogPrinter.getInstance().printLog("Comparing attribute " + oclmodelattr.get(i.getName()) + " with " + ((ENamedElement) j).getName());
							
							cov = cov + ( 1 * this.getAttributesWeigth() );
					} else {
						if(j instanceof EReference) {
							System.out.println("Comparing reference " + oclmodelattr.get(i.getName()) + " with " + ((ENamedElement) j).getName());
							
							
							if(LogPrinterConfig.getInstance().isVerbose())
								LogPrinter.getInstance().printLog("Comparing reference " + oclmodelattr.get(i.getName()) + " with " + ((ENamedElement) j).getName());
							
							cov = cov + ( 1 * this.getReferencesWeight() );
						} else {									
							//MANAGE ERROR TODO
							System.err.println("ERROR not reference or attribute: " + ((ENamedElement) j).getName());
							
							
							if(LogPrinterConfig.getInstance().isVerbose())
								LogPrinter.getInstance().printLog("ERROR not reference or attribute: " + ((ENamedElement) j).getName());
							
						}
					}
					}
				}
			} else {
				if (i.isAbstract()){
					System.out.println("Abstract class ignored: " + i.getName());
					
					
					if(LogPrinterConfig.getInstance().isVerbose())
						LogPrinter.getInstance().printLog("Abstract class ignored: " + i.getName());
					
				} else {
					//TODO - Manage Exception 
					System.out.println("Class not present in the transformation: " + i.getName());
					
					
					if(LogPrinterConfig.getInstance().isVerbose())
						LogPrinter.getInstance().printLog("Class not present in the transformation: " + i.getName());
					
				}
			}
		}		
		
		System.out.println("==========================================================================================");
		System.out.println("Attributes in the metamodels " + trgMetamodel + ":");
		
		
		if(LogPrinterConfig.getInstance().isVerbose()) {
			LogPrinter.getInstance().printLog("==========================================================================================");
			LogPrinter.getInstance().printLog("Attributes in the metamodels " + trgMetamodel + ":");
		}
		
		for(EClass i : mm_attributes.keySet()) {
			System.out.println("checking class: " + i.getName());
			
			
			if(LogPrinterConfig.getInstance().isVerbose())
				LogPrinter.getInstance().printLog("checking class: " + i.getName());
			
			for(Object j : mm_attributes.get(i)) {
				if(j instanceof EAttribute) {
					System.out.println("|--checking attribute: " + ((EAttribute) j).getName());
					
					
					if(LogPrinterConfig.getInstance().isVerbose())
						LogPrinter.getInstance().printLog("|--checking attribute: " + ((EAttribute) j).getName());
					
					size = size + ( 1 * this.getAttributesWeigth() );
				} else {
					if(j instanceof EReference) {
						System.out.println("|--checking reference: " + ((EReference) j).getName());
						
						
						if(LogPrinterConfig.getInstance().isVerbose())
							LogPrinter.getInstance().printLog("|--checking reference: " + ((EReference) j).getName());
						
						size = size + ( 1 * this.getReferencesWeight() );
					} else {
						System.err.println("--None of them: " + ((ENamedElement) j).getName());
						
						
						if(LogPrinterConfig.getInstance().isVerbose())
							LogPrinter.getInstance().printLog("--None of them: " + ((ENamedElement) j).getName());
					}
				}
			}
		}		
		System.out.println("==========================================================================================");
		System.out.println("Calculating coverage:");
		
		
		if(LogPrinterConfig.getInstance().isVerbose()) {
			LogPrinter.getInstance().printLog("==========================================================================================");
			LogPrinter.getInstance().printLog("Calculating coverage:");
		}
		
		return (cov / size);
	}
	
		
	public List<String> extractMetaclasses(List<String> mms) {
		Object[] arr = null;
		List<String> list = new ArrayList<String>();

		for (String mm : mms) {
			Resource myResourcemm = this.RegisterResource(mm, "ecore");

			IQueryResult results = QueryBuilder.getInstance().executeQuery(myResourcemm,
					QueryBuilder.getInstance().buildCondition(EcorePackage.Literals.ECLASS));

			arr = results.toArray();
			for (int i = 0; i < arr.length; i++) {
				EClass me = (EClass) arr[i];
				list.add(me.getName());
			}
		}
		return list;
	}
	
	
	public List<String> extractOCLModelElement(String Tmodel){
		// calculate coverage
		Object[] arr = null;
		List<String> list = new ArrayList<String>();
		ATLPackage.eINSTANCE.eClass();		
		Resource myResource = this.RegisterResource(Tmodel, "xmi");
		
		IQueryResult results = QueryBuilder.getInstance().executeQuery(myResource, 
				QueryBuilder.getInstance().buildCondition(OCLPackage.Literals.OCL_MODEL_ELEMENT));
		
		arr = results.toArray();
		for (int i = 0; i < arr.length; i++) {
			OclModelElement me = (OclModelElement) arr[i];
			list.add(me.getName());
		}		
		return list;
	}
	

	public Map<String,List<String>> extractOCLRulesAttributes(String Tmodel) throws URISyntaxException {
		
		List<OCLAttributeDerived> oadList = new ArrayList<OCLAttributeDerived>();
		Map<String,List<String>> oadMap = new HashMap<String,List<String>>();
		List<String> tempAttr = new ArrayList<String>();
		
		String currentModelElement = null;
		
		File file = new File(URI.createURI(Tmodel).toFileString());
		oadList = XMIParser.getInstance().parse(file);
		
		for(OCLAttributeDerived oad : oadList) {			
			if(oad.getName().equals("atl:MatchedRule")) {				
				System.out.println("Accessing matched rule: " + oad.getValue());
				
				if(LogPrinterConfig.getInstance().isVerbose())
					LogPrinter.getInstance().printLog("Accessing matched rule: " + oad.getValue());
				
			} else {				
				if(oad.getName().equals("ocl:OclModelElement")) {					
					currentModelElement = oad.getValue();
					oadMap.put(currentModelElement, new ArrayList<String>() );
					
					System.out.println("Accessing model element: " + currentModelElement);
					
					if(LogPrinterConfig.getInstance().isVerbose())
						LogPrinter.getInstance().printLog("Accessing model element: " + currentModelElement);
					
				} else {
					if(oad.getName().equals("ocl:NavigationOrAttributeCallExp")) {						
						if(oadMap.containsKey(currentModelElement)) {
							if(!oadMap.containsValue(oad.getValue())) {
								oadMap.get(currentModelElement).add(oad.getValue());
							}
						}						
					} else {
						//TODO - Manage Exception
						System.err.println("ERROR");
					}
				}
			}
		}	
				
		return oadMap;
	}
	
	
	public Map<EClass, List<Object>> extractModelAttributes(List<String> mms) {
		Object[] arr = null;
		List<String> list = new ArrayList<String>();
		
		Map<EClass,List<Object>> map = new HashMap<EClass, List<Object>>();
		List<EClass> supertypes = new ArrayList<EClass>();

		for (String mm : mms) {			
			Resource myResourcemm = this.RegisterResource(mm, "ecore");
			IQueryResult results = QueryBuilder.getInstance().executeQuery(myResourcemm, 
					QueryBuilder.getInstance().buildCondition(EcorePackage.Literals.ECLASS));
			
			arr = results.toArray();
			for (int i = 0; i < arr.length; i++) {EClass me = (EClass) arr[i];}	
			
			for (int i=0;i<arr.length;i++) {
				EClass me = (EClass)arr[i];
				for(EClass st : me.getESuperTypes()) {
					if(supertypes.contains(st) == false)
						supertypes.add(st);
				}
			}	
			
			for(EClass st : supertypes) {
				List<Object> tmp = new ArrayList<Object>();
				for(EAttribute ea : st.getEAttributes()){
					tmp.add(ea);
				}
				map.put(st, tmp);				
				for (int j=0;j<arr.length;j++) {
					EClass me = (EClass) arr[j];					
					if(st.isSuperTypeOf(me) && (st != me)) {						
						List<Object> tmpAttr = new ArrayList<Object>();
						if(map.containsKey(me)) {							
							for(EAttribute ea : st.getEAttributes()){tmpAttr.add(ea);}
							for(EReference ea : st.getEReferences()){tmpAttr.add(ea);}
							map.get(me).addAll(tmpAttr);							
						} else {							
							for(EAttribute ea : st.getEAttributes()){tmpAttr.add(ea);}
							for(EReference ea : st.getEReferences()){tmpAttr.add(ea);}
							for(EAttribute ea : me.getEAttributes()){tmpAttr.add(ea);}
							for(EReference ea : me.getEReferences()){tmpAttr.add(ea);}							
							map.put(me, tmpAttr);							
						}						
					}
				}
			}			
		}		
		return map;
	}
	
	
	public Resource RegisterResource( String res, String ext ) {
		// register XMI resource factory
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put(ext, new XMIResourceFactoryImpl());
		// obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();
		// get the resource
		Resource myResource = resSet.getResource(URI.createURI(res), true);		
		return myResource;
	}
	
}
