package com.himanshu.companyservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IpoNotFoundException extends Throwable
{
	private String message;
}
