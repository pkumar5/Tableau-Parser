# Tableau-Parser V2.0 updated July 2018

This JAVA application extracts the Calculated Fields from a Tableau TWB (Tableau Workbook File) file into a text file.
The text output file is saved in the same directory as the TWB file.

To run the application from the command line, go to the dist folder and type the following:
java -jar "TWB_Parser.jar"

To run the java excutiable application double click "TWB_Parser.jar"

Note: A TWBX file is a ZIP file, if you have to recover a TWB file from within a TWBX rename the <file>.twbx file to <file>.zip to explore its contents. 

Changes from V1.0: 
1. Calculated Fields that contained Tableau "Calculation_<number>" now contain the Calculation Name.
2. Datasources that Contained "sqlproxy.<number>" now contain the Datasource Name.
