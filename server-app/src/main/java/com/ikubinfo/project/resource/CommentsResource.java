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
import com.ikubinfo.project.repository.UserRepository;
import com.ikubinfo.project.service.CommentsService;
import com.ikubinfo.project.util.Paths;
@Path(Paths.COMMENTS)
public class CommentsResource extends BaseResource {
	private CommentsService commentsService;
	private UserRepository userRepository;
	public CommentsResource() {
		commentsService=new CommentsService();
		userRepository=new UserRepository();
	}
	
	@GET
	public Response getAllComments() {
		return Response.ok(commentsService.getAllComments()).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") final int id) {
		commentsService.delete(id);
		return Response.noContent().build();
	}
}
