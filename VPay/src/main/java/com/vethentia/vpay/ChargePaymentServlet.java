/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vethentia.vpay;

import com.stripe.exception.APIException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andy
 */
public class ChargePaymentServlet extends HttpServlet {

    /**
     * @return the context
     */
    public ServletContext getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(ServletContext context) {
        this.context = context;
    }

    private ServletContext context;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.setContext(config.getServletContext());
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String token = request.getParameter("token");
        String amount = request.getParameter("amount");
 
        if((action != null) && (token != null) && (amount != null)) {
            
          if (action.equals("chargestripe")) {

            // check if user sent empty string
            if (!token.equals("undefined")) {
                
                // Parse payment JSON objec
                ChargeStripe  myCharge = new ChargeStripe();
                
                Double value = Double.parseDouble(amount) * 100;
                long total = value.longValue();
                
                myCharge.setAmount(total);
                myCharge.setStripeToken(token);
                
                try {
                    myCharge.createCharge();
                    
                } catch (APIException ex) {
                    Logger.getLogger(VPayManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else {
             
                context.getRequestDispatcher("/error.jsp").forward(request, response);
            }
          }else {
             
              context.getRequestDispatcher("/error.jsp").forward(request, response);
          }
        } else {
             
            context.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        
        // Nothing to show TBD
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        
        //response.setContentType("text/html;charset=UTF-8");
        //try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
        //    out.println("<!DOCTYPE html>");
        //    out.println("<html>");
        //    out.println("<head>");
        //    out.println("<title>Servlet ChargePaymentServlet</title>");            
        //    out.println("</head>");
        //    out.println("<body>");
        //    out.println("<h1>Servlet ChargePaymentServlet at " + request.getContextPath() + "</h1>");
        //    out.println("</body>");
        //    out.println("</html>");
        //}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
