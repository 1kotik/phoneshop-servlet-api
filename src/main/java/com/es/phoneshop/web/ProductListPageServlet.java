package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;


import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.exception.BadProductRequestException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = new ArrayListProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if ("delete".equals(request.getParameter("action"))) {
                productDao.delete(Long.parseLong(request.getParameter("product-id")));
                response.sendRedirect(request.getContextPath() + "/products");
                return;
            }

            if ("get-product".equals(request.getParameter("action"))) {
                request.setAttribute("product", productDao.getProduct(Long.parseLong(request.getParameter("product-id"))));
                request.getRequestDispatcher("/WEB-INF/pages/productInfo.jsp").forward(request, response);
                return;
            }

            if ("redirect-to-add-product-page".equals(request.getParameter("action"))) {
                request.getRequestDispatcher("/WEB-INF/pages/addProduct.jsp").forward(request, response);
                return;
            }

            request.setAttribute("products", productDao.findProducts());
            request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/exception.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if ("update".equals(request.getParameter("save-action"))) {
                productDao.save(new Product(Long.parseLong(request.getParameter("product-id")),
                        request.getParameter("product-code"),
                        request.getParameter("product-description"),
                        new BigDecimal(request.getParameter("product-price")),
                        Currency.getInstance(request.getParameter("product-currency")),
                        Integer.parseInt(request.getParameter("product-stock")),
                        request.getParameter("product-image-url")));
            }

            if ("add".equals(request.getParameter("save-action"))) {
                productDao.save(new Product(null,
                        request.getParameter("product-code"),
                        request.getParameter("product-description"),
                        new BigDecimal(request.getParameter("product-price")),
                        Currency.getInstance(request.getParameter("product-currency")),
                        Integer.parseInt(request.getParameter("product-stock")),
                        request.getParameter("product-image-url")));
            }

            response.sendRedirect(request.getContextPath() + "/products");
        } catch (NullPointerException | BadProductRequestException | IllegalArgumentException e) {
            request.setAttribute("message", "Bad parameters. You should fill in all parameters correctly");
            request.getRequestDispatcher("/WEB-INF/pages/exception.jsp").forward(request, response);
        }

    }
}
