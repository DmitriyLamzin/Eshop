package org.lamzin.eshop.controller.rest;


import org.lamzin.eshop.dto.OrderCardBasicDto;
import org.lamzin.eshop.dto.OrderCardExtendedDto;
import org.lamzin.eshop.dtoBuilder.DtoToOrderCardBuilder;
import org.lamzin.eshop.dtoBuilder.OrderCardDtoBuilder;
import org.lamzin.eshop.model.OrderCard;
import org.lamzin.eshop.model.Person;
import org.lamzin.eshop.service.interfaces.CardService;
import org.lamzin.eshop.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Dmitriy on 13.06.2016.
 */
@RestController
@RequestMapping("/rest/card")
public class CardController {
    @Autowired
    ProductService productService;

    @Autowired
    CardService cardService;

    @Autowired
    OrderCardDtoBuilder orderCardDtoBuilder;

    @Autowired
    DtoToOrderCardBuilder dtoToOrderCardBuilder;

    @RequestMapping(value = "/new",
            method = RequestMethod.GET)
    public ResponseEntity<OrderCardExtendedDto> createOrderCard() {
        return new ResponseEntity<OrderCardExtendedDto>(new OrderCardExtendedDto(), HttpStatus.OK);
    }

    @RequestMapping(value = "/items/{id}",
            method = RequestMethod.POST)
    public ResponseEntity<OrderCardExtendedDto> addOrderItem(@PathVariable long id,
                                                             @RequestParam(required = false, defaultValue = "1") int size,
                                                             @RequestBody OrderCardExtendedDto orderCardToSend) {
        OrderCard orderCard = dtoToOrderCardBuilder.buildOrderCard(orderCardToSend);
        orderCard.addOrderItem(productService.getProductById(id), size);

        return new ResponseEntity<OrderCardExtendedDto>(orderCardDtoBuilder.buildExtended(orderCard), HttpStatus.OK);
    }

    @RequestMapping(value = "/items/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<OrderCardExtendedDto> deleteOrderItem(@PathVariable long id, @RequestBody OrderCardExtendedDto orderCardToSend) {
        OrderCard orderCard = dtoToOrderCardBuilder.buildOrderCard(orderCardToSend);
        orderCard.removeOrderItem(productService.getProductById(id));

        return new ResponseEntity<OrderCardExtendedDto>(orderCardDtoBuilder.buildExtended(orderCard), HttpStatus.OK);
    }

    @RequestMapping(value = "/{cardId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<OrderCardExtendedDto> deleteOrderCard(@PathVariable long cardId) {

        cardService.deletOrderCard(cardId);
        return new ResponseEntity<OrderCardExtendedDto>(HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET)
    public ResponseEntity<Resources<OrderCardExtendedDto>> getAllCards() {
        List<OrderCard> orderCards = cardService.getAllOrderCards();
        List<OrderCardExtendedDto> orderCardExtendedDtos = orderCardDtoBuilder.buildListExtended(orderCards);
        List<Link> linkList = new ArrayList<Link>();
        linkList.add(linkTo(methodOn(CardController.class).getAllCards()).withSelfRel());

        Resources<OrderCardExtendedDto> resources = new Resources<OrderCardExtendedDto>(orderCardExtendedDtos, linkList);
        return new ResponseEntity<Resources<OrderCardExtendedDto>>(resources, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST)
    public ResponseEntity<OrderCardExtendedDto> sendOrderCard(@RequestBody @Valid OrderCardExtendedDto orderCardToSend) {
        if (orderCardToSend.getSize() == 0) {
            return new ResponseEntity<OrderCardExtendedDto>(HttpStatus.BAD_REQUEST);
        }

        OrderCard orderCard = dtoToOrderCardBuilder.buildOrderCard(orderCardToSend);
        return new ResponseEntity<OrderCardExtendedDto>(
                orderCardDtoBuilder.buildExtended(cardService.createOrderCard(orderCard)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    public OrderCardExtendedDto getOrderCard(@PathVariable long id) {
        return orderCardDtoBuilder.buildExtended(cardService.getOrderCard(id));
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.POST)
    public void sendOrder(@PathVariable long id) {
        //TODO
    }

    @RequestMapping(value = "/{id}/person",
            method = RequestMethod.POST)
    public Person addPerson(@PathVariable long id, @RequestBody Person person) {
        return cardService.addPerson(id, person);
    }
}