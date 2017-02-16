package com.sertec.controllers;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.springframework.web.context.annotation.SessionScope;

import com.sertec.domain.DatoHorario;

/**
 *
 * @author pablo
 */
@SessionScope
@ManagedBean
@Named("suberArchivoController")
public class SubeArchivoController {
    
	private UploadedFile file;
	private List <DatoHorario> datos;
	
	public String upload() {
		try {
			if(file != null) {
	            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
	            FacesContext.getCurrentInstance().addMessage(null, message);
	            
	            //readFile((FileInputStream) file.getInputstream());
	            readXlsxFile((FileInputStream) file.getInputstream());
	        } else {
	        	System.out.println("Problema con le archivo");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        
        return "";
    }
	
	private void readXlsxFile(FileInputStream fis) {
		try {
			XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = mySheet.iterator();
			// Traversing over each row of XLSX file
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case Cell.CELL_TYPE_NUMERIC:
							System.out.print(cell.getNumericCellValue() + "\t");
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							System.out.print(cell.getBooleanCellValue() + "\t");
							break;
						default : 
					}
				}
				System.out.println(""); 
			}
			myWorkBook.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}

	private void readFile(FileInputStream file) {
		try {
		    POIFSFileSystem fs = new POIFSFileSystem(file);
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    HSSFSheet sheet = wb.getSheetAt(0);
		    HSSFRow row;
		    HSSFCell cell;

		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();

		    int cols = 0; // No of columns
		    //int tmp = 0;

		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    //for(int i = 0; i < rows; i++) {
		    //    row = sheet.getRow(i);
		    //    if(row != null) {
		    //        tmp = sheet.getRow(i).getPhysicalNumberOfCells();
//		            if(tmp > cols) cols = tmp;
//		        }
//		    }

		    for(int r = 0; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		            for(int c = 0; c < cols; c++) {
		                cell = row.getCell((short)c);
		                if(cell != null) {
		                                  System.out.println("contenido " + cell.getStringCellValue());
		                }
		            }
		        }
		    }
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
	}
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<DatoHorario> getDatos() {
		return datos;
	}

	public void setDatos(List<DatoHorario> datos) {
		this.datos = datos;
	}
	
	
}
