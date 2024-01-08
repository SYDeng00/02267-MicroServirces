package org.acme.Exceptions;

public class PersonNotExistException extends Exception{
    public PersonNotExistException(Object person){
        super(person.getClass().toString());
    }
}
