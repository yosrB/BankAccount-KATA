package com.kata.test.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account implements Serializable{
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
        @SequenceGenerator(name = "sequenceGenerator")
        private Long id;

        public long balance;

        @OneToMany(mappedBy = "account")
        public List<Operation> operations = new ArrayList<>();

        public long getBalance() {
            return balance;
        }

        public void setBalance(long balance) {
            this.balance = balance;
        }

        public List<Operation> getOperations() {
            return operations;
        }

        public void setOperations(List<Operation> operations) {
            this.operations = operations;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

}
