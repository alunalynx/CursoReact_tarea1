package org.example;

import java.util.List;

public class numeros2 implements numeros {

    @Override
    public double mayor(List<Integer> nums) {
        return nums.stream().mapToDouble(a -> a).max().getAsDouble();
    }

    @Override
    public double menor(List<Integer> nums) {
        return nums.stream().mapToDouble(a -> a).min().getAsDouble();
    }



}
