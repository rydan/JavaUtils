package org.cutpaste.utils;

import java.util.Optional;
import java.util.function.Supplier;

public class NoNull {

    /**
     *
     * get uses an ExecuteAroundMethod pattern to remove the need for null checks during a get-like operation
     *
     * get is meant to encapsulate null checks when doing chained gets, where any part in the chain might return null.
     * (But it can be used in pretty much any situation where a single value needs to be fetched, and steps along the way might
     * result in null pointer exceptions)
     *
     * The Optional will be empty if the function completes successfully but returns null, or if there is a null pointer
     * exception thrown at any time during the execution of the supplied function
     *
     * @param function to execute for value
     * @param <T> return type
     * @return Optional, containing value of function, or empty if value of function is null or function throws null pointer somewhere
     */

    public static <T> Optional<T> get(Supplier<T> function) {
        try {
            return Optional.ofNullable(function.get());
        } catch (NullPointerException e) {
            //OK, just return empty Optional
        }
        return Optional.empty();
    }

}
