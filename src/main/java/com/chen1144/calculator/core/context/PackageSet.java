package com.chen1144.calculator.core.context;

import com.chen1144.calculator.plugin.CalculatePackage;

public interface PackageSet {
    <T extends CalculatePackage> T getPackage(Class<? extends T> pkg);
}
