package com.example.application;

import org.eclipse.jdt.core.IType;

import com.example.domain.Location;
import com.example.domain.LocationFormatter;

public class TypeProcessor implements ClipProcessor {
    private final IType type;

    public TypeProcessor(IType type) {
        this.type = type;
    }

    @Override
    public String[] getJavaLocation() {
        Location location = LocationResolver.resolve(this.type);
        return LocationFormatter.asType(location);
    }
}
