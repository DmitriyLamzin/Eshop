package org.lamzin.eshop.controllers.rest;

import org.lamzin.eshop.entites.OrderCard;
import org.lamzin.eshop.entites.Person;
import org.lamzin.eshop.entites.dto.OrderCardBasicDto;
import org.lamzin.eshop.entites.dto.OrderCardExtendedDto;
import org.lamzin.eshop.services.CardService;
import org.lamzin.eshop.services.dtoWrapers.DtoToOrderCardBuilder;
import org.lamzin.eshop.services.dtoWrapers.OrderCardDtoBuilder;
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
    CardService cardService;

    @Autowired
    OrderCardDtoBuilder orderCardDtoBuilder;

    @Autowired
    DtoToOrderCardBuilder dtoToOrderCardBuilder;

    @RequestMapping(value = "/new",
            method = RequestMethod.GET)
    public ResponseEntity<OrderCardExtendedDto> createOrderCard(){
        return new ResponseEntity<OrderCardExtendedDto>(new OrderCardExtendedDto(), HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET)
    public ResponseEntity<Resources<OrderCardBasicDto>> getAllCards(){
        List<OrderCard> orderCards = cardService.getAllOrderCards();
        List<OrderCardBasicDto> orderCardBasicDtos = orderCardDtoBuilder.buildListBasic(orderCards);
        List<Link> linkList = new ArrayList<Link>();
        linkList.add(linkTo(methodOn(CardController.class).getAllCards()).withSelfRel());

        Resources<OrderCardBasicDto> resources = new Resources<OrderCardBasicDto>(orderCardBasicDtos, linkList);
        return new ResponseEntity<Resources<OrderCardBasicDto>>(resources, HttpStatus.OK);
    }
    @RequestMapping(
            method = RequestMethod.POST)
    public ResponseEntity<OrderCardExtendedDto> sendOrderCard(@RequestBody @Valid OrderCardExtendedDto orderCardToSend){
        if (orderCardToSend.getSize() == 0 ){
            return new ResponseEntity<OrderCardExtendedDto>(HttpStatus.BAD_REQUEST);
        }

        OrderCard orderCard = dtoToOrderCardBuilder.buildOrderCard(orderCardToSend);
        return new ResponseEntity<OrderCardExtendedDto>(
                orderCardDtoBuilder.buildExtended(cardService.createOrderCard(orderCard)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    public OrderCardExtendedDto getOrderCard(@PathVariable long id){
        return orderCardDtoBuilder.buildExtended(cardService.getOrderCard(id));
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.POST)
    public void sendOrder(@PathVariable long id){
        //TODO
    }

    @RequestMapping(value = "/{id}/person",
            method = RequestMethod.POST)
    public Person addPerson(@PathVariable long id, @RequestBody Person person){
            return cardService.addPerson(id, person);
    }

}
