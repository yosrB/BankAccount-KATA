package com.kata.test.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Operation implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private Date date;

    private OperationType type;

    private Long amount ;

    private Long balance ;

    @ManyToOne
    private Account account;

    public Operation(Date date, OperationType type, long amount,long balance, Account account) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.balance= balance;
        this.account = account;
    }

    public Operation() {

    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Long account_id) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Operation{" +
                " date=" + date +
                ", type=" + type +
                ", amount=" + amount +
                ", balance=" + balance +
                "} ";
    }
}
