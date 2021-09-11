package com.example.quit;

import com.example.quit.models.AddictionWithRelapse;
import com.example.quit.models.Relapse;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

public class StatisticTest {

    @Test
    public void test_sort_relapse() {
        List<Relapse> relapses = new ArrayList<>();
        relapses.add(new Relapse(1));
        relapses.add(new Relapse(5));List<Long> periods = new ArrayList<>();
        relapses.add(new Relapse(6));
        relapses.add(new Relapse(0));
        for (Relapse relapse : relapses) {
            System.out.println(relapse.getRelapseDate());
        }

        System.out.println("-------------");

        for (Relapse relapse:new TreeSet<>(relapses) ) {
            System.out.println(relapse.getRelapseDate());
        }

    }
}
