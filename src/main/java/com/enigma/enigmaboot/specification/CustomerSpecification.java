package com.enigma.enigmaboot.specification;

import com.enigma.enigmaboot.dto.CustomerSearchDTO;
import com.enigma.enigmaboot.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerSpecification {
    public static Specification<Customer> getSpecification (CustomerSearchDTO customerSearchDTO) {
        return new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (!(customerSearchDTO.getSearchCustomerId() == null )) {
                    Predicate customerIdPredicate = criteriaBuilder.like(root.get("id"), "%" + customerSearchDTO.getSearchCustomerId() + "%");
                    predicates.add(customerIdPredicate);
                }

                if (!(customerSearchDTO.getSearchCustomerFirstName() == null )) {
                    Predicate customerFirstNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + customerSearchDTO.getSearchCustomerFirstName().toLowerCase(Locale.ROOT) + "%");
                    predicates.add(customerFirstNamePredicate);
                }

                if (!(customerSearchDTO.getSearchCustomerLastName() == null )) {
                    Predicate customerLastNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + customerSearchDTO.getSearchCustomerLastName().toLowerCase(Locale.ROOT) + "%");
                    predicates.add(customerLastNamePredicate);
                }

                if (!(customerSearchDTO.getSearchCustomerDateOfBirth() == null )) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //untuk memformat
                    String modifiedDate = sdf.format(customerSearchDTO.getSearchCustomerDateOfBirth());
                    System.out.println("SPECIFICATION MODIFIED : " + modifiedDate);

                    Predicate createDatePredicate = criteriaBuilder.equal(criteriaBuilder.function("TO_CHAR", String.class, root.get("dateOfBirth"), criteriaBuilder.literal("yyyy-MM-dd")), modifiedDate);
                    System.out.println("CREATE DATE : " + createDatePredicate);
                    predicates.add(createDatePredicate);
                }

                if (!(customerSearchDTO.getSearchCustomerStatus() == null )) {
                    Predicate customerStatusPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + customerSearchDTO.getSearchCustomerStatus().toLowerCase(Locale.ROOT) + "%");
                    predicates.add(customerStatusPredicate);
                }

                if (!(customerSearchDTO.getSearchCustomerUserName() == null )) {
                    Predicate customerUserNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("userName")), "%" + customerSearchDTO.getSearchCustomerUserName().toLowerCase(Locale.ROOT) + "%");
                    predicates.add(customerUserNamePredicate);
                }

                Predicate[] arrayPredicate = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicate);
            }
        };
    }
}
