package org.acme;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class HelloService {

	private Person person = new Person("Deng", "Linde");

	@Path("/hello")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello Course";
	}

	@Path("/person")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson() {
		return new Person("John Doe", "123 Main Street");
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePerson(Person person) {
		this.person = person; // Assuming 'person' is a class field that holds the Person object
		return Response.ok(person).build();
	}
}
