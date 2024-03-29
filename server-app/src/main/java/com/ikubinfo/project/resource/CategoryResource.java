package com.ikubinfo.project.resource;

import java.net.URI;
import java.net.URISyntaxException;

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
import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.repository.CategoryRepository;
import com.ikubinfo.project.repository.UserRepository;
import com.ikubinfo.project.service.CategoryService;
import com.ikubinfo.project.util.Paths;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path(Paths.CATEGORIES)
public class CategoryResource  extends BaseResource {

		private CategoryService categoryService;
		private CategoryRepository categoryRepository;
		private UserRepository userRepository;
		public CategoryResource() {
			this.categoryService= new CategoryService();
			this.categoryRepository=new CategoryRepository();
			this.userRepository=new UserRepository();
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
		public Response update(CategoryModel category , @PathParam("categoryId") int categoryId) {
			return Response.ok(categoryService.update(category,categoryId)).build();
		}
		
		@POST
		public Response insert(CategoryModel category) throws URISyntaxException {
				return Response.created(new URI("/categories/"+categoryService.insert(category, userRepository.getUserByUsername(getUsernameFromToken())).getCategoryId())).build();
		}
		
		@DELETE
		@Path("/{categoryId}")
		public Response delete(@PathParam("categoryId") int categoryId) {
			categoryService.delete(categoryId);
			return Response.noContent().build();
		}
		
		@PUT
		@Path("/subscribe/{id}")
		public Response subscribe(@PathParam("id") final int id) {
			return Response.ok(categoryService.subscribe(getUsernameFromToken(),id)).build();
			
		}
		
		@PUT
		@Path("/unsubscribe/{id}")
		public Response unsubscribe(@PathParam("id") final int id) {
			return Response.ok(categoryService.unsubscribe(getUsernameFromToken(),id)).build();
		}

		@GET
		@Path("/subscribed")
		public Response getSubscribedCategories() {
			return Response.ok(categoryRepository.getSubscribedCategories(getUsernameFromToken())).build();
		}
		
		@GET
		@Path("/{id}/posts")
		public Response getPostsofCategory(@PathParam("id") final int id) {
			return Response.ok(categoryService.getPostsOfCategory(getUsernameFromToken(),id)).build();
		}
		
		@GET
		@Path("/unsubscribed")
		public Response getUnsubscribedCategories() {
			return Response.ok(categoryRepository.getUnsubscribedCategories(getUsernameFromToken())).build();
		}
}
