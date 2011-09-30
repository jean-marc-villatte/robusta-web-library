/*
 * Copyright 2007-2011 Nicolas Zozol
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robustaweb.library.rest.controller.implementation;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.rest.controller.ResourceController;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.resource.Resource;

/**
 * This is a ResourceController designed for JAX-RS specifications
 * <p>
 * Using Jersey or another implentation of JAX-RS, injection will call functions
 * with @Context annotation. Calling these functions, and especially
 * {@link JaxRsResourceController#setHttpServletRequest(javax.servlet.http.HttpServletRequest)
 * setHttpServletRequest()}, we find enough informations for all the usual work.
 * </p>
 * 
 * @todo1 : same stuff than JspResourceController : removes handle... and use
 *        classicF() with overloading
 * @todo1 : create isPut() & isDelete() en detectant _method
 * @author Nicolas Zozol for Robusta Web ToolKit & <a
 *         href="http://www.edupassion.com">Edupassion.com</a> -
 *         nzozol@robustaweb.com
 */
public class JaxRsResourceController implements
		ResourceController<HttpServletRequest> {

	String authorizationValue;
	private UriInfo context;
	private HttpServletRequest request;
	private HttpServletResponse response;

	@Context
	public void setContext(UriInfo context) {

		this.context = context;
		
		//TODO : if headers contains : If-None-Match, If-Unmodified-Since, or If-modified-Since
		init();
	}

	public UriInfo getContext() {
		return context;
	}

	@Override
	public String getUri() {
		return this.context.getBaseUri().toString();
	}

	/**
	 * Does nothing, and is called at the end of the setContext() function
	 * Overwrite the function for, by exemple, initializing the application if
	 * needed.
	 */
	public void init() {
	}

	/**
	 * Set the HttpServletRequest that has hitten the JAX Servlet and brings
	 * informations.
	 */
	@Context
	public void setHttpServletRequest(HttpServletRequest request) {

		this.request = request;
	}

	/**
	 * Set the HttpServletResponse that has hitten the JAX Servlet.
	 */
	@Context
	public void setHttpServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public String getParam(String param) {
		if (context == null) {
			return "";
		} else {
			return context.getQueryParameters().getFirst(param);
		}
	}

	/**
	 * Returns the Authorization header value of the incoming Http request
	 * 
	 * @return
	 */
	@Override
	public String getAuthorizationValue() {
		return this.getHeaders().getValue("authorization", true);
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public String getRemoteIp() {
		return this.request.getRemoteAddr();
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public HttpServletRequest getHttpRequest() {
		return request;
	}

	public HttpServletResponse getHttpResponse() {
		return response;
	}

	@Override
	public CoupleList<String, String> getCookies() {

		CoupleList<String, String> result = new CoupleList<String, String>();
		/* Getting cookies */
		javax.servlet.http.Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			result.addCouple(cookie.getName(), cookie.getValue());
		}
		return result;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public CoupleList<String, String> getParams() {
		CoupleList<String, String> result = new CoupleList<String, String>();

		Enumeration en = getHttpRequest().getParameterNames();
		while (en.hasMoreElements()) {
			String name = en.nextElement().toString();
			result.addCouple(name, getHttpRequest().getParameter(name));
		}
		return result;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public CoupleList<String, String> getHeaders() {
		CoupleList<String, String> result = new CoupleList<String, String>();

		Enumeration en = getHttpRequest().getParameterNames();
		while (en.hasMoreElements()) {
			String name = en.nextElement().toString();
			result.addCouple(name, getHttpRequest().getParameter(name));
		}
		return result;
	}

	/**
	 * Shortcut for returning a valid representation of the entity
	 * 
	 * @param entity
	 * @return
	 */
	public Response getResponse(String entity) {
		Response r = Response.status(Status.OK).entity(entity).build();
		return r;
	}

	public Response getResponse(Resource resource)
			throws RepresentationException {
		Response r = Response.status(Status.OK).entity(
				resource.getRepresentation()).build();
		return r;
	}

	public Response ok(String rawString) {
		Response r = Response.status(Status.OK).entity(rawString).build();
		return r;
	}

	public Response ok(Representation representation) {
		return ok(representation.toString());
	}

	public Response ok(Resource resource) {
		return ok(resource.getRepresentation());
	}

	public Response ok(Object o) {
		return ok(o.toString());
	}

	public Response checkIfNoneMatch(Object md5hashOrTimestampOrOther,
			Representation representation) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response notModified(String response) {
		Response r = Response.status(Status.NOT_MODIFIED).entity(response)
				.build();
		return r;
	}

	public Response preconditionFailed412(String response) {
		Response r = Response.status(Status.PRECONDITION_FAILED).entity(
				response).build();
		return r;
	}
}
