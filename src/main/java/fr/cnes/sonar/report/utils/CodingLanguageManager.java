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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.cnes.sonar.report.providers.AbstractDataProvider;

/**
 * Public class which contains language definition
 */
public final class CodingLanguageManager {

    /** Name for properties' file about language. */
    public static final String LANGUAGE_PROPERTIES = "language.properties";
    
    /** Logger for StringManager. */
    private static final Logger LOGGER = Logger.getLogger(CodingLanguageManager.class.getCanonicalName());
    
    /** String separator in file */
	private static final String SEPARATOR = ",";
    
    /** Contain all the properties related to the report. */
    private static Properties properties;

    /** List of language */
    private static List<CodingLanguage> languages = new ArrayList<CodingLanguage>();
    
    /** Map of language corresponding to tools */
    private static Map<String,CodingLanguage> languageTools = new HashMap<String,CodingLanguage>();
  

	/** Unique instance of this class (singleton). */
    private static CodingLanguageManager ourInstance = null;

    
    /**
     * Charge the properties
     * @param path String path to file properties 
     */
    public static void chargeProperties(String path) {
        // store properties
        properties = new Properties();
        // read the file
        InputStream input = null;

        final ClassLoader classLoader = AbstractDataProvider.class.getClassLoader();

        try {
            // load properties file as a stream
        	if ( ! path.equals(StringManager.EMPTY)) {
        		
        		input =  new FileInputStream(path);
        	}
        	else{
            	// if the given properties file is not found, charge the default one
            	input = classLoader.getResourceAsStream(CodingLanguageManager.LANGUAGE_PROPERTIES);
            }
            if(input!=null) {
                // load properties from the stream in an adapted structure
                properties.load(input);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if(input!=null) {
                try {
                    // close the stream if necessary (not null)
                    input.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }

    }

    /**
     * Private constructor to singletonize the class.
     */
    private CodingLanguageManager() {
    	//build the list of language
    	if (properties == null) {
    		chargeProperties(StringManager.EMPTY);
    	}
    	String languageList = properties.getProperty("language.list");
    	
    	//for all language
    	String[] languageArray = languageList.split(SEPARATOR);
    	String placeholder;
    	String label;
    	String toolList;
    	String[] toolArray;
    	String ruleTypeList;
    	CodingLanguage codingLanguage;
    	CodingLanguageTool codingLanguageTool;
    	for ( String language : languageArray ) {
    		//System.out.println( "%%Manager (" + language +")");
    		//get plaholder,tools and label
    		placeholder = properties.getProperty("language."+language+".placeholder");
    		label = properties.getProperty("language."+language+".label");
    		toolList = properties.getProperty("language."+language+".tools");
    		//build coding language
    		codingLanguage = new CodingLanguage();
    		codingLanguage.setName(language);
    		codingLanguage.setLabel(label);
    		codingLanguage.setPlaceHolder(placeholder);
    		//get tools
    		toolArray = toolList.split(SEPARATOR);
    		for ( String tool : toolArray ) {
        		//System.out.println( "%%Manager (" + tool +")");
        		//get placeholder,ruletypes and label
        		placeholder = properties.getProperty("language."+language+"."+tool+".placeholder");
        		label = properties.getProperty("language."+language+"."+tool+".label");
        		ruleTypeList = properties.getProperty("language."+language+"."+tool+".ruletypes");
        		//build coding language
        		codingLanguageTool = new CodingLanguageTool();
        		codingLanguageTool.setName(tool);
        		codingLanguageTool.setLabel(label);
        		codingLanguageTool.setPlaceHolder(placeholder);
        		//get rule types
        		for ( String ruleType : ruleTypeList.split(SEPARATOR)) {
        			codingLanguageTool.addRuleType(ruleType);
        			languageTools.put(ruleType,codingLanguage);
        		}
        		
        		codingLanguage.addTool(codingLanguageTool);
        	}
    		languages.add(codingLanguage);
    	}
    }

    /**
     * Get the singleton
     *
     * @return unique instance of StringManager
     */
    public static synchronized CodingLanguageManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new CodingLanguageManager();
        }
        return ourInstance;
    }

    /**
     * Returne the language list
     * @return List of CodingLanguage
     */
    public List<CodingLanguage> getLanguages() {
  		return languages;
  	}
 
    /**
     * Return the language list of a tool
     * @param tool String : language name
     * @return CodingLanguag language 
     */
    public CodingLanguage getLanguage(String tool) {
  		return languageTools.get(tool);
  	}

}
