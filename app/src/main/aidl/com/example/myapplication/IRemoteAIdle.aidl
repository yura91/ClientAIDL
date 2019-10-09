// IRemoteAIdle.aidl
package com.example.myapplication;

// Declare any non-default types here with import statements
import com.example.myapplication.Product;
interface IRemoteAIdle {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   void addProduct(String name , int quantity, float cost);
       Product getProduct(String name);
}
