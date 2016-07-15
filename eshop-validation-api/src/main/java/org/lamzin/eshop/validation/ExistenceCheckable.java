package org.lamzin.eshop.validation;

/**
 * Created by Dmitriy on 03.04.2016.
 */
public interface ExistenceCheckable<K> {
    boolean exists (K id);
}
