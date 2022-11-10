package com.example.doctorcare.api.utilis;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PaginationAndSortUtil {
    public Pageable paginate(int page, int size, String[] sorts) {
        Sort sort = sortBy(sorts);
        Pageable pageable;

        if (sort != null) {
            pageable = PageRequest.of(page - 1, size, sort);
        } else {
            pageable = PageRequest.of(page - 1, size);
        }

        return pageable;
    }

    private Sort sortBy(String[] sorts) {
        if (sorts == null || sorts.length == 0) return null;

        List<Sort.Order> orders = new ArrayList<>();

        if (sorts[0].contains(",")) {
            // sortOrder="field, direction"
            for (String sortOrder : sorts) {
                String[] _sort = sortOrder.replace("\\s", "").split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sorts[1].replace("\\s", "")),
                    sorts[0].replace("\\s", "")));
        }

        return Sort.by(orders);
    }

    private Sort.Direction getSortDirection(String direction) {
        if ("desc".equals(direction)) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    public static <T> List<List<T>> getPages(Collection<T> c, Integer pageSize) {
        if (c == null)
            return Collections.emptyList();
        List<T> list = new ArrayList<T>(c);
        if (pageSize == null || pageSize <= 0 || pageSize > list.size())
            pageSize = list.size();
        int numPages = (int) Math.ceil((double)list.size() / (double)pageSize);
        List<List<T>> pages = new ArrayList<List<T>>(numPages);
        for (int pageNum = 0; pageNum < numPages;)
            pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        return pages;
    }
}
