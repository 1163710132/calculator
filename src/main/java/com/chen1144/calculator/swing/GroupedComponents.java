package com.chen1144.calculator.swing;

import java.util.Collection;
import java.util.Map;

public interface GroupedComponents<TGroup, TComponent> {
    void add(TGroup group, TComponent component);
    void clear();
}
