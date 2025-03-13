package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class CartPageServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = HttpSessionUtils.getCartFromSession(request.getSession());
        Map<Long, String> errorMessages = HttpSessionUtils.getErrorMessagesFromSession(request.getSession());
        Map<Long, String> quantityValues = HttpSessionUtils.getQuantityValuesFromSession(request.getSession());

        request.setAttribute(AppConstants.RequestAttributes.CART_ATTRIBUTE, cart);
        request.setAttribute(AppConstants.RequestAttributes.ERRORS_ATTRIBUTE, errorMessages);
        request.setAttribute(AppConstants.RequestAttributes.QUANTITY_VALUES_ATTRIBUTE, quantityValues);
        request.getSession().removeAttribute(HttpSessionUtils.ERROR_MESSAGES_SESSION_ATTRIBUTE);
        request.getSession().removeAttribute(HttpSessionUtils.QUANTITY_VALUES_SESSION_ATTRIBUTE);
        request.getRequestDispatcher(AppConstants.JspFilePaths.CART_JSP).forward(request, response);
    }

}
