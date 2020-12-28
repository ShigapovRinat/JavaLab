package ru.javalab.querydsl.controller;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javalab.querydsl.dto.GoodDto;
import ru.javalab.querydsl.model.Good;
import ru.javalab.querydsl.repository.GoodRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class GoodController {

    @Autowired
    private GoodRepository goodRepository;

    @GetMapping("/good/search")
    public ResponseEntity<List<GoodDto>> searchByPredicate(@QuerydslPredicate(root = Good.class, bindings = GoodRepository.class) Predicate predicate) {
        return ResponseEntity.ok(
                StreamSupport.stream(goodRepository.findAll(predicate).spliterator(), true)
                        .map(good ->
                                GoodDto.builder()
                                        .title(good.getTitle())
                                        .price(good.getPrice())
                                        .build()).collect(Collectors.toList()));
    }
}
