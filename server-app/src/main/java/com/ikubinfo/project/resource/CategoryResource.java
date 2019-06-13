package com.ikubinfo.project.resource;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.repository.CategoryRepository;
import com.ikubinfo.project.service.CategoryService;
import com.ikubinfo.project.util.Paths;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path(Paths.CATEGORIES)
public class CategoryResource  extends BaseResource {

		private CategoryService categoryService;
		private CategoryRepository categoryRepository;
		public CategoryResource() {
			this.categoryService= new CategoryService();
			this.categoryRepository=new CategoryRepository();
		}
		
		@GET
		public Response getCategories() {
			return Response.ok(categoryService.getCategories()).build();
		}
		
		@GET
		@Path("/{categoryName}")
		public Response getCategoryName(@PathParam("categoryName") String categoryName) {
			try {
			return Response.ok(categoryService.getCategoryByName(categoryName)).build();
			}catch (NoResultException e) {
				return Response.noContent().build();
			}
		}
		
		@GET
		@Path("/id/{categoryId}")
		public Response getCategoryById(@PathParam("categoryId") int categoryId) {
			try {
			return Response.ok(categoryService.getCategoryById(categoryId)).build();
			}catch (NoResultException e) {
				return Response.noContent().build();
			}
		}
		
		@PUT
		@Path("/{categoryName}")
		public Response update(CategoryEntity category , @PathParam("categoryName") String categoryName) {
			return Response.ok(categoryService.update(category,categoryName)).build();
		}
		
		@POST
		public Response insert(CategoryEntity category) {
			try {
				return Response.ok(categoryService.insert(category)).build();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return Response.serverError().build();
			}
		}
		
		@DELETE
		@Path("/{categoryName}")
		public Response delete(@PathParam("categoryName") String categoryName) {
			categoryService.delete(categoryName);
			return Response.noContent().build();
		}
		
		@PUT
		@Path("/subscribe/{id}")
		public Response subscribe(UserModel user,@PathParam("id") final int id) {
			return Response.ok(categoryRepository.subscribe(user.getUsername(),categoryRepository.getCategoryById(id))).build();
			
		}
		
		@PUT
		@Path("/unsubscribe/{id}")
		public Response unsubscribe(UserModel user,@PathParam("id") final int id) {
			
			return Response.ok(categoryRepository.unsubscribe(user.getUsername(),id)).build();
		}


}
