package com.luv2code.springdemo.entity.tms.error;

public class TMSErrorutils {
	public static String getDescription (TMSExeption exception)
	{
		switch(exception.getErrorCode())
		{
		case "E001":
			return "InvalidDataException";
		case "E002":
			return "DuplicateCategoryException";
		case "E003":
			return "InvalidDateException";
		case "E004":
			return "MissingArgumentException";
		case "E005":
			return "NegativeAmountException";
		case "E006":
			return "NullAmountException";
		case "E007":
			return "InvalidPaymentmethodException";
		default :
			return "Unknown Exception!!!";
		
		}
		
	}

}
