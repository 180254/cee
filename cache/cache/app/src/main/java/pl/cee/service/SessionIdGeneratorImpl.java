package pl.cee.service;

import java.math.BigInteger;
import java.util.Random;

public class SessionIdGeneratorImpl implements SessionIdGenerator {

    private final Random random;

    public SessionIdGeneratorImpl(Random random) {
        this.random = random;
    }

    @Override
    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
}
