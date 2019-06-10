package com.ikubinfo.project.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.service.SomethingService;
import com.ikubinfo.project.util.Paths;

@Path(Paths.SOMETHING)
public class SomethingResource extends BaseResource {

	private SomethingService somethingService;

	public SomethingResource() {
		this.somethingService = new SomethingService();
	}

	@GET
	@Path("/{id}")
	public Response getEmployeeById(@PathParam("id") final int id) {
		return ok(somethingService.getSomething(id));
	}

}
