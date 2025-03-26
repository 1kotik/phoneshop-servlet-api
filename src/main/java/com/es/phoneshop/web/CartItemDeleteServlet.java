package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.services.CartService;
import com.es.phoneshop.model.services.DefaultCartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = Long.parseLong(request.getPathInfo().substring(1));
        cartService.deleteItem(HttpSessionUtils.getCartFromSession(request.getSession()), productId);
        response.sendRedirect(request.getContextPath() + "/cart?message=" + AppConstants.Messages.ITEM_DELETE_SUCCESS_MESSAGE);
    }
}
