package org.generation.italy.demo.controller;

import java.util.List;

import org.generation.italy.demo.pojo.Ingrediente;
import org.generation.italy.demo.pojo.Pizza;
import org.generation.italy.demo.service.IngredienteService;
import org.generation.italy.demo.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingrediente")
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingServ; 
	
	@Autowired
	private PizzaService pizzaServ;
	
	@GetMapping
	public String index(Model model) {
		
		List<Ingrediente> ingredienti = ingServ.findAllWPizza();
		
		model.addAttribute("Ingredienti", ingredienti);
		
		return "ingrediente-index";
	}
	
	@GetMapping("/create")
	public String getIngredienteCreate(Model model) {
		
		Ingrediente ingrediente = new Ingrediente();
		
		List<Pizza> pizze = pizzaServ.findAll();
		
		model.addAttribute("ingrediente", ingrediente);
		model.addAttribute("pizze", pizze);
		
		return "ingrediente-create";
	}
	
	@PostMapping("/create")
	public String storeIngrediente(@Valid Ingrediente ingrediente) {
		
		List<Pizza> ingredientePizze = ingrediente.getPizze();
		for (Pizza pizza : ingredientePizze)
			pizza.getIngredienti().add(ingrediente);
		ingServ.save(ingrediente);
		return "redirect:/ingrediente";
	}	
}
