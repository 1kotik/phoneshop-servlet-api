package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.enums.PaymentMethod;
import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.Order;
import com.es.phoneshop.model.services.DefaultOrderService;
import com.es.phoneshop.model.services.OrderService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;

public class CheckoutPageServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cart cart = HttpSessionUtils.getCartFromSession(request.getSession());
        Order order = HttpSessionUtils.getOrderFromSession(request.getSession());
        Map<String, String> errorMessages = HttpSessionUtils.getErrorMessagesFromSession(request.getSession());

        orderService.updateOrder(order, cart);

        request.setAttribute(AppConstants.RequestAttributes.ERRORS_ATTRIBUTE, errorMessages);
        request.setAttribute(AppConstants.RequestAttributes.ORDER_ATTRIBUTE, order);
        request.getSession().removeAttribute(HttpSessionUtils.ERROR_MESSAGES_SESSION_ATTRIBUTE);
        request.getRequestDispatcher(AppConstants.JspFilePaths.CHECKOUT_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Order order = HttpSessionUtils.getOrderFromSession(request.getSession());
        Map<String, String> errorMessages = HttpSessionUtils.getErrorMessagesFromSession(request.getSession());

        setOrderDetails(order, request, errorMessages);

        if (errorMessages.isEmpty()) {
            orderService.placeOrder(order);
            response.sendRedirect(request.getContextPath() + "/overview/" + order.getSecureId() +
                    "?message=" + AppConstants.Messages.ORDER_SAVE_SUCCESS_MESSAGE);
        } else {
            response.sendRedirect(request.getContextPath() + "/checkout?message=" +
                    AppConstants.Messages.ORDER_SAVE_ERROR_MESSAGE);
        }
    }

    private Optional<String> validateStringParameter(String value) {
        return value == null || value.isEmpty() ? Optional.of(AppConstants.Messages.INVALID_PARAMETER_MESSAGE)
                : Optional.empty();
    }

    private Optional<String> validateDeliveryDate(String deliveryDate) {
        try {
            LocalDate.parse(deliveryDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return Optional.empty();
        } catch (DateTimeParseException e) {
            return Optional.of(AppConstants.Messages.INVALID_DATE_MESSAGE);
        }
    }

    private Optional<String> validatePaymentMethod(String paymentMethod) {
        try {
            PaymentMethod.getPaymentMethod(paymentMethod);
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            return Optional.of(AppConstants.Messages.INVALID_PAYMENT_METHOD_MESSAGE);
        }
    }

    private void setOrderDetails(Order order, HttpServletRequest request, Map<String, String> errorMessages) {
        String firstName = request.getParameter(AppConstants.Parameters.FIRST_NAME_PARAMETER);
        String lastName = request.getParameter(AppConstants.Parameters.LAST_NAME_PARAMETER);
        String phone = request.getParameter(AppConstants.Parameters.PHONE_PARAMETER);
        String deliveryAddress = request.getParameter(AppConstants.Parameters.DELIVERY_ADDRESS_PARAMETER);
        String deliveryDate = request.getParameter(AppConstants.Parameters.DELIVERY_DATE_PARAMETER);
        String paymentMethod = request.getParameter(AppConstants.Parameters.PAYMENT_METHOD_PARAMETER);

        validateStringParameter(firstName).
                ifPresentOrElse(error -> errorMessages.put(AppConstants.Parameters.FIRST_NAME_PARAMETER, error),
                        () -> order.getCustomer().setFirstName(firstName));
        validateStringParameter(lastName).
                ifPresentOrElse(error -> errorMessages.put(AppConstants.Parameters.LAST_NAME_PARAMETER, error),
                        () -> order.getCustomer().setLastName(lastName));
        validateStringParameter(phone).
                ifPresentOrElse(error -> errorMessages.put(AppConstants.Parameters.PHONE_PARAMETER, error),
                        () -> order.getCustomer().setPhone(phone));
        validateStringParameter(deliveryAddress).
                ifPresentOrElse(error -> errorMessages.put(AppConstants.Parameters.DELIVERY_ADDRESS_PARAMETER, error),
                        () -> order.setDeliveryAddress(deliveryAddress));
        validateDeliveryDate(deliveryDate).
                ifPresentOrElse(error -> errorMessages.put(AppConstants.Parameters.DELIVERY_DATE_PARAMETER, error),
                        () -> order.setDeliveryDate(LocalDate.parse(deliveryDate,
                                DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        validatePaymentMethod(paymentMethod).
                ifPresentOrElse(error -> errorMessages.put(AppConstants.Parameters.PAYMENT_METHOD_PARAMETER, error),
                        () -> order.setPaymentMethod(PaymentMethod.getPaymentMethod(paymentMethod)));
    }
}