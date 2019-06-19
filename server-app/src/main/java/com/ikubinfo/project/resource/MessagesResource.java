package com.ikubinfo.project.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.repository.SuggestionsRepository;
import com.ikubinfo.project.util.*;
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path(Paths.MESSAGES)
public class MessagesResource extends BaseResource {
		private SuggestionsRepository suggestionsRepository;
		public MessagesResource() {
			this.suggestionsRepository= new SuggestionsRepository();
		}
		
		@GET
		public Response get() {
			return Response.ok(suggestionsRepository.getAcceptedCategories(getUsernameFromToken())).build();
		}
	
}
