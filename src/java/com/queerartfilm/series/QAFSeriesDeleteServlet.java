/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.series;

import com.queerartfilm.dao.DAO;
import com.queerartfilm.dao.QAFSeriesDAO;
import com.queerartfilm.film.FeaturedFilmDeleteServlet;
import com.queerartfilm.validation.FormUtil;
import com.queerartfilm.validation.IsIntegerV;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ch67dotnet
 */
public class QAFSeriesDeleteServlet extends HttpServlet {


    private static final Logger logger = Logger.getLogger(FeaturedFilmDeleteServlet.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/delete_confirm.jsp";
    private static final String PARAM_ID = "id";
    private static final String FAIL_MSG_FMT =
            "The attempt to delete QAF Series record failed because it was not " +
            "found: id = <strong>%s</strong>";
    private static final String FAIL_LOG_FMT = "QAFSeries delete failed:id = %s";
    private static final String SUCCESS_MSG_FMT =
            "The attempt to delete QAF Series record succeeded: " +
            "id = <strong>%s</strong>, name = <strong>%s</strong>";
    private static final String SUCCESS_LOG_FMT = "QAFSeries delete succeeded: id = %s, name = %s";
    // Vars ---------------------------------------------------------------------------------------
    private DAO<QAFSeries> dao;

    @Override
    public void init() throws ServletException {
        this.dao = new QAFSeriesDAO();
    }
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String idString = FormUtil.getParamOrEmpty(request, PARAM_ID);

        QAFSeries qafSeries = null;
        if (IsIntegerV.P.apply(idString)) {
            try {
                long id = Long.parseLong(idString);
                qafSeries = dao.find(id);
            } catch (NumberFormatException ex) {
                logger.warning("Shouldn't be here, parse error on " + idString);
            }
        }

        String message;
        if (qafSeries == null) {
            message = String.format(FAIL_MSG_FMT, idString);
            logger.warning(String.format(FAIL_LOG_FMT, idString));
        } else {
            dao.delete(qafSeries);
            message = String.format(SUCCESS_MSG_FMT, qafSeries.getId(), qafSeries.getTitle());
            logger.info(String.format(SUCCESS_LOG_FMT,  qafSeries.getId(), qafSeries.getTitle()));
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
