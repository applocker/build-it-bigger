package com.dappslocker.lib;

public class Joker {
    String [] jokes = {
            "Joke 0: Why did the Chicken cross the road?",
            "Joke 1: Why did the Monkey cross the road?",
            "Joke 2: Why did the Rabbit cross the road?",
            "Joke 3: Why did the Elephant cross the road?"
    };
    public String tellARandomJoke(){
        int jokeIndex = (int)(Math.random() * jokes.length);
        return jokes[jokeIndex];
    }
}
