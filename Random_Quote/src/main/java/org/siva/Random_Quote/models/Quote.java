package org.siva.Random_Quote.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quote {
    private String content;

    public String print(){
        return this.getContent();
    }

}