package adf.homework.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import adf.homework.dto.ProductDTO;
import adf.homework.service.ProductService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private static final String PRODUCT = "product";
    private static final String PRODUCT_FORM = "product/form";

    private final ProductService productService;

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "product/index";
    }

    @GetMapping("/new")
    public String getProductCreateForm(Model model) {
        model.addAttribute(PRODUCT, new ProductDTO());
        return PRODUCT_FORM;
    }

    @PostMapping("/new")
    public String postProductCreateForm(@Valid @ModelAttribute("product") ProductDTO dto, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            model.addAttribute(PRODUCT, dto);
            return PRODUCT_FORM;
        }

        ProductDTO saved = productService.createProduct(dto);

        return "redirect:/products/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable long id, Model model) {
        model.addAttribute(PRODUCT, productService.getProduct(id));
        return "product/details";
    }

    @GetMapping("/{id}/edit")
    public String getProductUpdateForm(@PathVariable long id, Model model) {
        model.addAttribute(PRODUCT, productService.getProduct(id));
        return PRODUCT_FORM;
    }

    @PostMapping("/{id}/edit")
    public String postProductUpdateForm(@PathVariable long id, @Valid ProductDTO dto, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute(PRODUCT, dto);
            return PRODUCT_FORM;
        }

        ProductDTO saved = productService.updateProduct(dto);

        return "redirect:/products/" + saved.getId();
    }

}
