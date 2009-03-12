/**
 * 
 */
package fr.cls.atoll.motu.data;

/**
 * Select parameter (variable) class. This class allows to defined logical expression to select data from a
 * product.
 * 
 * @author $Author: dearith $
 * @version $Revision: 1.1 $ - $Date: 2009-02-20 13:00:25 $
 */
public class SelectData {

    /**
     * Default constructor.
     * 
     */
    public SelectData() {

    }

    /**
     * Logical expression (boolean)to select data. It can contain variable's name, mathematical expression,
     * relational operators, logical operator.
     * 
     * @uml.property name="logicalExpression"
     */
    private String logicalExpression = "";

    /**
     * Getter of the property <tt>expression</tt>.
     * 
     * @return Returns the expression.
     * @uml.property name="logicalExpression"
     */
    public String getLogicalExpression() {
        return this.logicalExpression;
    }

    /**
     * Setter of the property <tt>expression</tt>.
     * 
     * @param logicalExpression The expression to set.
     * @uml.property name="logicalExpression"
     */
    public void setLogicalExpression(String logicalExpression) {
        this.logicalExpression = logicalExpression;
    }

}