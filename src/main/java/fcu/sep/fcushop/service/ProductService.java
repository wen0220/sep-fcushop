package fcu.sep.fcushop.service;

import fcu.sep.fcushop.database.Sql2oDbHandler;
import fcu.sep.fcushop.model.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;

/**
 *productservice.
 */
@Service
public class ProductService {

  @Autowired
  private Sql2oDbHandler sql2oDbHandler;

  public ProductService() {

  }

  /**
   *productlist.
   */
  public List<Product> getProducts() {
    try (Connection connection = sql2oDbHandler.getConnector().open()) {
      String query = "select ID id, NAME name, IMAGE_URL imageUrl, PRICE price,"
          + " DESCRIPTION description from PRODUCT";
      return connection.createQuery(query).executeAndFetch(Product.class);
    }
  }

  /**
   *keyword product list.
   */
  public List<Product> getProducts(String keyword) {
    try (Connection connection = sql2oDbHandler.getConnector().open()) {
      String query = "select ID id, NAME name, IMAGE_URL imageUrl, "
          + "PRICE price, DESCRIPTION description"
          + " from PRODUCT where name like :keyword";

      return connection.createQuery(query)
          .addParameter("keyword", "%" + keyword + "%")
          .executeAndFetch(Product.class);
    }
  }

  /**
   *insert product.
   */
  public String insert(String name, String imageUrl, Integer price, String description) {
    try (Connection connection = sql2oDbHandler.getConnector().open()) {
      String query = "insert into PRODUCT (NAME,IMAGE_URL,PRICE,DESCRIPTION) "
          + "values (:name,:imageUrl,:price,:description)";
      System.out.println(query);
      connection.createQuery(query)
          .addParameter("name", name)
          .addParameter("imageUrl", imageUrl)
          .addParameter("price", price)
          .addParameter("description", description)
          .executeUpdate();
      return "Success";
    }
  }

  /**
   *update product.
   */
  public String update(String name, Integer price) {
    try (Connection connection = sql2oDbHandler.getConnector().open()) {
      String query = "update product set PRICE=:price where NAME=:name";

      connection.createQuery(query)
          .addParameter("name", name)
          .addParameter("price", price)
          .executeUpdate();
      return "Success";
    }
  }
}

