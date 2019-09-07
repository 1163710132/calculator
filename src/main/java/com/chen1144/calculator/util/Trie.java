package com.chen1144.calculator.util;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Trie<T, V> {
    private TrieNode<T, V> root;
    private BiPredicate<T, T> matcher;

    public Trie(BiPredicate<T, T> matcher){
        root = new TrieNode<>();
        this.matcher = matcher;
    }

    public void insert(Stream<T> seq, V value){
        var consumer = new Consumer<T>() {
            TrieNode<T, V> node = root;
            @Override
            public void accept(T t) {
                Optional<TrieNode<T, V>> optional = node.children.stream()
                        .filter(pair -> matcher.test(pair.getKey(), t))
                        .map(Pair::getValue)
                        .findAny();
                if(!optional.isPresent()){
                    TrieNode<T, V> child = new TrieNode<>();
                    node.children.add(new Pair<>(t, child));
                    node = child;
                }else{
                    node = optional.get();
                }
            }
        };
        seq.forEachOrdered(consumer);
        consumer.node.value = value;
    }

    public Stream<V> getByPredicate(Stream<Predicate<T>> seq){
        var consumer = new Consumer<Predicate<T>>() {
            Stream<TrieNode<T, V>> nodes = Stream.of(root);
            @Override
            public void accept(Predicate<T> predicate) {
                nodes = nodes.flatMap(node ->
                        nodes = node.children.stream()
                                .filter(child -> predicate.test(child.getKey()))
                                .map(Pair::getValue));
            }
        };
        seq.forEachOrdered(consumer);
        return consumer.nodes.map(node -> node.value).filter(Objects::nonNull);
    }

    public Stream<V> get(Stream<T> seq){
        var consumer = new Consumer<T>() {
            Stream<TrieNode<T, V>> nodes = Stream.of(root);
            @Override
            public void accept(T t) {
                nodes = nodes.flatMap(node ->
                    nodes = node.children.stream()
                            .filter(child -> {
                                return matcher.test(child.getKey(), t);
                            })
                            .map(Pair::getValue));
            }
        };
        seq.forEachOrdered(consumer);
        return consumer.nodes.map(node -> node.value).filter(Objects::nonNull);
    }

    static class TrieNode<T, V>{
        //Map<T, TrieNode<T, V>> children;
        List<Pair<T, TrieNode<T, V>>> children;
        V value;

        TrieNode(){
            this(null);
        }

        TrieNode(V value){
            this.value = value;
            this.children = new ArrayList<>();
        }
    }
}
