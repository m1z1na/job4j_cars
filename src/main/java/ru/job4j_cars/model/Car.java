package ru.job4j_cars.model;

import java.util.HashSet;
import java.util.Set;

public class Car {

    private int id;

    private Engine engine;

    private Set<Owner> owners = new HashSet<>();
}