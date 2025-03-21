package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.ProductOutOfStockException;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.services.CartService;
import com.es.phoneshop.model.services.DefaultCartService;
import com.es.phoneshop.model.services.DefaultProductService;
import com.es.phoneshop.model.services.ProductService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;


public class ProductDetailsPageServlet extends HttpServlet {
    private ProductService productService;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = DefaultProductService.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = parseProductId(request);
        Product product = productService.getProduct(productId);
        LinkedList<Product> recentlyViewedProducts = HttpSessionUtils.getRecentlyViewedProductsFromSession(request.getSession());

        productService.updateRecentlyViewedProducts(recentlyViewedProducts, product);
        request.setAttribute("product", product);
        request.setAttribute("cart", HttpSessionUtils.getCartFromSession(request.getSession()));
        request.setAttribute("recentlyViewedProducts", recentlyViewedProducts);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int quantity = parseQuantity(request);
            Cart cart = HttpSessionUtils.getCartFromSession(request.getSession());
            Long productId = parseProductId(request);
            cartService.addItem(cart, productId, quantity);
        } catch (ParseException e) {
            response.sendRedirect(getRedirectPath(request, "Invalid quantity"));
            return;
        } catch (ProductOutOfStockException e) {
            response.sendRedirect(getRedirectPath(request, e.getMessage()));
            return;
        }
        response.sendRedirect(getRedirectPath(request, ""));
    }

    private Long parseProductId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }

    private Integer parseQuantity(HttpServletRequest request) throws ParseException {
        int quantity = NumberFormat.getInstance(request.getLocale())
                .parse(request.getParameter("quantity")).intValue();
        if (quantity <= 0) {
            throw new ParseException("", 0);
        }
        return quantity;
    }

    private String getMessageParamIfAddToCartSuccess(String errorMessage) {
        return errorMessage.isEmpty() ? "?message=Product is added to the cart"
                : "?error=" + errorMessage;
    }

    private String getRedirectPath(HttpServletRequest request, String errorMessage) {
        return request.getContextPath() + "/products/" + parseProductId(request)
                + getMessageParamIfAddToCartSuccess(errorMessage);
    }

}
