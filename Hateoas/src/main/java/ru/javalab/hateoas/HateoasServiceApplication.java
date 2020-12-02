package ru.javalab.hateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.javalab.hateoas.models.Basket;
import ru.javalab.hateoas.models.Company;
import ru.javalab.hateoas.models.Good;
import ru.javalab.hateoas.models.User;
import ru.javalab.hateoas.reposiories.*;
import ru.javalab.hateoas.services.OrdersService;

import java.util.ArrayList;

@SpringBootApplication
public class HateoasServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HateoasServiceApplication.class, args);

        GoodsRepository goodsRepository = context.getBean(GoodsRepository.class);
        UsersRepository usersRepository = context.getBean(UsersRepository.class);
        CompaniesRepository companiesRepository = context.getBean(CompaniesRepository.class);
        OrdersRepository ordersRepository = context.getBean(OrdersRepository.class);
        BasketsRepository basketsRepository = context.getBean(BasketsRepository.class);
        MessagesRepository messagesRepository = context.getBean(MessagesRepository.class);
        OrdersService ordersService = context.getBean(OrdersService.class);

        Company company = Company.builder()
                .name("TheBest")
                .info("First company")
                .build();

        companiesRepository.save(company);

        Good good = Good.builder()
                .description("adf")
                .company(company)
                .title("dffas")
                .build();

        goodsRepository.save(good);

        Basket basket = Basket.builder()
                .good(good)
                .quantityGood(3)
                .build();

        basket = basketsRepository.save(basket);

        User user = User.builder()
                .name("Alex")
                .name("Ivanov")
                .email("sd@mail.ru")
                .isConfirmed(true)
                .basket(basket)
                .orders(new ArrayList<>())
                .build();

        user = usersRepository.save(user);

        basketsRepository.addUser(basket.getId(),user);

        ordersService.createOrder(user.getId());
//        User user2 = User.builder()
//                .name("Alex")
//                .email("alex@mail.ru")
//                .basket(Basket.builder()
//                        .goods(Collections.singletonList(Good.builder().title("dsf").build())).build())
//                .build();
//        usersRepository.save(user2);

    }

}
