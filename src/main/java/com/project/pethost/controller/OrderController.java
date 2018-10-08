package com.project.pethost.controller;

import com.project.pethost.dbo.OrderDbo;
import com.project.pethost.dbo.OrderStatusDbo;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.form.OrderCreationForm;
import com.project.pethost.repository.OrderRepository;
import com.project.pethost.repository.PetRepository;
import com.project.pethost.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/pethost")
public class OrderController {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final OrderRepository orderRepository;
    private final DataService dataService;
    private final PetRepository petRepository;

    @Autowired
    public OrderController(final OrderRepository orderRepository,
                           final PetRepository petRepository,
                           final DataService dataService) {
        this.orderRepository = orderRepository;
        this.dataService = dataService;
        this.petRepository = petRepository;
    }

    @GetMapping(path = "/outgoingOrders")
    public String myOutgoingOrders(final Model model, @AuthenticationPrincipal final Principal principal) {
        final UserDbo currentUser = dataService.getCurrentUser(principal);
        final List<OrderDbo> orders = orderRepository.findAllByPetOwner(currentUser);
        model.addAttribute("title", "My outcoming orders");
        model.addAttribute("orders", orders);
        return "orders/waitingOrdersPage";
    }

    @GetMapping(path = "/incomingOrders")
    public String myIncomingOrders(final Model model, @AuthenticationPrincipal final Principal principal) {
        final UserDbo currentUser = dataService.getCurrentUser(principal);
        final List<OrderDbo> orders = orderRepository.findAllByPetKeeper(currentUser);
        model.addAttribute("title", "My incoming orders");
        model.addAttribute("orders", orders);
        return "orders/waitingOrdersPage";
    }

    @GetMapping(value = "/createOrder")
    public String createOrder(final Model model, @AuthenticationPrincipal final Principal principal) {
        model.addAttribute("form", new OrderCreationForm());
        final UserDbo currentUser = dataService.getCurrentUser(principal);
        model.addAttribute("myPets", petRepository.findAllByOwnerAndAndProcessed(currentUser, false));
        return "orders/createOrderPage";
    }

    @PostMapping(value = "/createOrder")
    public String saveOrder(@ModelAttribute @Valid final OrderCreationForm form,
                            final BindingResult result,
                            @AuthenticationPrincipal final Principal principal) {
        // Validate result
        if (result.hasErrors()) {
            return "orders/createOrderPage";
        }
        final OrderDbo orderDbo = new OrderDbo();
        final Optional<PetDbo> petDbo = petRepository.findById(form.getPetId());
        if (petDbo.isPresent()) {
            final PetDbo pet = petDbo.get();
            orderDbo.setPet(pet);
            pet.setProcessed(true);
            petRepository.save(pet);
        }
        orderDbo.setCreatedDate(LocalDateTime.now());
        orderDbo.setComments(form.getComments());
        final OrderStatusDbo orderCreatedStatus = dataService.findByStatus("Requested");
        orderDbo.setStatus(orderCreatedStatus);
        orderRepository.save(orderDbo);

        return "redirect:outgoingOrders";
    }

    @GetMapping(path = "/waitingOrders")
    public String waitingOrders(final Model model, @AuthenticationPrincipal final Principal principal) {
        model.addAttribute("title", "Active orders");
        final List<OrderDbo> orders = orderRepository.findAllByStatus(dataService.findByStatus("Requested"));
        model.addAttribute("orders", orders);
        if (principal != null) {
            return "orders/waitingOrdersPage";
        }
        else return "orders/waitingOrdersForNotLoggedUserPage";
    }

    @RequestMapping(value = "/changeOrderStatus", method = RequestMethod.GET)
    public @ResponseBody String changeOrderStatus() {
        return "Changed order status";
    }
}
