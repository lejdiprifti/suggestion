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
			return Response.ok(categoryService.getCategoryByName(categoryName)).build();
		}
		
		@GET
		@Path("/id/{categoryId}")
		public Response getCategoryById(@PathParam("categoryId") int categoryId) {
			return Response.ok(categoryService.getCategoryById(categoryId)).build();
			
		}
		
		@PUT
		@Path("/{categoryId}")
		public Response update(CategoryEntity category , @PathParam("categoryId") int categoryId) {
			return Response.ok(categoryService.update(category,categoryId)).build();
		}
		
		@POST
		public Response insert(CategoryEntity category) {
				return Response.ok(categoryService.insert(category)).build();
			

		}
		
		@DELETE
		@Path("/{categoryId}")
		public Response delete(@PathParam("categoryId") int categoryId) {
			categoryRepository.delete(categoryId);
			return Response.noContent().build();
		}
		
		@PUT
		@Path("/subscribe/{id}")
		public Response subscribe(@PathParam("id") final int id) {
			return Response.ok(categoryRepository.subscribe(id)).build();
			
		}
		
		@PUT
		@Path("/unsubscribe/{id}")
		public Response unsubscribe(@PathParam("id") final int id) {
			return Response.ok(categoryRepository.unsubscribe(id)).build();
		}

		@GET
		@Path("/subscribed")
		public Response getSubscribedCategories() {
			return Response.ok(categoryRepository.getSubscribedCategories()).build();
		}
		
}
