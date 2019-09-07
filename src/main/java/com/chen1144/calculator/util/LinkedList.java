package com.chen1144.calculator.util;

import java.util.Iterator;
import java.util.function.Consumer;

public class LinkedList<T> {
    private Node<T> nil;
    private int size;

    public LinkedList(){
        nil = new Node<>();
        nil.prev = nil;
        nil.next = nil;
        size = 0;
    }

    public Iterator iterator(){
        return new Iterator(nil);
    }

    public void insertPrev(Iterator iterator, T value){
        Node<T> node = new Node<>();
        node.value = value;
        iterator.node.next.prev = node;
        node.prev = iterator.node;
        node.next = iterator.node.next;
        iterator.node.next = node;
        size++;
    }

    public void insertNext(Iterator iterator, T value){
        Node<T> node = new Node<>();
        node.value = value;
        iterator.node.prev.next = node;
        node.next = iterator.node;
        node.prev = iterator.node.prev;
        iterator.node.prev = node;
        size++;
    }

    public void pushBack(T value){
        Node<T> node = new Node<>();
        node.value = value;
        nil.prev.next = node;
        node.prev = nil.prev;
        node.next = nil;
        nil.prev = node;
        size++;
    }

    public int size(){
        return size;
    }

    public static class Node<T>{
        private Node<T> prev;
        private Node<T> next;
        private T value;

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }
    public class Iterator {
        private Node<T> node;

        public Iterator(Node<T> node){
            this.node = node;
        }

        public boolean moveNext() {
            node = node.next;
            return node != nil;
        }

        public T get() {
            return node.value;
        }

        public boolean movePrev() {
            node = node.prev;
            return node != nil;
        }

        public T remove() {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
            return node.value;
        }

        public Iterator next(){
            return new Iterator(node.next);
        }

        public Iterator prev(){
            return new Iterator(node.prev);
        }

        public void set(T t) {
            node.value = t;
        }

        public boolean valid(){
            return node != nil;
        }

        public Iterator fork(){
            return new Iterator(node);
        }

        @Override
        public String toString() {
            if(node == nil){
                return "NIL";
            }else{
                return node.toString();
            }
        }
    }
}
