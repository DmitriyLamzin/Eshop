package org.lamzin.eshop.services.dtoWrapers;

import org.lamzin.eshop.dao.catalog.interfaces.ProductDao;
import org.lamzin.eshop.entites.catalog.Product;
import org.lamzin.eshop.entites.dto.ProductBasicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 28.06.2016.
 */
@Service
public class DtoToProductBuilder {

    @Autowired
    ProductDao productDao;

    public List<Product> buildProductList(List<ProductBasicDto> orderedProducts) {
        List<Product> productList = new ArrayList<Product>();

        for (ProductBasicDto productBasicDto: orderedProducts){
            productList.add(productDao.findById(productBasicDto.getProductId()));
        }
        return productList;
    }
}
