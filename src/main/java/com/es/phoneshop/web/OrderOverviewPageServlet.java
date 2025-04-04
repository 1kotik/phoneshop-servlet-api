package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.services.CartService;
import com.es.phoneshop.model.services.DefaultCartService;
import com.es.phoneshop.model.services.DefaultOrderService;
import com.es.phoneshop.model.services.OrderService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = DefaultOrderService.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secureId = request.getPathInfo().substring(1);
        request.setAttribute(AppConstants.RequestAttributes.ORDER_ATTRIBUTE, orderService.getOrderBySecureId(secureId));
        cartService.clearCart(HttpSessionUtils.getCartFromSession(request.getSession()));
        request.getSession().removeAttribute(HttpSessionUtils.ORDER_SESSION_ATTRIBUTE);
        request.getRequestDispatcher(AppConstants.JspFilePaths.OVERVIEW_JSP).forward(request, response);
    }
}
