package com.pptest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

public class QueueTest {

	@POST
	@Consumes("text/plain")
	public String push(String i1, String i2) {
		// save to JMS queue
		try {
			final SendRecvClient client = new SendRecvClient();
			final Gcd g = new Gcd();
			final int i3 = g.gcdCalc(i1, i2); // calculate gcd and save to data
			// base
			client.sendRecvAsync(i1 + " " + i2 + " " + i3);
			client.stop();
			return "Success";
		} catch (final Throwable t) {
			t.printStackTrace();
			return "Failed";
		}
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String gcdList() {
		// json structure
		final TestConnection test = new TestConnection();
		final String json = new Gson().toJson(test.getGCD());
		return json;
		// convert to json
	}
}
