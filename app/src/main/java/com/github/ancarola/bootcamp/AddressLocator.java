package com.github.ancarola.bootcamp;

public interface AddressLocator {

    String toAddress(Location location);
    Location toLocation(String address);
}
