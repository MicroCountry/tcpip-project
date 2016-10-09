package com.proxy;

import java.lang.reflect.Proxy;

public class TestProxy {
	public static void main(String[] args) {
		HelloWordImpl hello = new HelloWordImpl();
		HelloWorldHandler handler = new HelloWorldHandler(hello);
		HelloWorld proxy = (HelloWorld)Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), handler);
		proxy.sayHello();
	}
}
