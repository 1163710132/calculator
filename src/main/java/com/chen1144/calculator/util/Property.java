package com.chen1144.calculator.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class Property<T> implements Supplier<T>, Consumer<T> {
    private Supplier<T> getter;
    private Consumer<T> setter;

    public Property(Supplier<T> getter, Consumer<T> setter){
        this.getter = getter;
        this.setter = setter;
    }

    public static <T> Property<T> of(Supplier<T> getter, Consumer<T> setter){
        return new Property<>(getter, setter);
    }

    @Override
    public T get(){
        return getter.get();
    }

    public T set(T value){
        setter.accept(value);
        return getter.get();
    }

    public Property<T> putIfAbsent(T value){
        if (getter.get() == null){
            setter.accept(value);
        }
        return this;
    }

    public Property<T> computeIfAbsent(Supplier<T> supplier){
        if (getter.get() == null){
            setter.accept(supplier.get());
        }
        return this;
    }

    @Override
    public void accept(T t) {
        setter.accept(t);
    }

    public void exec(Consumer<T> consumer){
        consumer.accept(getter.get());
    }
}
