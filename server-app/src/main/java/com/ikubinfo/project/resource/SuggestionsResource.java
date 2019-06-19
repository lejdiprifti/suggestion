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
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.RoleEntity;
import com.ikubinfo.project.repository.SuggestionsRepository;
import com.ikubinfo.project.util.Paths;

@Path(Paths.SUGGESTIONS)
public class SuggestionsResource extends BaseResource {
	private SuggestionsRepository suggestionsRepository;
	private CategoryConverter categoryConverter;

	public SuggestionsResource() {
		this.suggestionsRepository = new SuggestionsRepository();
		this.categoryConverter = new CategoryConverter();
	}

	@GET
	public Response getSuggestions() {
		return Response.ok(suggestionsRepository.getSuggestions()).build();
	}

	@GET
	@Path("/{id}")
	public Response getSuggestionById(@PathParam("id") final int id) {
		return Response.ok(categoryConverter.toModel(suggestionsRepository.getSuggestionById(id))).build();
	}

	@POST
	public Response suggest(CategoryEntity suggestion) throws URISyntaxException {
		suggestionsRepository.insert(suggestion);
		return Response.created(new URI("/" + suggestion.getCategoryId())).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") final int id) {
		suggestionsRepository.delete(id);
		return Response.noContent().build();
	}

	@PUT
	@Path("/accept/{id}")
	public Response accept(@PathParam("id") final int id) {
		return Response.ok(categoryConverter.toModel(suggestionsRepository.accept(getUsernameFromToken(),id))).build();
	}

	@PUT
	@Path("/decline/{id}")
	public Response decline(@PathParam("id") final int id) {
		return Response.ok(categoryConverter.toModel(suggestionsRepository.decline(getUsernameFromToken(),id))).build();
	

}
}
