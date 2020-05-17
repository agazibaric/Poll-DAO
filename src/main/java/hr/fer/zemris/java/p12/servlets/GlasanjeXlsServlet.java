package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * Web servlet that creates XLS file 
 * that contains poll informations with current voting results.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException | NullPointerException ex) {
			return;
		}
		
		List<PollOptions> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		pollOptions.sort(PollOptions.BY_VOTES_COUNT);
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati-glasanja.xls\"");

		HSSFWorkbook workbook = createXLSDocument(pollOptions);
		OutputStream ostream = resp.getOutputStream();
		workbook.write(ostream);
		ostream.flush();
		workbook.close();
	}
	
	/**
	 * Method creates {@link HSSFWorkbook} object 
	 * that contains band informations given in {@code bandList}.
	 * 
	 * @param bandList list of {@link BandInfo} objects.
	 * @return         {@link HSSFWorkbook} object 
	 */
	private HSSFWorkbook createXLSDocument(List<PollOptions> pollOptions) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Informacije o anketi");
		
		HSSFRow firstRow = sheet.createRow(0);
		firstRow.createCell(0).setCellValue("Ime");
		firstRow.createCell(1).setCellValue("Broj glasova");
		firstRow.createCell(2).setCellValue("Link");
		
		for (int index = 0, n = pollOptions.size(); index < n; index++) {
			createNewRow(sheet, pollOptions.get(index), index + 1);
		}
		return workbook;
	}
	
	/**
	 * Method creates new {@link HSSFRow} in given {@code sheet}<br>
	 * which contains name, number of votes and link of the song of the given {@code band}.<br>
	 * Given {@code index} represents index of row in sheet.
	 * 
	 * @param sheet HSSFSheet sheet to which row is added
	 * @param band  band whose informations are stored in row
	 * @param index index of row in sheet
	 */
	private void createNewRow(HSSFSheet sheet, PollOptions option, int index) {
		HSSFRow row = sheet.createRow(index);
		row.createCell(0).setCellValue(option.getOptionTitle());
		row.createCell(1).setCellValue(option.getVotesCount());
		row.createCell(2).setCellValue(option.getOptionLink());
	}

}
