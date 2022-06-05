package com.vedalegal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.modal.Category;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/*@GetMapping("/get/{categoryId}")
	private ResponseEntity<BaseApiResponse> getCategoryByCategoryId(@PathVariable Long id) {
		Category category=categoryService.findCategoryById(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(category);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}*/

	/*@DeleteMapping("/delete/{CategorIid}")
	private ResponseEntity<?> deleteCategory(@PathVariable Long id) {
		Category categoryDeleted=categoryService.deleteCategory(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(categoryDeleted);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}*/

	/*@PostMapping("/addCategory")
	public ResponseEntity<BaseApiResponse> addCategory(@RequestBody Category modal) {
		Category categoryAdded = categoryService.addCategory(modal);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(categoryAdded);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}*/

	@GetMapping("/getCategoryList/{masterServiceId}")
	private ResponseEntity<BaseApiResponse> getCategoryList(@PathVariable Long masterServiceId) {
		List<Category> categoryList = categoryService.getCategoryByMasterService(masterServiceId);
		BaseApiResponse baseApiResponse=ResponseBuilder.getSuccessResponse(categoryList);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
}
