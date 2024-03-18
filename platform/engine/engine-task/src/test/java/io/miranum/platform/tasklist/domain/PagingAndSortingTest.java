package io.miranum.platform.tasklist.domain;

import org.junit.jupiter.api.Test;

import static io.miranum.platform.tasklist.domain.PagingAndSorting.DEFAULT_SORT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PagingAndSortingTest {

    @Test
    public void defaults_if_empty() {
        var pageAndSort = new PagingAndSorting(0, 1, null);
        assertThat(pageAndSort.getPageIndex()).isEqualTo(0);
        assertThat(pageAndSort.getPageSize()).isEqualTo(1);
        assertThat(pageAndSort.getSanitizedSort()).isEqualTo(DEFAULT_SORT);
    }

    @Test
    public void checks_if_no_direction() {
        var pageAndSort = new PagingAndSorting(0, 1, "other");
        assertThat(pageAndSort.getPageIndex()).isEqualTo(0);
        assertThat(pageAndSort.getPageSize()).isEqualTo(1);
        assertThrows(IllegalArgumentException.class, pageAndSort::getSanitizedSort);
    }

    @Test
    public void delivers_sort() {
        var pageAndSort = new PagingAndSorting(0, 1, "+other");
        assertThat(pageAndSort.getPageIndex()).isEqualTo(0);
        assertThat(pageAndSort.getPageSize()).isEqualTo(1);
        assertThat(pageAndSort.getSanitizedSort()).isEqualTo("+other");
    }

}