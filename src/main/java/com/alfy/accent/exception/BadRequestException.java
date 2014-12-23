package com.alfy.accent.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Author: Alan Christensen
 * 12/23/2014
 */
public class BadRequestException extends WebApplicationException {
  public BadRequestException(String message) {
    super(Response.status(400).entity(message).build());
  }
}
