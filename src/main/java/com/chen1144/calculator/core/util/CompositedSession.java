package com.chen1144.calculator.core.util;

import com.chen1144.calculator.core.context.Tokenizer;
import com.chen1144.calculator.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompositedSession implements Tokenizer.Session {
    private List<Tokenizer.Session> subSessions;
    private List<Tokenizer.Session> availableSessions;
    private List<Pair<Integer, Tokenizer.TokenizeResult>> ptrResultPairs;
    private int ptr = -1;

    public CompositedSession(Stream<Tokenizer.Session> sessions){
        this.subSessions = sessions.collect(Collectors.toList());
        availableSessions = new java.util.LinkedList<>(subSessions);
        ptrResultPairs = new ArrayList<>();
    }

    @Override
    public Tokenizer.TokenizeResult apply(int value) {
        ptr++;
        if(availableSessions.size() > 0){
            availableSessions.removeIf(session -> {
                Tokenizer.TokenizeResult tokenizeResult = session.apply(value);
                if(tokenizeResult.succeed()){
                    ptrResultPairs.add(new Pair<>(ptr, tokenizeResult));
                    return true;
                }else if(tokenizeResult.failed()){
                    return true;
                }else return false;
            });
        }
        if(availableSessions.size() > 0){
            return Tokenizer.TokenizeResult.ofUndetermined();
        }else if(ptrResultPairs.size() > 0){
            Pair<Integer, Tokenizer.TokenizeResult> top = ptrResultPairs.get(ptrResultPairs.size() - 1);
            int rollback = ptr - top.getKey() + top.getValue().rollback();
            Tokenizer.TokenizeResult result = Tokenizer.TokenizeResult.ofSucceed(top.getValue().get(), rollback);
            reset();
            return result;
        }else{
            reset();
            return Tokenizer.TokenizeResult.ofFailed();
        }
    }

    public void reset(){
        availableSessions.clear();
        availableSessions.addAll(subSessions);
        ptrResultPairs.clear();
        ptr = -1;
    }
}
