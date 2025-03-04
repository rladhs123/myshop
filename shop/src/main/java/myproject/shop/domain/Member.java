package myproject.shop.domain;

import lombok.Data;

@Data
public class Member {

    private int memberId;
    private String name;
    private int address;

    public Member() {
    }

    public Member(int memberId, String name, int address) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
    }
}
