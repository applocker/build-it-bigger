package com.udacity.gradle.builditbigger.backend;

import com.dappslocker.lib.Joker;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@SuppressWarnings("DefaultAnnotationParam")
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    /** A simple endpoint method that retrieves a random Joke */
    @ApiMethod(name = "getAJoke")
    public MyBean getAJoke() {
        Joker joke = new Joker();
        MyBean response = new MyBean();
        response.setData(joke.tellARandomJoke());
        return response;
    }

}
