/**
 */
package OCL;

import ATL.LocatedElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link OCL.VariableDeclaration#getId <em>Id</em>}</li>
 *   <li>{@link OCL.VariableDeclaration#getVarName <em>Var Name</em>}</li>
 *   <li>{@link OCL.VariableDeclaration#getType <em>Type</em>}</li>
 *   <li>{@link OCL.VariableDeclaration#getInitExpression <em>Init Expression</em>}</li>
 *   <li>{@link OCL.VariableDeclaration#getLetExp <em>Let Exp</em>}</li>
 *   <li>{@link OCL.VariableDeclaration#getBaseExp <em>Base Exp</em>}</li>
 *   <li>{@link OCL.VariableDeclaration#getVariableExp <em>Variable Exp</em>}</li>
 * </ul>
 *
 * @see OCL.OCLPackage#getVariableDeclaration()
 * @model
 * @generated
 */
public interface VariableDeclaration extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see OCL.OCLPackage#getVariableDeclaration_Id()
	 * @model unique="false" dataType="PrimitiveTypes.String" ordered="false"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link OCL.VariableDeclaration#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Var Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Var Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var Name</em>' attribute.
	 * @see #setVarName(String)
	 * @see OCL.OCLPackage#getVariableDeclaration_VarName()
	 * @model unique="false" dataType="PrimitiveTypes.String" required="true" ordered="false"
	 * @generated
	 */
	String getVarName();

	/**
	 * Sets the value of the '{@link OCL.VariableDeclaration#getVarName <em>Var Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Var Name</em>' attribute.
	 * @see #getVarName()
	 * @generated
	 */
	void setVarName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link OCL.OclType#getVariableDeclaration <em>Variable Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(OclType)
	 * @see OCL.OCLPackage#getVariableDeclaration_Type()
	 * @see OCL.OclType#getVariableDeclaration
	 * @model opposite="variableDeclaration" containment="true" ordered="false"
	 * @generated
	 */
	OclType getType();

	/**
	 * Sets the value of the '{@link OCL.VariableDeclaration#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(OclType value);

	/**
	 * Returns the value of the '<em><b>Init Expression</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link OCL.OclExpression#getInitializedVariable <em>Initialized Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Expression</em>' containment reference.
	 * @see #setInitExpression(OclExpression)
	 * @see OCL.OCLPackage#getVariableDeclaration_InitExpression()
	 * @see OCL.OclExpression#getInitializedVariable
	 * @model opposite="initializedVariable" containment="true" ordered="false"
	 * @generated
	 */
	OclExpression getInitExpression();

	/**
	 * Sets the value of the '{@link OCL.VariableDeclaration#getInitExpression <em>Init Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Init Expression</em>' containment reference.
	 * @see #getInitExpression()
	 * @generated
	 */
	void setInitExpression(OclExpression value);

	/**
	 * Returns the value of the '<em><b>Let Exp</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link OCL.LetExp#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Let Exp</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Let Exp</em>' container reference.
	 * @see #setLetExp(LetExp)
	 * @see OCL.OCLPackage#getVariableDeclaration_LetExp()
	 * @see OCL.LetExp#getVariable
	 * @model opposite="variable" transient="false" ordered="false"
	 * @generated
	 */
	LetExp getLetExp();

	/**
	 * Sets the value of the '{@link OCL.VariableDeclaration#getLetExp <em>Let Exp</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Let Exp</em>' container reference.
	 * @see #getLetExp()
	 * @generated
	 */
	void setLetExp(LetExp value);

	/**
	 * Returns the value of the '<em><b>Base Exp</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link OCL.IterateExp#getResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Exp</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Exp</em>' container reference.
	 * @see #setBaseExp(IterateExp)
	 * @see OCL.OCLPackage#getVariableDeclaration_BaseExp()
	 * @see OCL.IterateExp#getResult
	 * @model opposite="result" transient="false" ordered="false"
	 * @generated
	 */
	IterateExp getBaseExp();

	/**
	 * Sets the value of the '{@link OCL.VariableDeclaration#getBaseExp <em>Base Exp</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Exp</em>' container reference.
	 * @see #getBaseExp()
	 * @generated
	 */
	void setBaseExp(IterateExp value);

	/**
	 * Returns the value of the '<em><b>Variable Exp</b></em>' reference list.
	 * The list contents are of type {@link OCL.VariableExp}.
	 * It is bidirectional and its opposite is '{@link OCL.VariableExp#getReferredVariable <em>Referred Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable Exp</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable Exp</em>' reference list.
	 * @see OCL.OCLPackage#getVariableDeclaration_VariableExp()
	 * @see OCL.VariableExp#getReferredVariable
	 * @model opposite="referredVariable" ordered="false"
	 * @generated
	 */
	EList<VariableExp> getVariableExp();

} // VariableDeclaration
