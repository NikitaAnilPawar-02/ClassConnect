package com.user.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.DB.DBConnect;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DownloadReceiptServlet")
public class DownloadReceiptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int studentId = Integer.parseInt(request.getParameter("studentId"));

		String studentName = "";
		double amountPaid = 0.0;
		String paymentDate = "";
		String transactionId = "";

		try (Connection conn = DBConnect.getConn();
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT s.studentName, p.amountPaid, p.paymentDate, p.razorpayPaymentId "
								+ "FROM student s JOIN payments p ON s.studentId = p.studentId "
								+ "WHERE s.studentId = ? AND p.paymentStatus = 'Success'")) {

			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				studentName = rs.getString("studentName");
				amountPaid = rs.getDouble("amountPaid");
				paymentDate = rs.getString("paymentDate");
				transactionId = rs.getString("razorpayPaymentId");
			} else {
				response.sendRedirect(
						"fees.jsp?error=No successful payment found.");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("fees.jsp?error=Error generating receipt.");
			return;
		}

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition",
				"attachment; filename=Payment_Receipt_" + studentName + ".pdf");

		try (OutputStream out = response.getOutputStream()) {
			Document document = new Document();
			PdfWriter.getInstance(document, out);
			document.open();

			Rectangle rect = new Rectangle(36, 36, 559, 806);
			rect.setBorder(Rectangle.BOX);
			rect.setBorderWidth(2);
			document.add(rect);

			Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD,
					BaseColor.WHITE);
			Font normalFont = new Font(Font.FontFamily.HELVETICA, 12,
					Font.NORMAL, BaseColor.BLACK);

			PdfPTable headerTable = new PdfPTable(1);
			headerTable.setWidthPercentage(100);
			PdfPCell headerCell = new PdfPCell(new Phrase(
					"Gurukrupa Classes - Payment Receipt", titleFont));
			headerCell.setBackgroundColor(new BaseColor(0, 102, 204)); 
															
			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerCell.setPadding(10);
			headerCell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(headerCell);
			document.add(headerTable);

		
			document.add(new Paragraph(" "));

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10);
			table.setWidths(new float[]{3, 7}); 

			PdfPCell cell1 = new PdfPCell(
					new Phrase("Details", new Font(Font.FontFamily.HELVETICA,
							14, Font.BOLD, BaseColor.WHITE)));
			PdfPCell cell2 = new PdfPCell(new Phrase("Information",
					new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD,
							BaseColor.WHITE)));
			cell1.setBackgroundColor(new BaseColor(0, 102, 204)); 
			cell2.setBackgroundColor(new BaseColor(0, 102, 204)); 
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(8);
			cell2.setPadding(8);
			table.addCell(cell1);
			table.addCell(cell2);

			addTableRow(table, "Student Name", studentName);
			addTableRow(table, "Transaction ID", transactionId);
			addTableRow(table, "Amount Paid", "₹ " + amountPaid);
			addTableRow(table, "Payment Date", paymentDate);

			document.add(table);

			document.add(new Paragraph("\nThank you for your payment!",
					new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD,
							BaseColor.BLACK)));
			document.add(new Paragraph("\n"));

			LineSeparator ls = new LineSeparator();
			ls.setLineColor(BaseColor.GRAY);
			document.add(new Chunk(ls));

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addTableRow(PdfPTable table, String label, String value) {
		Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD,
				BaseColor.BLACK);
		Font valueFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL,
				BaseColor.BLACK);

		PdfPCell cell1 = new PdfPCell(new Phrase(label, labelFont));
		PdfPCell cell2 = new PdfPCell(new Phrase(value, valueFont));
		cell1.setPadding(8);
		cell2.setPadding(8);
		table.addCell(cell1);
		table.addCell(cell2);
	}
}
