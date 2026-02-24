package com.devsuperior.examplemockspy.services;

import com.devsuperior.examplemockspy.dto.ProductDTO;
import com.devsuperior.examplemockspy.entities.Product;
import com.devsuperior.examplemockspy.repositories.ProductRepository;
import com.devsuperior.examplemockspy.services.exceptions.InvalidDataException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class PrductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private Product product;
    private ProductDTO productDTO;
    private Long existingId, nonExisitinId;

    @BeforeEach
    void setUp() throws Exception {

        existingId = 1L;
        nonExisitinId = 2L;

        product = new Product(1L, "PlayStation", 2000.00);
        productDTO = new ProductDTO(product);

        Mockito.when(repository.save(any())).thenReturn(product);

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(repository.getReferenceById(nonExisitinId)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    void insertShouldReturnProductDTOWhenValidDate() {
        ProductService serviceSpy = Mockito.spy(service);
        Mockito.doNothing().when(serviceSpy).validateData(productDTO);

        ProductDTO result = serviceSpy.insert(productDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("PlayStation", result.getName());
    }

    @Test
    void insertShouldReturnInvalidDataExceptionWhenProductNameisBlank() {
        productDTO.setName("");

        ProductService serviceSpy = Mockito.spy(service);
        Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(Mockito.any());

        Assertions.assertThrows(InvalidDataException.class,
                () -> serviceSpy.insert(productDTO));
    }

    @Test
    void insertShouldReturnInvalidDataExceptionWhenProductPriceisNegativeOrZero() {
        productDTO.setPrice(-5.0);

        ProductService serviceSpy = Mockito.spy(service);
        Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(Mockito.any());

        Assertions.assertThrows(InvalidDataException.class,
                () -> serviceSpy.insert(productDTO));
    }
}
