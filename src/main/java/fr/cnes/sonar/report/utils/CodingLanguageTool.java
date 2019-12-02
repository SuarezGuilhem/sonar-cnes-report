/*
 * This file is part of cnesreport.
 *
 * cnesreport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * cnesreport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with cnesreport.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.cnes.sonar.report.utils;

import java.util.ArrayList;
import java.util.List;

/**
* Public class which contains a coding language tool definition
*/
public class CodingLanguageTool {
    /** Language tool 's name*/
    private String name;
    /** Language's placeholder */
    private String placeHolder;
    /** Language's rule types*/
    private List<String> ruleTypes;
    /** Language's label*/
    private String label;

    /**
     * Default constructor
     */
    public CodingLanguageTool() {
        this.placeHolder = "";
        this.name = "";
        this.label = "";
        this.ruleTypes = new ArrayList<String>();
    }

    /**
     * Language's placeholder
     * @return a string
     */
    public String getPlaceHolder() {
        return placeHolder;
    }

    /**
     * Set language's placeholder
     * @param placeholder value to set
     */
    public void setPlaceHolder(final String placeholder) {
        this.placeHolder = placeholder;
    }

    /**
     * Language's name
     * @return a String
     */
    public String getName() {
        return name;
    }

    /**
     * Set language's name
     * @param pName value to set
     */
    public void setName(final String pName) {
        this.name = pName;
    }
    
    /**
     * Language's label
     * @return a String
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set language's label
     * @param label value to set
     */
    public void setLabel(final String label) {
        this.label = label;
    }
    
    /**
     * Language's rule types
     * @return a list of String
     */
    public List<String> getRuleTypes() {
        return this.ruleTypes;
    }
    
    /**
     * Add language's rule type
     * @param type  value to set
     */
    public void addRuleType(final String type) {
        this.ruleTypes.add(type);
    }
    
}
