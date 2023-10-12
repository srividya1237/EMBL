package com.embl.ontology.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = "stackTrace")
public class OLSException extends RuntimeException
{

   private static final long serialVersionUID = 1L;

   private HttpStatusCode status;
   private String error;

   public OLSException(String error, HttpStatusCode httpStatusCode)
   {
      super(error);
      setError(error);
      setStatus(httpStatusCode);   
   }
     
   public String getError()
   {
      return error;
   }
   
   public void setError(String error)
   {
      this.error = error;
   }
   
   public HttpStatusCode getStatus()
   {
      return status;
   }

   public void setStatus(HttpStatusCode httpStatusCode)
   {
      this.status = httpStatusCode;
   }   
}
