package com.chen1144.calculator.util;

import java.util.*;
import java.util.function.BinaryOperator;

public class DAG<K, V> {
    private BinaryOperator<V> edgeCompressor;
    private Map<K, Map<K, V>> prevMap;
    private Map<K, Map<K, V>> nextMap;

    public DAG(BinaryOperator<V> compressor){
        this.edgeCompressor = compressor;
        prevMap = new HashMap<>();
        nextMap = new HashMap<>();
    }

    public void put(K src, K dst, V val){
        Map<K, V> srcPrevNodes = this.prevMap.get(src);
        Map<K, V> dstNextNodes = this.nextMap.get(dst);
        if(srcPrevNodes != null){
            srcPrevNodes.forEach((K prev, V edge) -> {
                V compressed = edgeCompressor.apply(edge, val);
                putIfAbsent(prev, dst, compressed);
            });
        }
        if(dstNextNodes != null){
            dstNextNodes.forEach((K next, V edge)->{
                V compressed = edgeCompressor.apply(val, edge);
                putIfAbsent(src, next, compressed);
            });
        }
        if(srcPrevNodes != null && dstNextNodes != null){
            srcPrevNodes.forEach((K prev, V edge1) -> {
                dstNextNodes.forEach((K next, V edge2) -> {
                    V compressed = edgeCompressor.apply(edgeCompressor.apply(edge1, val), edge2);
                    putIfAbsent(prev, next, compressed);
                });
            });
        }
        prevMap.computeIfAbsent(dst, useLess->new HashMap<>()).put(src, val);
        nextMap.computeIfAbsent(src, useLess->new HashMap<>()).put(dst, val);
    }

    public void putIfAbsent(K src, K dst, V val){
        prevMap.computeIfAbsent(dst, useLess->new HashMap<>()).putIfAbsent(src, val);
        nextMap.computeIfAbsent(src, useLess->new HashMap<>()).putIfAbsent(dst, val);
    }

    public V get(K src, K dst){
        Map<K, V> dstPrevNodes = this.prevMap.get(dst);
        if(dstPrevNodes == null){
            return null;
        }else{
            return dstPrevNodes.get(src);
        }
    }

    public Map<K, V> getPrevMap(K dst){
        return Collections.unmodifiableMap(prevMap.get(dst));
    }

    public Map<K, V> getNextMap(K src){
        return Collections.unmodifiableMap(nextMap.get(src));
    }
}
