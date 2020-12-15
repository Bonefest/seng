package main.products.org;

import java.lang.Exception;

public class UndefinedProductIDException extends Exception {
	public UndefinedProductIDException(String errorMessage) {
		super(errorMessage);
	}
	
}