package ru.javalab.hateoas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.javalab.hateoas.models.Basket;
import ru.javalab.hateoas.models.Good;
import ru.javalab.hateoas.models.Order;
import ru.javalab.hateoas.models.User;
import ru.javalab.hateoas.reposiories.BasketsRepository;
import ru.javalab.hateoas.reposiories.GoodsRepository;
import ru.javalab.hateoas.reposiories.UsersRepository;
import ru.javalab.hateoas.services.OrdersService;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class OrderTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdersService ordersService;

    private final String orderId = UUID.randomUUID().toString();

    @BeforeEach
    public void setUp() {
        when(ordersService.createOrder(1L)).thenReturn(expextedOrder());
    }

    @Test
    public void createOrderTest() throws Exception {
        mockMvc.perform(put("/user/1/order-create")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(expextedOrder().getOrderId()))
                .andExpect(jsonPath("$.quantityGood").value(expextedOrder().getQuantityGood()))
                .andExpect(jsonPath("$.user.name").value(expextedOrder().getUser().getName()))
                .andExpect(jsonPath("$.good.title").value(expextedOrder().getGood().getTitle()))
                .andDo(document("order", responseFields(
                        fieldWithPath("id").description("id для бд"),
                        fieldWithPath("orderId").description("id заказа"),
                        fieldWithPath("quantityGood").description("Количевство товара в заказе"),
                        subsectionWithPath("user").description("Покупатель"),
                        subsectionWithPath("good").description("Товар")
                )));
    }

    private Order expextedOrder() {
        return Order.builder()
                .id(1L)
                .orderId(orderId)
                .quantityGood(10)
                .user(User.builder().id(1L).name("alex").email("alex@mail.ru").isConfirmed(true).build())
                .good(Good.builder().id(1L).title("good").description("good").build())
                .build();

    }

}