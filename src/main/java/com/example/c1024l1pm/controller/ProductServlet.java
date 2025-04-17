package com.example.c1024l1pm.controller;

import com.example.c1024l1pm.model.Product;
import com.example.c1024l1pm.service.ProductDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {

    private static final long serialVersion = 1L;
    private ProductDAO productDAO;

    public void init() {
        productDAO = new ProductDAO();
        System.out.println("initializing Product");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showNewform(request, response);
                    break;
                case "edit":
//                    showEditForm(req, resp);
                    break;
                case "delete":
//                    DeleteUser(req, resp);
                    break;
                default:
                    listProduts(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertProduct(request, response);
                    break;
                case "edit":
//                    updateProduct(request, response);
                    break;
                            
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        Product newProduct = new Product(name, price);
        productDAO.save(newProduct);
//        productDAO.saveWithStoreProdure(newProduct);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/products/create.jsp");
        dispatcher.forward(request, response);
        RequestDispatcher view = getServletContext().getRequestDispatcher("/products/list.jsp");
        view.forward(request, response);
    }

    private void showNewform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/products/create.jsp");
        dispatcher.forward(request, response);
    }

    private void listProduts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
//        List<Product> listProducts = productDAO.findAll();
        List<Product> listProducts = productDAO.findAllWithStoreProdure();
        request.setAttribute("products", listProducts);
        RequestDispatcher dispatcher;
        dispatcher = request.getRequestDispatcher("products/list.jsp");
        dispatcher.forward(request, response);
    }

    public void destroy() {
        System.out.println("destroying Product");
    }
}
