package com.nitinsurana.activemqexample;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        new Producer().start();
        new Consumer().start();
        new ObjectProducer().start();
        new ObjectConsumer().start();
    }
}
