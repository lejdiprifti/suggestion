package com.ikubinfo.project.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.converter.CategoryConverter;

import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.repository.SuggestionsRepository;
import com.ikubinfo.project.repository.UserRepository;

import com.ikubinfo.project.service.SuggestionsService;
import com.ikubinfo.project.util.Paths;


@Path(Paths.SUGGESTIONS)
public class SuggestionsResource extends BaseResource {
	private SuggestionsRepository suggestionsRepository;
	private CategoryConverter categoryConverter;
	private UserRepository userRepository;
	private SuggestionsService suggestionsService;
			public SuggestionsResource() {
				this.suggestionsRepository=new SuggestionsRepository();
				this.categoryConverter = new CategoryConverter();
				this.userRepository= new UserRepository();
				this.suggestionsService=new SuggestionsService();
			}

	@GET
	public Response getSuggestions() {
		return Response.ok(suggestionsService.getSuggestions(getRoleFromToken(),getUsernameFromToken())).build();
	}

	@GET
	@Path("/{id}")
	public Response getSuggestionById(@PathParam("id") final int id) {
		return Response.ok(categoryConverter.toModel(suggestionsRepository.getSuggestionById(id))).build();
	}

	@POST
	public Response suggest(CategoryModel suggestion) throws URISyntaxException {
		return Response.created(new URI("/suggestions/"+suggestionsService.suggest(suggestion,userRepository.getUserByUsername(getUsernameFromToken())).getCategoryId())).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateSuggestion(CategoryModel category,@PathParam("id") final int id) {
		return Response.ok(suggestionsService.update(category, id, getUsernameFromToken())).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") final int id) {
		suggestionsService.delete(id);
		return Response.noContent().build();
	}

	@PUT
	@Path("/accept/{id}")
	public Response accept(@PathParam("id") final int id) {
		return Response.ok(suggestionsService.accept(getUsernameFromToken(),id)).build();
	}

	@PUT
	@Path("/decline/{id}")
	public Response decline(@PathParam("id") final int id) {
		return Response.ok(suggestionsService.decline(getUsernameFromToken(),id)).build();
	}

}

