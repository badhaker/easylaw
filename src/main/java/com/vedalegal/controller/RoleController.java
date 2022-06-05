package com.vedalegal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.modal.Role;
import com.vedalegal.resource.RestMappingConstant;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.RoleService;

@RestController
@RequestMapping(RestMappingConstant.RoleUri.BASE_URI)
public class RoleController {

	@Autowired
	RoleService roleService;

	@GetMapping
	public ResponseEntity<BaseApiResponse> getAllRoles() {

		List<Role> roleList = roleService.getAllRoles();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(roleList);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	/*
	 * @PostMapping(RestMappingConstant.RoleUri.ADD_ROLE_URI) public
	 * ResponseEntity<Role> addRole(@RequestBody Role role){ try { Role
	 * roleAdded=roleService.addRole(role); return new
	 * ResponseEntity<Role>(roleAdded,HttpStatus.CREATED); } catch(Exception e) {
	 * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	@PostMapping(RestMappingConstant.RoleUri.ADD_ROLE_URI)
	public ResponseEntity<BaseApiResponse> addRole(@RequestParam String role) {

		String roleAdded = roleService.addRole(role);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(roleAdded);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

	}

	@PutMapping("/updatePermission/{roleId}")
	public ResponseEntity<BaseApiResponse> addPermissions(@RequestParam String permission, @PathVariable Long roleId) 
	{

		String roleAdded = roleService.addPermission(permission,roleId);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(roleAdded);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

	}
	
	@DeleteMapping("/delete/{roleId}")
	public ResponseEntity<BaseApiResponse> deleteRoleById(@PathVariable("roleId") Long id) {
		CommonSuccessResponse response=roleService.deleteRoleById(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
		
	}


	@GetMapping("/permissions/{role}")
	public ResponseEntity<BaseApiResponse> getRolePermissions(@PathVariable("role") String role)
	{
		String permissions = roleService.getPermissions(role);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(permissions);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}

}
