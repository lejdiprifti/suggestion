package com.ikubinfo.project.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.model.CommentsModel;
import com.ikubinfo.project.service.CommentsService;
import com.ikubinfo.project.util.Paths;
@Path(Paths.COMMENTS)
public class CommentsResource extends BaseResource {
	private CommentsService commentsService;
	public CommentsResource() {
		commentsService=new CommentsService();
	}
	
	@GET
	public Response getAllComments() {
		return Response.ok(commentsService.getAllComments()).build();
	}
	
	@POST
	public Response insert(CommentsModel comment) throws URISyntaxException {
		return Response.created(new URI("/comment/"+commentsService.insert(comment).getId())).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") final int id) {
		commentsService.delete(id);
		return Response.noContent().build();
	}
}
