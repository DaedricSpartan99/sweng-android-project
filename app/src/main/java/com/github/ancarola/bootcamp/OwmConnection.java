package com.github.ancarola.bootcamp;

import com.github.ancarola.bootcamp.owmstate.OwmState;

import java.nio.channels.NoConnectionPendingException;
import java.util.HashMap;

public interface OwmConnection {

    OwmState getLocalData();

    OwmState pull() throws NoConnectionPendingException;

    boolean hasLocalData();
}
