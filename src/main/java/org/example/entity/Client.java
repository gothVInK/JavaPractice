package org.example.entity;

import org.example.entity.common.Column;
import org.example.entity.common.IEntity;
import org.example.entity.common.NonEditColumn;
import org.example.entity.common.SequenceColumn;

import java.time.LocalDate;
import java.util.Map;

public class Client implements IEntity {
    @Column(title = "Client Code")
    @SequenceColumn
    @NonEditColumn
    private Integer client_code;

    @Column(title = "First Name") private String first_name;
    @Column(title = "Middle Name") private String middle_name;
    @Column(title = "Last Name") private String last_name;
    @Column(title = "Passport Number") private String passport_number;
    @Column(title = "Client Adress") private String client_adress;
    @Column(title = "Phone number") private String phone_number;

    public Client(Integer clientCode, String firstName, String middleName, String lastName, String passportNumber, String clientAdress, String phoneNumber) {
        this.client_code = clientCode;
        this.first_name = firstName;
        this.middle_name = middleName;
        this.last_name = lastName;
        this.passport_number = passportNumber;
        this.client_adress = clientAdress;
        this.phone_number = phoneNumber;
    }

    public Client(Map<String, String> columns) {
        this(
                columns.get("client_code") != null ? Integer.valueOf(columns.get("client_code")) : null,
                columns.get("first_name"),
                columns.get("middle_name"),
                columns.get("last_name"),
                columns.get("passport_number"),
                columns.get("client_adress"),
                columns.get("phone_number")
        );
    }

    @Override
    public Integer getPk() {
        return client_code;
    }
    public String getFirstName() {return first_name;}
    public String getMiddleName() {
        return middle_name;
    }
    public String getLastName() {
        return last_name;
    }
    public String getPassportNumber() {
        return passport_number;
    }
    public String getClientAdress() {
        return client_adress;
    }
    public String getPhoneNumber() {
        return phone_number;
    }

}
