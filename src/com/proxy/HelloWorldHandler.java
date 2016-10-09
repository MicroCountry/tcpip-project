package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloWorldHandler implements InvocationHandler{
	
	private Object obj;
	
	public HelloWorldHandler(Object object){
		this.obj = object;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result =null;
		doBefore();
		result = method.invoke(obj, args);
		doAfter();
		return result;
	}

	private void doAfter() {
		System.out.println("before...");
	}

	private void doBefore() {
		System.out.println("after...");
	}

}
