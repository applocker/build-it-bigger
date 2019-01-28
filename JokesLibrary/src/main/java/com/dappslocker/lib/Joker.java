package com.dappslocker.lib;

public class Joker {

    @SuppressWarnings("CanBeFinal")
    private String [] jokes = {
            "Joke 0: Why did the Chicken cross the road? To get to the other side",
            "Joke 1: Why did the Monkey cross the road? To get to the other side",
            "Joke 2: Why did the Rabbit cross the road? To get to the other side",
            "Joke 3: Why did the Elephant cross the road? To get to the other side"
    };
    public String tellARandomJoke(){
        int jokeIndex = (int)(Math.random() * jokes.length);
        return jokes[jokeIndex];
    }
}
