package com.docspace.home;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.docspace.domain.IDocument;
import com.docspace.facade.DocumentFacade;
import com.docspace.utilities.exception.DocSpaceException;
import com.docspace.utilities.servicelocator.ServiceLocator;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private final Logger logger = Logger.getLogger(DownloadServlet.class.getName());
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Downloading file...");
		String documentId = request.getParameter("documentId");
		DocumentFacade documentFacade = (DocumentFacade) ServiceLocator.getInstance().getBean("documentFacade");
		IDocument document = null;
		try {
			document = documentFacade.retrieveDocument(Integer.parseInt(documentId));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (DocSpaceException e1) {
			e1.printStackTrace();
		}
		System.out.println(document.getName());
	    System.out.println(document.getType());
	    System.out.println(document.getFileContent().length);
	    response.setHeader("Content-Type", document.getType());
	    response.setHeader("Content-Length", String.valueOf(document.getFileContent().length));
	    response.setHeader("Content-Disposition", "attachment;filename=\"" + document.getName() + "\"");
	    try {
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(document.getFileContent());
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			logger.severe("Download file error. " + e.getMessage());
			e.printStackTrace();
		}
	    System.out.println("done!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
