package com.oranbyte.acolyte.services;

import com.oranbyte.acolyte.AcolyteCommandRoot;

public interface AcolyteRootService {

    void printBanner();

    void printVersionString();

    void printCurrentCommandDetails(AcolyteCommandRoot root);

    void printCommandList();


}
